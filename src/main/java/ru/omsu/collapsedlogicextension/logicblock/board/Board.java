package ru.omsu.collapsedlogicextension.logicblock.board;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.function.Supplier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.omsu.collapsedlogicextension.init.ModInit;
import ru.omsu.collapsedlogicextension.logicblock.board.cellstates.Activator;
import ru.omsu.collapsedlogicextension.logicblock.board.cellstates.CellState;
import ru.omsu.collapsedlogicextension.logicblock.board.cellstates.EmptyCell;
import ru.omsu.collapsedlogicextension.logicblock.board.tools.Tool;
import ru.omsu.collapsedlogicextension.logicblock.util.CombinedTextureRegions;
import ru.omsu.collapsedlogicextension.logicblock.util.Direction2D;

public class Board {

    private static final Logger logger = LogManager.getLogger(ModInit.MOD_ID + " : " + Board.class);

    /** Фантомная клетка для активации начальной */
    private final Cell activator = new Cell(this, -1, 4);

    private final Cell[][] cells = new Cell[9][13];
    private Queue<Runnable> deferredCellUpdate = new LinkedList<>();

    public Board() {
        activator.cellState = new Activator(activator);
        for (int y = 0; y < cells.length; ++y) {
            for (int x = 0; x < cells[0].length; ++x) {
                cells[y][x] = new Cell(this, x, y);
            }
        }
    }

    public void applyTool(final Tool tool, final int x, final int y) {
        final Cell cell = getCell(x, y);
        final CellState newCellState = tool.apply(cell);
        final Map<Direction2D, Boolean> map = cell.setCellState(newCellState);
        addEvents(cell, map);
        update();
        addEvents(cell, newCellState.update());
    }

    public void switchSchemeActive() {
        addEvents(
                activator,
                activator.isActivate(Direction2D.RIGHT)
                        ? activator.cellState.forceDeactivate()
                        : activator.cellState.forceActivate());
    }

    /**
     * Класс должен возвращать данные, которые потом сможет десериализовать через метод {@link
     * #deserialize}
     */
    public String serialize() {
        final StringBuilder builder = new StringBuilder();
        for (final Cell[] cells : cells) {
            for (final Cell cell : cells) {
                builder.append(cell.cellState.getClass().getName()).append(';');
            }
        }
        return builder.toString();
    }

    /**
     * Класс должен загружать данные, которые потом сможет сериализовать через метод {@link
     * #serialize}
     */
    public void deserialize(final String data) {
        try {
            StringBuilder builder = new StringBuilder();
            int x = 0, y = 0;
            for (final char c : data.toCharArray()) {
                if (c != ';') builder.append(c);
                else {
                    final Class<?> clazz = Class.forName(builder.toString());
                    cells[y][x].cellState =
                            (CellState) clazz.getConstructor(Cell.class).newInstance(cells[y][x]);
                    ++x;
                    if (x == cells[0].length) {
                        x = 0;
                        ++y;
                        if (y == cells.length) return;
                    }
                    builder = new StringBuilder();
                }
            }
        } catch (final InstantiationException
                | IllegalAccessException
                | InvocationTargetException
                | NoSuchMethodException
                | ClassNotFoundException e) {
            logger.error(e.getMessage());
        }
        logger.error("Board has not been deserialized!");
        for (int y = 0; y < cells.length; ++y) {
            for (int x = 0; x < cells[0].length; ++x) {
                cells[y][x] = new Cell(this, x, y);
            }
        }
    }

    /** @return updater, который возвращает текстуру конкретной клетки доски */
    public Supplier<CombinedTextureRegions> getTextureUpdaterForCell(final int x, final int y) {
        return getCell(x, y)::getTexture;
    }

    private Cell getCell(final int x, final int y) {
        if (x == activator.x && y == activator.y) return activator;
        return x < 0 || y < 0 || x >= cells[0].length || y >= cells.length
                ? new Cell(this, -100, -100)
                : cells[y][x];
    }

    private Cell getCell(final Cell from, final Direction2D fromTo) {
        return getCell(from.x + fromTo.xShift, from.y + fromTo.yShift);
    }

    /** Обновляет доску с каждым игровым тиком */
    public void update() {
        final Queue<Runnable> deferredCellUpdate = this.deferredCellUpdate;
        this.deferredCellUpdate = new LinkedList<>();
        for (final Runnable event : deferredCellUpdate) event.run();
    }

    private void addEvents(final Cell cell, final Map<Direction2D, Boolean> map) {
        for (final Map.Entry<Direction2D, Boolean> entry : map.entrySet()) {
            final Cell dirCell = getCell(cell, entry.getKey());
            if (entry.getValue()) {
                deferredCellUpdate.add(() -> addEvents(dirCell, dirCell.activate(entry.getKey())));
            } else {
                deferredCellUpdate.add(
                        () -> addEvents(dirCell, dirCell.deactivate(entry.getKey())));
            }
        }
    }

    public static final class Cell {

        public final int x;
        public final int y;
        @Deprecated private final Board board;

        private CellState cellState = new EmptyCell(this);

        private Cell(final Board board, final int x, final int y) {
            this.board = board;
            this.x = x;
            this.y = y;
        }

        /**
         * @return клетка в направлении от текущей
         * @deprecated удалить, т.к. у CellState метод getTexture(Set<Cell> neighbors) принимает
         *     соседей клетки и update тоже
         */
        @Deprecated
        public Cell getCell(final Direction2D fromThisTo) {
            return board.getCell(this, fromThisTo);
        }

        /** @return текстура клетки */
        public CombinedTextureRegions getTexture() {
            return cellState.getTexture();
        }

        /** @return новое состояние клетки, повернутой по часовой стрелке на 90 градусов */
        public CellState getRotated() {
            return cellState.getRotated();
        }

        /** Устанавливает новое состояние этой клетке */
        public Map<Direction2D, Boolean> setCellState(final CellState newCellState) {
            if (!cellState.equalsWithoutActive(newCellState)) {
                final Map<Direction2D, Boolean> map = cellState.forceDeactivate();
                cellState = newCellState;
                return map;
            }
            return new HashMap<>();
        }

        /** Активирует клетку */
        public Map<Direction2D, Boolean> activate(final Direction2D fromToThis) {
            return cellState.activate(fromToThis);
        }

        /** Деактивирует клетку */
        public Map<Direction2D, Boolean> deactivate(final Direction2D fromToThis) {
            return cellState.deactivate(fromToThis);
        }

        /** @return true, если клетка активирована */
        public boolean isActivate(final Direction2D fromThisTo) {
            return cellState.isActivate(fromThisTo);
        }

        /** @return true, если клетка может быть соединена с клеткой в указанном направлении */
        public boolean canBeConnected(final Direction2D fromToThis) {
            return cellState.canBeConnected(fromToThis);
        }
    }
}
