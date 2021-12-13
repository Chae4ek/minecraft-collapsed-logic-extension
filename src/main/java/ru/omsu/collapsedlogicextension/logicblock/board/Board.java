package ru.omsu.collapsedlogicextension.logicblock.board;

import java.lang.reflect.InvocationTargetException;
import java.util.EnumMap;
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
    private final Cell activator = new Cell();

    private final int activatorX = -1, activatorY = 4;

    private final Cell[][] cells = new Cell[9][13];
    private Queue<Runnable> deferredCellUpdate = new LinkedList<>();

    public Board() {
        activator.cellState = new Activator();
        for (int y = 0; y < cells.length; ++y) {
            for (int x = 0; x < cells[0].length; ++x) {
                cells[y][x] = new Cell();
            }
        }
    }

    public void applyTool(final Tool tool, final int x, final int y) {
        final Cell cell = getCell(x, y);
        final CellState newCellState = tool.apply(cell);
        final Map<Direction2D, Boolean> map = cell.setCellState(newCellState);
        addEvents(x, y, map);
        update();
        addEvents(x, y, newCellState.update(getNeighbors(x, y)));
    }

    public void switchSchemeActive() {
        addEvents(
                activatorX,
                activatorY,
                activator.isActivate(Direction2D.RIGHT)
                        ? activator.cellState.forceDeactivate()
                        : activator.cellState.forceActivate());
    }

    private Map<Direction2D, Cell> getNeighbors(final int fromX, final int fromY) {
        final Map<Direction2D, Cell> neighbors = new EnumMap<>(Direction2D.class);
        for (final Direction2D connectedDirection : Direction2D.values()) {
            neighbors.put(connectedDirection, getCell(fromX, fromY, connectedDirection));
        }
        return neighbors;
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
                    cells[y][x].cellState = (CellState) clazz.getConstructor().newInstance();
                    if (++x == cells[0].length) {
                        if (++y == cells.length) return;
                        x = 0;
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
                cells[y][x] = new Cell();
            }
        }
    }

    /** @return updater, который возвращает текстуру конкретной клетки доски */
    public Supplier<CombinedTextureRegions> getTextureUpdaterForCell(final int x, final int y) {
        return () -> getCell(x, y).getTexture(getNeighbors(x, y));
    }

    private Cell getCell(final int x, final int y) {
        if (x == activatorX && y == activatorY) return activator;
        return x < 0 || y < 0 || x >= cells[0].length || y >= cells.length
                ? new Cell()
                : cells[y][x];
    }

    private Cell getCell(final int fromX, final int fromY, final Direction2D fromTo) {
        return getCell(fromX + fromTo.xShift, fromY + fromTo.yShift);
    }

    /** Обновляет доску с каждым игровым тиком */
    public void update() {
        final Queue<Runnable> deferredCellUpdate = this.deferredCellUpdate;
        this.deferredCellUpdate = new LinkedList<>();
        for (final Runnable event : deferredCellUpdate) event.run();
    }

    private void addEvents(final int cellX, final int cellY, final Map<Direction2D, Boolean> map) {
        for (final Map.Entry<Direction2D, Boolean> entry : map.entrySet()) {
            final Cell dirCell = getCell(cellX, cellY, entry.getKey());
            if (entry.getValue()) {
                deferredCellUpdate.add(
                        () ->
                                addEvents(
                                        cellX + entry.getKey().xShift,
                                        cellY + entry.getKey().yShift,
                                        dirCell.activate(entry.getKey())));
            } else {
                deferredCellUpdate.add(
                        () ->
                                addEvents(
                                        cellX + entry.getKey().xShift,
                                        cellY + entry.getKey().yShift,
                                        dirCell.deactivate(entry.getKey())));
            }
        }
    }

    public static final class Cell {

        private CellState cellState = new EmptyCell();

        /** @return текстура клетки */
        public CombinedTextureRegions getTexture(final Map<Direction2D, Cell> neighbors) {
            return cellState.getTexture(neighbors);
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
