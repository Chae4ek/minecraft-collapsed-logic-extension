package ru.omsu.collapsedlogicextension.logicblock.board;

import java.util.Collections;
import java.util.Map;
import ru.omsu.collapsedlogicextension.logicblock.board.cellstates.CellState;
import ru.omsu.collapsedlogicextension.logicblock.board.cellstates.EmptyCell;
import ru.omsu.collapsedlogicextension.logicblock.util.CombinedTextureRegions;
import ru.omsu.collapsedlogicextension.logicblock.util.Direction2D;

public class Cell {

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
        return Collections.emptyMap();
    }

    public Map<Direction2D, Boolean> forceActivate() {
        return cellState.forceActivate();
    }

    public Map<Direction2D, Boolean> forceDeactivate() {
        return cellState.forceDeactivate();
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
