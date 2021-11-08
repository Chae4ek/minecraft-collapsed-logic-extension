package ru.omsu.collapsedlogicextension.common.blocks.logicblock.board.cellstates;

import ru.omsu.collapsedlogicextension.common.blocks.logicblock.board.Board.Cell;
import ru.omsu.collapsedlogicextension.common.blocks.logicblock.util.BakedTexture;
import ru.omsu.collapsedlogicextension.common.blocks.logicblock.util.Direction2D;
import ru.omsu.collapsedlogicextension.common.blocks.logicblock.util.TextureRegion;
import ru.omsu.collapsedlogicextension.util.api.Exclude;

public abstract class CellState {

    @Exclude
    protected final Cell parent;
    protected boolean isActive;

    public CellState(final Cell parent) {
        this.parent = parent;
    }

    /** @return текстура клетки */
    public abstract BakedTexture getTexture();

    /** @return новое состояние клетки, повернутой против часовой стрелки на 90 градусов */
    public abstract CellState getRotated();

    /** Активирует клетку */
    public abstract void activate(Cell from, Direction2D fromToThis);

    /** Деактивирует клетку */
    public abstract void deactivate(Cell from, Direction2D fromToThis);

    /** @return true, если клетка активирована */
    public final boolean isActive() {
        return isActive;
    }

    /** Деактивирует все свои активные направления и саму клетку */
    public abstract void deactivateAllForce();

    /** @return true, если клетка генерирует ток */
    public abstract boolean isGenerator();

    public abstract boolean canBeConnectedFrom(Direction2D direction);
}
