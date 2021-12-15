package ru.omsu.collapsedlogicextension.logicblock.board.cellstates;

import java.util.Collections;
import java.util.Map;
import ru.omsu.collapsedlogicextension.logicblock.board.Cell;
import ru.omsu.collapsedlogicextension.logicblock.util.CombinedTextureRegions;
import ru.omsu.collapsedlogicextension.logicblock.util.Direction2D;

public class OperatorNot implements CellState {

    private static final int ATLAS_TEXTURE_X = 68;
    private static final int ATLAS_TEXTURE_Y = 17;

    private Direction2D output = Direction2D.UP;
    private Direction2D input = Direction2D.DOWN;
    private boolean outputActive;

    @Override
    public CombinedTextureRegions getTexture(final Map<Direction2D, Cell> neighbors) {
        final int ATLAS_TEXTURE_WITH_ROTATION = ATLAS_TEXTURE_Y * output.id;
        return new CombinedTextureRegions(ATLAS_TEXTURE_X, ATLAS_TEXTURE_WITH_ROTATION);
    }

    @Override
    public CellState getRotated() {
        final OperatorNot newState = new OperatorNot();
        newState.input = input.rotate();
        newState.output = output.rotate();
        return newState;
    }

    @Override
    public Map<Direction2D, Boolean> getUpdatedEvents(final Map<Direction2D, Cell> neighbors) {
        return neighbors.get(input).isActivate(input.opposite())
                ? getForceDeactivatedEvents()
                : getForceActivatedEvents();
    }

    @Override
    public Map<Direction2D, Boolean> getActivatedEvents(final Direction2D fromToThis) {
        return outputActive && fromToThis.opposite() == input
                ? getForceDeactivatedEvents()
                : Collections.emptyMap();
    }

    @Override
    public Map<Direction2D, Boolean> getForceActivatedEvents() {
        outputActive = true;
        return Collections.singletonMap(output, true);
    }

    @Override
    public Map<Direction2D, Boolean> getDeactivatedEvents(final Direction2D fromToThis) {
        if (!outputActive && fromToThis.opposite() == input) return getForceActivatedEvents();
        if (outputActive && fromToThis.opposite() == output) return getForceActivatedEvents();
        return Collections.emptyMap();
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
