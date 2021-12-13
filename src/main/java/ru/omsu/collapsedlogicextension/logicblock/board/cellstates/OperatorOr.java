package ru.omsu.collapsedlogicextension.logicblock.board.cellstates;

import java.util.Collections;
import java.util.Map;
import ru.omsu.collapsedlogicextension.logicblock.board.Board.Cell;
import ru.omsu.collapsedlogicextension.logicblock.util.CombinedTextureRegions;
import ru.omsu.collapsedlogicextension.logicblock.util.Direction2D;

public class OperatorOr implements CellState {

    private Direction2D input1 = Direction2D.LEFT;
    private Direction2D input2 = Direction2D.RIGHT;
    private Direction2D output = Direction2D.UP;

    private boolean firstInputActive, secondInputActive, outputActive;

    @Override
    public CombinedTextureRegions getTexture(final Map<Direction2D, Cell> neighbors) {
        return new CombinedTextureRegions(34, 17 * output.id);
    }

    @Override
    public CellState getRotated() {
        final OperatorOr newState = new OperatorOr();
        newState.output = output.rotate();
        newState.input1 = input1.rotate();
        newState.input2 = input2.rotate();
        return newState;
    }

    @Override
    public Map<Direction2D, Boolean> update(final Map<Direction2D, Cell> neighbors) {
        firstInputActive = neighbors.get(input1).isActivate(input1.opposite());
        secondInputActive = neighbors.get(input2).isActivate(input2.opposite());
        if (firstInputActive || secondInputActive) return forceActivate();
        return forceDeactivate();
    }

    @Override
    public Map<Direction2D, Boolean> activate(final Direction2D fromToThis) {
        final Direction2D fromThisTo = fromToThis.opposite();
        if (fromThisTo == input1 && !firstInputActive) firstInputActive = true;
        else if (fromThisTo == input2 && !secondInputActive) secondInputActive = true;

        if (firstInputActive || secondInputActive) return forceActivate();
        return forceDeactivate();
    }

    @Override
    public Map<Direction2D, Boolean> forceActivate() {
        outputActive = true;
        return Collections.singletonMap(output, true);
    }

    @Override
    public Map<Direction2D, Boolean> deactivate(final Direction2D fromToThis) {
        final Direction2D fromThisTo = fromToThis.opposite();
        if (fromThisTo == input1 && firstInputActive) firstInputActive = false;
        else if (fromThisTo == input2 && secondInputActive) secondInputActive = false;
        else if (fromThisTo == output && outputActive) return forceActivate();

        if (firstInputActive || secondInputActive) return forceActivate();
        return forceDeactivate();
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
        return fromThisTo == output || fromThisTo == input1 || fromThisTo == input2;
    }

    @Override
    public boolean equalsWithoutActive(final CellState state) {
        if (this == state) return true;
        if (state == null || getClass() != state.getClass()) return false;
        final OperatorOr that = (OperatorOr) state;
        return input1 == that.input1 && input2 == that.input2 && output == that.output;
    }
}
