package ru.omsu.collapsedlogicextension.logicblock.board.cellstates;

import ru.omsu.collapsedlogicextension.logicblock.board.Board.Cell;
import ru.omsu.collapsedlogicextension.logicblock.util.CombinedTextureRegions;
import ru.omsu.collapsedlogicextension.logicblock.util.Direction2D;

public class OperatorNot extends CellState {

    private Direction2D output = Direction2D.UP;
    private Direction2D input = Direction2D.DOWN;
    private boolean outputActive;

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
        if (parent.getCell(input).isActivate(input.opposite())) forceDeactivate();
        else forceActivate();
    }

    @Override
    public void activate(final Direction2D fromToThis) {
        if (outputActive && fromToThis.opposite() == input) forceDeactivate();
    }

    @Override
    public void forceActivate() {
        outputActive = true;
        parent.getCell(output).activate(output);
    }

    @Override
    public void deactivate(final Direction2D fromToThis) {
        if (!outputActive && fromToThis.opposite() == input) forceActivate();
        else if (outputActive && fromToThis.opposite() == output) forceActivate();
    }

    @Override
    public void forceDeactivate() {
        outputActive = false;
        parent.getCell(output).deactivate(output);
    }

    @Override
    public boolean isActivate(final Direction2D fromThisTo) {
        return outputActive && fromThisTo == output;
    }

    @Override
    public boolean canBeConnected(final Direction2D fromToThis) {
        final Direction2D fromThisTo = fromToThis.opposite();
        return fromThisTo == input || fromThisTo == output;
    }

    @Override
    public boolean equalsWithoutActive(final CellState state) {
        if (this == state) return true;
        if (state == null || getClass() != state.getClass()) return false;
        final OperatorNot that = (OperatorNot) state;
        return output == that.output && input == that.input;
    }
}
