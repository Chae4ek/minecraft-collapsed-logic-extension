package ru.omsu.collapsedlogicextension.logicblock.board.cellstates;

import java.util.Collections;
import java.util.Map;
import ru.omsu.collapsedlogicextension.logicblock.board.Cell;
import ru.omsu.collapsedlogicextension.logicblock.util.CombinedTextureRegions;
import ru.omsu.collapsedlogicextension.logicblock.util.Direction2D;

public class EmptyCellState implements CellState {

    private static final int ATLAS_TEXTURE_X = 0;
    private static final int ATLAS_TEXTURE_Y = 0;

    private static final CombinedTextureRegions texture =
            new CombinedTextureRegions(ATLAS_TEXTURE_X, ATLAS_TEXTURE_Y);

    @Override
    public CombinedTextureRegions getTexture(final Map<Direction2D, Cell> neighbors) {
        return texture;
    }

    @Override
    public CellState getRotated() {
        return this;
    }

    @Override
    public Map<Direction2D, Boolean> getUpdatedEvents(final Map<Direction2D, Cell> neighbors) {
        return Collections.emptyMap();
    }

    @Override
    public Map<Direction2D, Boolean> getActivatedEvents(final Direction2D fromToThis) {
        return Collections.emptyMap();
    }

    @Override
    public Map<Direction2D, Boolean> getForceActivatedEvents() {
        return Collections.emptyMap();
    }

    @Override
    public Map<Direction2D, Boolean> getDeactivatedEvents(final Direction2D fromToThis) {
        return Collections.emptyMap();
    }

    @Override
    public Map<Direction2D, Boolean> getForceDeactivatedEvents() {
        return Collections.emptyMap();
    }

    @Override
    public boolean isActivate(final Direction2D fromThisTo) {
        return false;
    }

    @Override
    public boolean canBeConnected(final Direction2D fromToThis) {
        return false;
    }

    @Override
    public boolean equalsWithoutActive(final CellState state) {
        return this == state || state != null && getClass() == state.getClass();
    }
}
