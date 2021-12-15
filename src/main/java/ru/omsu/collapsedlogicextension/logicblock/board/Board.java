package ru.omsu.collapsedlogicextension.logicblock.board;

import java.util.EnumMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.function.Supplier;
import ru.omsu.collapsedlogicextension.logicblock.board.cellstates.Activator;
import ru.omsu.collapsedlogicextension.logicblock.board.cellstates.CellState;
import ru.omsu.collapsedlogicextension.logicblock.board.tools.Tool;
import ru.omsu.collapsedlogicextension.logicblock.util.CombinedTextureRegions;
import ru.omsu.collapsedlogicextension.logicblock.util.Direction2D;

public class Board {

    /** Стартовая клетка для активации схемы */
    private final Cell activator = new Cell();

    private final int activatorX = -1, activatorY = 4;

    private Cell[][] cells = new Cell[9][13];
    private Queue<Runnable> deferredCellUpdate = new LinkedList<>();

    public Board() {
        activator.setCellState(new Activator());
        for (int y = 0; y < cells.length; ++y) {
            for (int x = 0; x < cells[0].length; ++x) {
                cells[y][x] = new Cell();
            }
        }
    }

    public Board(final Board board){
        cells = board.cells;
        deferredCellUpdate = board.deferredCellUpdate;
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
                        ? activator.forceDeactivate()
                        : activator.forceActivate());
    }

    private Map<Direction2D, Cell> getNeighbors(final int fromX, final int fromY) {
        final Map<Direction2D, Cell> neighbors = new EnumMap<>(Direction2D.class);
        for (final Direction2D connectedDirection : Direction2D.values()) {
            neighbors.put(connectedDirection, getCell(fromX, fromY, connectedDirection));
        }
        return neighbors;
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
}
