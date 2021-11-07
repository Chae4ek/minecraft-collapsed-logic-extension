package ru.omsu.collapsedlogicextension.common.blocks.logicblock.board.cellstates;

import ru.omsu.collapsedlogicextension.common.blocks.logicblock.board.Board.Cell;
import ru.omsu.collapsedlogicextension.common.blocks.logicblock.util.Direction2D;
import ru.omsu.collapsedlogicextension.common.blocks.logicblock.util.TextureRegion;

public class OperatorNot extends CellState {

    public OperatorNot(final Cell parent) {
        super(parent);
        /*directions.put(output, true);
        directions.put(Direction2D.oppositeOf(output), false);*/
    }

    @Override
    public TextureRegion getTextureRegion() {
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
