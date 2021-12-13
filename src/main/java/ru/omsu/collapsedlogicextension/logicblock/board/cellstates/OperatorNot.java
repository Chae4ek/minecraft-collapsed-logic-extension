package ru.omsu.collapsedlogicextension.logicblock.board.cellstates;

import java.util.Collections;
import java.util.Map;
import ru.omsu.collapsedlogicextension.logicblock.board.Board.Cell;
import ru.omsu.collapsedlogicextension.logicblock.util.CombinedTextureRegions;
import ru.omsu.collapsedlogicextension.logicblock.util.Direction2D;

public class OperatorNot implements CellState {

    private Direction2D output = Direction2D.UP;
    private Direction2D input = Direction2D.DOWN;
    private boolean outputActive;

    @Override
    public CombinedTextureRegions getTexture(final Map<Direction2D, Cell> neighbors) {
        return new CombinedTextureRegions(68, 17 * output.id);
    }

    @Override
    public CellState getRotated() {
        final OperatorNot newState = new OperatorNot();
        newState.input = input.rotate();
        newState.output = output.rotate();
        return newState;
    }

    @Override
    public Map<Direction2D, Boolean> update(final Map<Direction2D, Cell> neighbors) {
        return neighbors.get(input).isActivate(input.opposite())
                ? forceDeactivate()
                : forceActivate();
    }

    @Override
    public Map<Direction2D, Boolean> activate(final Direction2D fromToThis) {
        return outputActive && fromToThis.opposite() == input
                ? forceDeactivate()
                : Collections.emptyMap();
    }

    @Override
    public Map<Direction2D, Boolean> forceActivate() {
        outputActive = true;
        return Collections.singletonMap(output, true);
    }

    @Override
    public Map<Direction2D, Boolean> deactivate(final Direction2D fromToThis) {
        if (!outputActive && fromToThis.opposite() == input) return forceActivate();
        else if (outputActive && fromToThis.opposite() == output) return forceActivate();
        return Collections.emptyMap();
    }

    @Override
    public Map<Direction2D, Boolean> forceDeactivate() {
        outputActive = false;
        return Collections.singletonMap(output, false);
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
