package ru.omsu.collapsedlogicextension.common.blocks.logicblock.board.cellstates;

import net.minecraft.block.ComparatorBlock;
import net.minecraft.block.RedstoneBlock;
import ru.omsu.collapsedlogicextension.common.blocks.logicblock.board.Board.Cell;
import ru.omsu.collapsedlogicextension.common.blocks.logicblock.util.CombinedTextureRegions;
import ru.omsu.collapsedlogicextension.common.blocks.logicblock.util.Direction2D;

public abstract class CellState {

    protected final transient Cell parent;
    protected boolean isActive;

    public CellState(final Cell parent) {
        this.parent = parent;
    }

    /** @return текстура клетки */
    public abstract CombinedTextureRegions getTexture();

    /** @return новое состояние клетки, повернутой против часовой стрелки на 90 градусов */
    public abstract CellState getRotated();

    /** Активирует клетку */
    public abstract void activate(Cell from, Direction2D fromToThis);

    /** Активирует клетку, даже если она уже активирована */
    public abstract void forceActivate();

    /** Деактивирует клетку */
    public abstract void deactivate(Cell from, Direction2D fromToThis);

    /** Деактивирует клетку, даже если она уже деактивирована */
    public abstract void forceDeactivate();

    /** @return true, если клетка активирована */
    public final boolean isActive() {
        if(parent.x == 12){
            System.out.println("12 " + parent.y + " " + isActive);
        }
        return isActive;
    }

    /** @return true если клетка может проводить ток */
    public abstract boolean isConductive();

    /** @return true, если клетка может быть соединена с клеткой в указанном направлении */
    public abstract boolean canBeConnected(Direction2D fromToThis);
}
