package ru.omsu.collapsedlogicextension.common.blocks.logicblock.board.cellstates;

import ru.omsu.collapsedlogicextension.common.blocks.logicblock.board.Board.Cell;
import ru.omsu.collapsedlogicextension.common.blocks.logicblock.util.CombinedTextureRegions;
import ru.omsu.collapsedlogicextension.common.blocks.logicblock.util.Direction2D;

public class OperatorNot extends CellState {

    public OperatorNot(final Cell parent) {
        super(parent);
        /*directions.put(output, true);
        directions.put(Direction2D.oppositeOf(output), false);*/
    }

    @Override
    public CombinedTextureRegions getTexture() {
        return null;
    }

    @Override
    public CellState getRotated() {
        return null;
    }

    @Override
    public void activate(final Cell from, final Direction2D fromToThis) {}

    @Override
    public void forceActivate() {}

    @Override
    public void deactivate(final Cell from, final Direction2D fromToThis) {}

    @Override
    public void forceDeactivate() {}

    @Override
    public boolean canBeConnected(final Direction2D direction) {
        return false;
    }

    /*@Override
    public void activate(final Direction2D from, final boolean isInputActive) {
        directions.replace(from, isInputActive);
        directions.replace(Direction2D.oppositeOf(from), !isInputActive);
    }

    @Override
    public int getXTex() {
        return 68;
    }

    @Override
    public String getType() {
        return "OPERATOR NOT";
    }

    @Override
    public boolean isActiveAt(final Direction2D direction2D) {
        return directions.get(direction2D);
    }*/
}
