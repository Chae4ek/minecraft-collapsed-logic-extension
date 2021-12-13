package ru.omsu.collapsedlogicextension.logicblock.board.cellstates;

import java.util.Map;
import ru.omsu.collapsedlogicextension.logicblock.board.Board.Cell;
import ru.omsu.collapsedlogicextension.logicblock.util.CombinedTextureRegions;
import ru.omsu.collapsedlogicextension.logicblock.util.Direction2D;

public interface CellState {

    /** @return текстура клетки */
    CombinedTextureRegions getTexture(Map<Direction2D, Cell> neighbors);

    /** @return новое состояние клетки, повернутой по часовой стрелке на 90 градусов */
    CellState getRotated();

    /** Активирует или деактивирует клетку в зависимости от соседних клеток */
    Map<Direction2D, Boolean> update(Map<Direction2D, Cell> neighbors);

    /** Активирует клетку */
    Map<Direction2D, Boolean> activate(Direction2D fromToThis);

    /** Активирует клетку и клетки, в которые из этой идет ток */
    Map<Direction2D, Boolean> forceActivate();

    /** Деактивирует клетку */
    Map<Direction2D, Boolean> deactivate(Direction2D fromToThis);

    /** Деактивирует клетку и клетки, в которые из этой идет ток */
    Map<Direction2D, Boolean> forceDeactivate();

    /** @return true, если клетка активирует клетку в заданном направлении */
    boolean isActivate(Direction2D fromThisTo);

    /** @return true, если клетка может быть соединена с клеткой в указанном направлении */
    boolean canBeConnected(Direction2D fromToThis);

    /** @return true, если состояния равны без учета активности входов/выходов */
    boolean equalsWithoutActive(CellState state);
}
