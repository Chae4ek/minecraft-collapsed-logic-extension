package ru.omsu.collapsedlogicextension.common.blocks.logicblock.board;

import java.util.ArrayList;
import java.util.List;
import ru.omsu.collapsedlogicextension.common.blocks.logicblock.board.cellstates.Activator;
import ru.omsu.collapsedlogicextension.common.blocks.logicblock.board.cellstates.CellState;
import ru.omsu.collapsedlogicextension.common.blocks.logicblock.board.cellstates.EmptyCell;
import ru.omsu.collapsedlogicextension.common.blocks.logicblock.board.tools.Tool;
import ru.omsu.collapsedlogicextension.common.blocks.logicblock.util.CombinedTextureRegions;
import ru.omsu.collapsedlogicextension.common.blocks.logicblock.util.Direction2D;
import ru.omsu.collapsedlogicextension.common.blocks.logicblock.util.Direction3D;
import ru.omsu.collapsedlogicextension.util.api.Serializer.Serializable;

public class Board implements Serializable {

    private final Cell emptyCell = new Cell(this, -100, -100);

    // TODO: изменить на клетки-входы редстоун сигнала (?)
    /** Фантомная клетка для активации начальной */
    private final Cell activator = new Cell(this, 0, 4);

    private final Cell[][] cells = new Cell[9][13];
    private List<Runnable> deferredEvents = new ArrayList<>();

    public Board() {
        activator.setCellState(new Activator(activator));
        for (int y = 0; y < cells.length; ++y) {
            for (int x = 0; x < cells[0].length; ++x) {
                cells[y][x] = new Cell(this, x, y);
            }
        }
    }

    /** @return true, если сторона блока side может взаимодействовать с редстоуном */
    public boolean canConnectRedstone(final Direction3D side) {
        // TODO: сделать включение/отключение сторон блока (?)
        return true;
    }

    public Cell applyTool(final Tool tool, final int x, final int y) {
        final Cell cell = getCell(x, y);
        cell.setCellState(tool.apply(cell));
        return cell;
    }

    public Cell getCell(final int x, final int y) {
        return x < 0 || y < 0 || x >= cells[0].length || y >= cells.length
                ? emptyCell
                : cells[y][x];
    }

    private Cell getCell(final Cell from, final Direction2D fromTo) {
        return getCell(from.x + fromTo.xShift, from.y + fromTo.yShift);
    }

    public void switchSchemeActive() {
        if (!activator.isActive()) activator.forceActivate();
        else activator.forceDeactivate();
    }

    @Override
    public String serialize() {
        // TODO: нестабильно. нужны состояния
        return ""; // Serializer.serialize(cells);
    }

    @Override
    public void deserialize(final String data) {
        // TODO: нестабильно. нужны состояния
        // cells = Serializer.deserialize(data, Cell[][].class);
    }

    public void update() {
        System.out.println("here");
        if (!deferredEvents.isEmpty()) {
            final List<Runnable> deferredEvents = this.deferredEvents;
            this.deferredEvents = new ArrayList<>(); // fast clear
            for (final Runnable event : deferredEvents) event.run();
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
        public CombinedTextureRegions getTextureRegion() {
            return cellState.getTexture();
        }

        /** @return новое состояние клетки, повернутой против часовой стрелки на 90 градусов */
        public CellState getRotated() {
            return cellState.getRotated();
        }

        /** Устанавливает новое состояние этой клетке */
        public void setCellState(final CellState newCellState) {
            final CellState old = cellState;
            cellState = newCellState;
            if (old.isActive()) old.forceActivate();
        }

        /** Активирует клетку */
        public void activate(final Cell from, final Direction2D fromToThis) {
            board.deferredEvents.add(() -> cellState.activate(from, fromToThis));
        }

        /** Активирует клетку, даже если она уже активирована */
        public void forceActivate() {
            board.deferredEvents.add(cellState::forceActivate);
        }

        /** Деактивирует клетку */
        public void deactivate(final Cell from, final Direction2D fromToThis) {
            board.deferredEvents.add(() -> cellState.deactivate(from, fromToThis));
        }

        /** Деактивирует клетку, даже если она уже деактивирована */
        public void forceDeactivate() {
            board.deferredEvents.add(cellState::forceDeactivate);
        }

        /** @return true, если клетка активирована */
        public boolean isActive() {
            return cellState.isActive();
        }

        /** @return true, если клетка генерирует ток */
        public boolean isGenerator() {
            return cellState.isGenerator();
        }

        /** @return true, если клетка может быть соединена с клеткой в указанном направлении */
        public boolean canBeConnected(final Direction2D fromToThis) {
            return cellState.canBeConnected(fromToThis);
        }
    }
}
