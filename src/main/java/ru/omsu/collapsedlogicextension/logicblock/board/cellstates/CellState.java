package ru.omsu.collapsedlogicextension.logicblock.board.cellstates;

import java.util.Map;
import ru.omsu.collapsedlogicextension.logicblock.board.Board.Cell;
import ru.omsu.collapsedlogicextension.logicblock.util.CombinedTextureRegions;
import ru.omsu.collapsedlogicextension.logicblock.util.Direction2D;

public abstract class CellState {

    protected final transient Cell parent;

    public CellState(final Cell parent) {
        this.parent = parent;
    }

    /** @return текстура клетки */
    public abstract CombinedTextureRegions getTexture();

    /** @return новое состояние клетки, повернутой по часовой стрелке на 90 градусов */
    public abstract CellState getRotated();

    /** Активирует или деактивирует клетку в зависимости от соседних клеток */
    public abstract Map<Direction2D, Boolean> update();

    /** Активирует клетку */
    public abstract Map<Direction2D, Boolean> activate(Direction2D fromToThis);

    /** Активирует клетку и клетки, в которые из этой идет ток */
    public abstract Map<Direction2D, Boolean> forceActivate();

    /** Деактивирует клетку */
    public abstract Map<Direction2D, Boolean> deactivate(Direction2D fromToThis);

    /** Деактивирует клетку и клетки, в которые из этой идет ток */
    public abstract Map<Direction2D, Boolean> forceDeactivate();

    /** @return true, если клетка активирует клетку в заданном направлении */
    public abstract boolean isActivate(Direction2D fromThisTo);

    /** @return true, если клетка может быть соединена с клеткой в указанном направлении */
    public abstract boolean canBeConnected(Direction2D fromToThis);

    /** @return true, если состояния равны без учета активности входов/выходов */
    public abstract boolean equalsWithoutActive(CellState state);
}
