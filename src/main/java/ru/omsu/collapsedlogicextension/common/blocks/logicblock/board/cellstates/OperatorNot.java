package ru.omsu.collapsedlogicextension.common.blocks.logicblock.board.cellstates;

import ru.omsu.collapsedlogicextension.common.blocks.logicblock.board.Board.Cell;
import ru.omsu.collapsedlogicextension.common.blocks.logicblock.util.CombinedTextureRegions;
import ru.omsu.collapsedlogicextension.common.blocks.logicblock.util.Direction2D;

public class OperatorNot extends CellState {

    private Direction2D output = Direction2D.UP;
    private Direction2D input = Direction2D.DOWN;

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
    public void activate(final Cell from, final Direction2D fromToThis) {
        if (isActive && fromToThis.opposite() == input) forceDeactivate();
    }

    @Override
    public void forceActivate() {
        isActive = true;
        final Cell outputCell = parent.getCell(output);
        if (outputCell.canBeConnected(output)) outputCell.activate(parent, output);
    }

    @Override
    public void deactivate(final Cell from, final Direction2D fromToThis) {
        if (!isActive && fromToThis.opposite() == input) forceActivate();
        else if (isActive && fromToThis.opposite() == output) {
            final Cell outputCell = parent.getCell(output);
            if (outputCell.canBeConnected(output)) outputCell.activate(parent, output);
        }
    }

    @Override
    public void forceDeactivate() {
        isActive = false;
        final Cell outputCell = parent.getCell(output);
        if (outputCell.canBeConnected(output)) outputCell.deactivate(parent, output);
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
