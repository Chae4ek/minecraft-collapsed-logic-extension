package ru.omsu.collapsedlogicextension.logicblock.board;

import java.util.Map;
import ru.omsu.collapsedlogicextension.logicblock.board.cellstates.CellState;
import ru.omsu.collapsedlogicextension.logicblock.board.cellstates.EmptyCellState;
import ru.omsu.collapsedlogicextension.logicblock.util.CombinedTextureRegions;
import ru.omsu.collapsedlogicextension.logicblock.util.Direction2D;

public class Cell {

    private CellState cellState = new EmptyCellState();

    /** @return текстура клетки */
    public CombinedTextureRegions getTexture(final Map<Direction2D, Cell> neighbors) {
        return cellState.getTexture(neighbors);
    }

    /** @return новое состояние клетки, повернутой по часовой стрелке на 90 градусов */
    public CellState getRotated() {
        return cellState.getRotated();
    }

    /** @return true, если состояния равны без учета активности входов/выходов */
    public boolean equalsWithoutActive(final CellState state) {
        return cellState.equalsWithoutActive(state);
    }

    /** Устанавливает новое состояние этой клетке */
    public void setCellState(final CellState newCellState) {
        cellState = newCellState;
    }

    public Map<Direction2D, Boolean> getForceActivatedEvents() {
        return cellState.getForceActivatedEvents();
    }

    public Map<Direction2D, Boolean> getForceDeactivatedEvents() {
        return cellState.getForceDeactivatedEvents();
    }

    public Map<Direction2D, Boolean> getActivatedEvents(final Direction2D fromToThis) {
        return cellState.getActivatedEvents(fromToThis);
    }

    public Map<Direction2D, Boolean> getDeactivatedEvents(final Direction2D fromToThis) {
        return cellState.getDeactivatedEvents(fromToThis);
    }

    public boolean isActivate(final Direction2D fromThisTo) {
        return cellState.isActivate(fromThisTo);
    }

    /** @return true, если клетка может быть соединена с клеткой в указанном направлении */
    public boolean canBeConnected(final Direction2D fromToThis) {
        return cellState.canBeConnected(fromToThis);
    }
}
