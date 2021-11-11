package ru.omsu.collapsedlogicextension.common.blocks.logicblock.board;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.function.Supplier;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.omsu.collapsedlogicextension.common.blocks.logicblock.board.cellstates.Activator;
import ru.omsu.collapsedlogicextension.common.blocks.logicblock.board.cellstates.CellState;
import ru.omsu.collapsedlogicextension.common.blocks.logicblock.board.cellstates.EmptyCell;
import ru.omsu.collapsedlogicextension.common.blocks.logicblock.board.cellstates.Wire;
import ru.omsu.collapsedlogicextension.common.blocks.logicblock.board.tools.Tool;
import ru.omsu.collapsedlogicextension.common.blocks.logicblock.util.CombinedTextureRegions;
import ru.omsu.collapsedlogicextension.common.blocks.logicblock.util.Direction2D;
import ru.omsu.collapsedlogicextension.common.blocks.logicblock.util.Direction3D;
import ru.omsu.collapsedlogicextension.init.ModInit;
import ru.omsu.collapsedlogicextension.util.api.Serializer.Serializable;

public class Board implements Serializable {

    private static final Logger logger = LogManager.getLogger(ModInit.MOD_ID + " : " + Board.class);

    private final Cell emptyCell = new Cell(this, -100, -100);

    // TODO: изменить на клетки-входы редстоун сигнала (?)
    /** Фантомная клетка для активации начальной */
    private final Cell activator = new Cell(this, -1, 4);

    private final Cell[][] cells = new Cell[9][13];
    private final Queue<Runnable> deferredEvents = new ArrayDeque<>();

    public Board() {
        activator.cellState = new Activator(activator);
        for (int y = 0; y < cells.length; ++y) {
            for (int x = 0; x < cells[0].length; ++x) {
                cells[y][x] = new Cell(this, x, y);
            }
        }
    }

    /** @return true, если сторона блока side может взаимодействовать с редстоуном */
    public boolean canConnectRedstone(final Direction3D side) {
        // TODO: сделать включение/отключение сторон блока (?)
        return cells[side.y][side.x].isConductive();
    }

    public int getPowerOnSide(final Direction3D side){
        return cells[side.y][side.x].isActive() ? 15 : 0;
    }

    public void applyTool(final Tool tool, final int x, final int y) {
        final Cell cell = getCell(x, y);
        cell.setCellState(tool.apply(cell));
    }

    private Cell getCell(final int x, final int y) {
        if (x == activator.x && y == activator.y) return activator;
        return x < 0 || y < 0 || x >= cells[0].length || y >= cells.length
                ? emptyCell
                : cells[y][x];
    }

    private Cell getCell(final Cell from, final Direction2D fromTo) {
        return getCell(from.x + fromTo.xShift, from.y + fromTo.yShift);
    }

    public void switchSchemeActive() {
        if (!activator.isActive()) activator.cellState.forceActivate();
        else activator.cellState.forceDeactivate();
    }

    // TODO: переделать на Gson
    @Override
    public String serialize() {
        logger.log(Level.INFO, "Serialising board...");
        final StringBuilder builder = new StringBuilder();
        for (final Cell[] cells : cells) {
            for (final Cell cell : cells) {
                builder.append(cell.cellState.getClass().getName()).append(';');
            }
        }
        return builder.toString();
    }

    // TODO: переделать на Gson
    @Override
    public void deserialize(final String data) {
        logger.log(Level.INFO, "Deserialising board...");
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

    /** Обновляет доску с каждым игровым тиком */
    public void update() {
        for (int i = deferredEvents.size(); i > 0; --i) deferredEvents.poll().run();
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

        /** @return новое состояние клетки, повернутой против часовой стрелки на 90 градусов */
        public CellState getRotated() {
            return cellState.getRotated();
        }

        /** Устанавливает новое состояние этой клетке */
        private void setCellState(final CellState newCellState) {
            cellState.forceDeactivate();
            cellState = newCellState;
            board.update();
            for (final Direction2D direction : Direction2D.values()) {
                final Cell cell = getCell(direction);
                if (cell.isActive()) board.deferredEvents.add(() -> cell.cellState.forceActivate());
                else board.deferredEvents.add(() -> cell.cellState.forceDeactivate());
            }
        }

        /** Активирует клетку */
        public void activate(final Cell from, final Direction2D fromToThis) {
            board.deferredEvents.add(() -> cellState.activate(from, fromToThis));
        }

        /** Деактивирует клетку */
        public void deactivate(final Cell from, final Direction2D fromToThis) {
            board.deferredEvents.add(() -> cellState.deactivate(from, fromToThis));
        }

        /** @return true, если клетка активирована */
        public boolean isActive() {
            return cellState.isActive();
        }

        public boolean isConductive(){
            return cellState.isConductive();
        }

        /** @return true, если клетка может быть соединена с клеткой в указанном направлении */
        public boolean canBeConnected(final Direction2D fromToThis) {
            return cellState.canBeConnected(fromToThis);
        }
    }
}
