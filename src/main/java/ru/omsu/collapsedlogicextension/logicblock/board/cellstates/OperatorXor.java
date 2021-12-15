package ru.omsu.collapsedlogicextension.logicblock.board.cellstates;

import java.util.Collections;
import java.util.Map;
import ru.omsu.collapsedlogicextension.logicblock.board.Cell;
import ru.omsu.collapsedlogicextension.logicblock.util.CombinedTextureRegions;
import ru.omsu.collapsedlogicextension.logicblock.util.Direction2D;

public class OperatorXor implements CellState {

    private static final int ATLAS_TEXTURE_X = 51;
    private static final int ATLAS_TEXTURE_Y = 17;

    private Direction2D inputLeft = Direction2D.LEFT;
    private Direction2D inputRight = Direction2D.RIGHT;
    private Direction2D output = Direction2D.UP;

    private boolean firstInputActive, secondInputActive, outputActive;

    @Override
    public CombinedTextureRegions getTexture(final Map<Direction2D, Cell> neighbors) {
        final int ATLAS_TEXTURE_WITH_ROTATION = ATLAS_TEXTURE_Y * output.id;
        return new CombinedTextureRegions(ATLAS_TEXTURE_X, ATLAS_TEXTURE_WITH_ROTATION);
    }

    @Override
    public CellState getRotated() {
        final OperatorXor newState = new OperatorXor();
        newState.output = output.rotate();
        newState.inputLeft = inputLeft.rotate();
        newState.inputRight = inputRight.rotate();
        return newState;
    }

    @Override
    public Map<Direction2D, Boolean> getUpdatedEvents(final Map<Direction2D, Cell> neighbors) {
        firstInputActive = neighbors.get(inputLeft).isActivate(inputLeft.opposite());
        secondInputActive = neighbors.get(inputRight).isActivate(inputRight.opposite());
        outputActive = firstInputActive ^ secondInputActive;
        return outputActive ? getForceActivatedEvents() : getForceDeactivatedEvents();
    }

    @Override
    public Map<Direction2D, Boolean> getActivatedEvents(final Direction2D fromToThis) {
        final Direction2D fromThisTo = fromToThis.opposite();
        if (fromThisTo == inputLeft && !firstInputActive) firstInputActive = true;
        else if (fromThisTo == inputRight && !secondInputActive) secondInputActive = true;

        outputActive = firstInputActive ^ secondInputActive;
        return outputActive ? getForceActivatedEvents() : getForceDeactivatedEvents();
    }

    @Override
    public Map<Direction2D, Boolean> getForceActivatedEvents() {
        outputActive = true;
        return Collections.singletonMap(output, true);
    }

    @Override
    public Map<Direction2D, Boolean> getDeactivatedEvents(final Direction2D fromToThis) {
        final Direction2D fromThisTo = fromToThis.opposite();
        if (fromThisTo == inputLeft && firstInputActive) firstInputActive = false;
        else if (fromThisTo == inputRight && secondInputActive) secondInputActive = false;
        else if (fromThisTo == output && outputActive) return getForceActivatedEvents();

        outputActive = firstInputActive ^ secondInputActive;
        return outputActive ? getForceActivatedEvents() : getForceDeactivatedEvents();
    }

    @Override
    public Map<Direction2D, Boolean> getForceDeactivatedEvents() {
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
        return fromThisTo == output || fromThisTo == inputLeft || fromThisTo == inputRight;
    }

    @Override
    public boolean equalsWithoutActive(final CellState state) {
        if (this == state) return true;
        if (state == null || getClass() != state.getClass()) return false;
        final OperatorXor that = (OperatorXor) state;
        return inputLeft == that.inputLeft
                && inputRight == that.inputRight
                && output == that.output;
    }
}
