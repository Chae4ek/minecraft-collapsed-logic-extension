package ru.omsu.collapsedlogicextension.logicblock.board;

import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
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
    private Set<Runnable> deferredCellUpdate = new HashSet<>();

    public Board() {
        activator.cellState = new Activator(activator);
        for (int y = 0; y < cells.length; ++y) {
            for (int x = 0; x < cells[0].length; ++x) {
                cells[y][x] = new Cell(this, x, y);
            }
        }
    }

    public void applyTool(final Tool tool, final int x, final int y) {
        Cell cell = getCell(x, y);
        CellState newCellState = tool.apply(cell);
        addEvents(cell, cell.setCellState(newCellState));
        if(x == 1){
            //x+=0;
        }
        this.update();
        addEvents(cell, newCellState.update());
    }

    public void switchSchemeActive() {
        if (!activator.isActivate(Direction2D.RIGHT)) activator.cellState.forceActivate();
        else activator.cellState.forceDeactivate();
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
        final Set<Runnable> deferredCellUpdate = this.deferredCellUpdate;
        this.deferredCellUpdate = new HashSet<>();
        for (final Runnable event : deferredCellUpdate) event.run();
    }

    private void addEvents(Cell cell, Map<Direction2D, Boolean> map){
        for(Map.Entry<Direction2D, Boolean> entry : map.entrySet()){
            if(entry.getValue()){
                deferredCellUpdate.add(() -> addEvents(getCell(cell, entry.getKey()), getCell(cell, entry.getKey()).activate(entry.getKey())));
            }
            else{
                deferredCellUpdate.add(() -> addEvents(getCell(cell, entry.getKey()), getCell(cell, entry.getKey()).deactivate(entry.getKey())));
            }
        }
    }

    public static final class Cell {

        public final int x;
        public final int y;
        private final Board board;

        private CellState cellState = new EmptyCell(this);

        private Cell(final Board board, final int x, final int y) {
            this.board = board;
            this.x = x;
            this.y = y;
        }

        /** @return клетка в направлении от текущей */
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
            Map<Direction2D, Boolean> map = cellState.forceDeactivate();
            cellState = newCellState;
            //board.update();
            //board.update(); // костыль, исправляющий баг с миганием (но не всегда)
            //cellState.update();
            return map;
        }

        /** Активирует клетку */
        public Map<Direction2D, Boolean> activate(final Direction2D fromToThis) {
            return cellState.activate(fromToThis);
            //board.deferredCellUpdate.add(() -> cellState.activate(fromToThis));
        }

        /** Деактивирует клетку */
        public Map<Direction2D, Boolean> deactivate(final Direction2D fromToThis) {
            return cellState.deactivate(fromToThis);
            //board.deferredCellUpdate.add(() -> cellState.deactivate(fromToThis));
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
