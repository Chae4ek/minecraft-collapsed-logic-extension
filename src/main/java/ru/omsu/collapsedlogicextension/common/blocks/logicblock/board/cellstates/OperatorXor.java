package ru.omsu.collapsedlogicextension.common.blocks.logicblock.board.cellstates;

import ru.omsu.collapsedlogicextension.common.blocks.logicblock.board.Board.Cell;
import ru.omsu.collapsedlogicextension.common.blocks.logicblock.util.BakedTexture;
import ru.omsu.collapsedlogicextension.common.blocks.logicblock.util.Direction2D;
import ru.omsu.collapsedlogicextension.common.blocks.logicblock.util.TextureRegion;

public class OperatorXor extends CellState {

    /*private Direction2D input1;
    private Direction2D input2;

    private boolean isFirstInputActive;
    private boolean isSecondInputActive;*/

    public OperatorXor(final Cell parent) {
        super(parent);
        /*input1 = Direction2D.RIGHT;
        input2 = Direction2D.LEFT;
        isFirstInputActive = false;
        isSecondInputActive = false;*/
    }

    @Override
    public BakedTexture getTexture() {
        return null;
    }

    @Override
    public CellState getRotated() {
        return null;
    }

    @Override
    public void activate(final Cell from, final Direction2D fromToThis) {}

    @Override
    public void deactivate(final Cell from, final Direction2D fromToThis) {}

    @Override
    public void deactivateAllForce() {}

    @Override
    public boolean isGenerator() {
        return false;
    }

    @Override
    public boolean canBeConnectedFrom(Direction2D direction) {
        return false;
    }

}
