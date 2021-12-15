package ru.omsu.collapsedlogicextension.logicblock.board.cellstates;

import java.util.Map;
import ru.omsu.collapsedlogicextension.logicblock.board.Cell;
import ru.omsu.collapsedlogicextension.logicblock.util.CombinedTextureRegions;
import ru.omsu.collapsedlogicextension.logicblock.util.Direction2D;

public interface CellState {

    /** @return текстура клетки */
    CombinedTextureRegions getTexture(Map<Direction2D, Cell> neighbors);

    /** @return новое состояние клетки, повернутой по часовой стрелке на 90 градусов */
    CellState getRotated();

    /** @return события для активации или деактивации клетки в зависимости от соседних клеток */
    Map<Direction2D, Boolean> getUpdatedEvents(Map<Direction2D, Cell> neighbors);

    /** @return события для активации клетки */
    Map<Direction2D, Boolean> getActivatedEvents(Direction2D fromToThis);

    /** @return события для насильной активации клетки */
    Map<Direction2D, Boolean> getForceActivatedEvents();

    /** @return события для деактивации клетки */
    Map<Direction2D, Boolean> getDeactivatedEvents(Direction2D fromToThis);

    /** @return события для насильной деактивации клетки */
    Map<Direction2D, Boolean> getForceDeactivatedEvents();

    /** @return true, если клетка активирует клетку в заданном направлении */
    boolean isActivate(Direction2D fromThisTo);

    /** @return true, если клетка может быть соединена с клеткой в указанном направлении */
    boolean canBeConnected(Direction2D fromToThis);

    /** @return true, если состояния равны без учета активности входов/выходов */
    boolean equalsWithoutActive(CellState state);
}
