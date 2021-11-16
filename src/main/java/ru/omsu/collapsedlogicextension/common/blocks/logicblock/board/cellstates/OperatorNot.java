package ru.omsu.collapsedlogicextension.common.blocks.logicblock.board.cellstates;

import ru.omsu.collapsedlogicextension.common.blocks.logicblock.board.Board.Cell;
import ru.omsu.collapsedlogicextension.common.blocks.logicblock.util.CombinedTextureRegions;
import ru.omsu.collapsedlogicextension.common.blocks.logicblock.util.Direction2D;

public class OperatorNot extends CellState {

    private Direction2D output = Direction2D.UP;
    private Direction2D input = Direction2D.DOWN;
    private boolean isOutputActive;

    public OperatorNot(final Cell parent) {
        super(parent);
    }

    @Override
    public CombinedTextureRegions getTexture() {
        return new CombinedTextureRegions(68, 17 * output.id);
    }

    @Override
    public CellState getRotated() {
        final OperatorNot newState = new OperatorNot(parent);
        newState.input = input.rotate();
        newState.output = output.rotate();
        return newState;
    }

    @Override
    public void update() {
        if (parent.getCell(input).isActivate(input.opposite())) forceActivate();
        else forceDeactivate();
    }

    @Override
    public void activate(final Direction2D fromToThis) {
        if (isOutputActive && fromToThis.opposite() == input) forceDeactivate();
    }

    @Override
    public void forceActivate() {
        isOutputActive = true;
        parent.getCell(output).activate(output);
    }

    @Override
    public void deactivate(final Direction2D fromToThis) {
        if (!isOutputActive && fromToThis.opposite() == input) forceActivate();
        else if (isOutputActive && fromToThis.opposite() == output) {
            parent.getCell(output).activate(output);
        }
    }

    @Override
    public void forceDeactivate() {
        isOutputActive = false;
        parent.getCell(output).deactivate(output);
    }

    @Override
    public boolean isActivate(final Direction2D fromThisTo) {
        return isOutputActive && fromThisTo == output;
    }

    @Override
    public boolean canBeConnected(final Direction2D fromToThis) {
        final Direction2D fromThisTo = fromToThis.opposite();
        return fromThisTo == input || fromThisTo == output;
    }

    @Override
    public boolean isConductive() {
        return false;
    }
}
