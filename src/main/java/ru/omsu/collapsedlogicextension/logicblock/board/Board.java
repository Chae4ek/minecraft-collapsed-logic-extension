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

    private static final int SIZE_WIDTH = 13;
    private static final int SIZE_HEIGHT = 9;
    private static final int BOARD_BORDER_X = -1;

    /** Стартовая клетка для активации схемы */
    private final Cell activator = new Cell();

    private final int activatorX = BOARD_BORDER_X;
    private final int activatorY = SIZE_HEIGHT / 2;

    private final Cell[][] cells = new Cell[SIZE_HEIGHT][SIZE_WIDTH];
    private Queue<Runnable> deferredCellUpdate = new LinkedList<>();

    public Board() {
        activator.setCellState(new Activator());
        for (int y = 0; y < SIZE_HEIGHT; ++y) {
            for (int x = 0; x < SIZE_WIDTH; ++x) {
                cells[y][x] = new Cell();
            }
        }
    }

    public void applyTool(final Tool tool, final int x, final int y) {
        final Cell cell = getCell(x, y);
        final CellState newCellState = tool.apply(cell);
        Map<Direction2D, Boolean> events;
        if (!cell.equalsWithoutActive(newCellState)) {
            events = cell.getForceDeactivatedEvents();
            cell.setCellState(newCellState);
            addEvents(x, y, events);
        }
        update();
        events = newCellState.getUpdatedEvents(getNeighbors(x, y));
        addEvents(x, y, events);
    }

    public void switchSchemeActive() {
        addEvents(
                activatorX,
                activatorY,
                activator.isActivate(Direction2D.RIGHT)
                        ? activator.getForceDeactivatedEvents()
                        : activator.getForceActivatedEvents());
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
        return x < 0 || y < 0 || x >= SIZE_WIDTH || y >= SIZE_HEIGHT ? new Cell() : cells[y][x];
    }

    private Cell getCell(final int fromX, final int fromY, final Direction2D fromTo) {
        return getCell(fromX + fromTo.xShift, fromY + fromTo.yShift);
    }

    /** Обновляет доску с каждым игровым тиком */
    public void update() {
        final Queue<Runnable> cellUpdateForNow = deferredCellUpdate;
        deferredCellUpdate = new LinkedList<>();
        for (final Runnable event : cellUpdateForNow) event.run();
    }

    private void addEvents(
            final int cellX, final int cellY, final Map<Direction2D, Boolean> events) {
        for (final Map.Entry<Direction2D, Boolean> directionToActivate : events.entrySet()) {
            final Cell neighborCell = getCell(cellX, cellY, directionToActivate.getKey());
            if (directionToActivate.getValue()) {
                deferredCellUpdate.add(
                        () ->
                                addEvents(
                                        cellX + directionToActivate.getKey().xShift,
                                        cellY + directionToActivate.getKey().yShift,
                                        neighborCell.getActivatedEvents(
                                                directionToActivate.getKey())));
            } else {
                deferredCellUpdate.add(
                        () ->
                                addEvents(
                                        cellX + directionToActivate.getKey().xShift,
                                        cellY + directionToActivate.getKey().yShift,
                                        neighborCell.getDeactivatedEvents(
                                                directionToActivate.getKey())));
            }
        }
    }
}
