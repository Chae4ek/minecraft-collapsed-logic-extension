package ru.omsu.collapsedlogicextension.logicblock.board.cellstates;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;
import ru.omsu.collapsedlogicextension.logicblock.board.Cell;
import ru.omsu.collapsedlogicextension.logicblock.util.CombinedTextureRegions;
import ru.omsu.collapsedlogicextension.logicblock.util.Direction2D;

public class Activator implements CellState {

    private static final int ATLAS_TEXTURE_X = 153;
    private static final int ATLAS_TEXTURE_ACTIVE_Y = 17;
    private static final int ATLAS_TEXTURE_DEACTIVE_Y = 0;

    private boolean isActive;

    @Override
    public CombinedTextureRegions getTexture(final Map<Direction2D, Cell> neighbors) {
        return new CombinedTextureRegions(
                ATLAS_TEXTURE_X, isActive ? ATLAS_TEXTURE_ACTIVE_Y : ATLAS_TEXTURE_DEACTIVE_Y);
    }

    @Override
    public CellState getRotated() {
        return this;
    }

    @Override
    public Map<Direction2D, Boolean> getUpdatedEvents(final Map<Direction2D, Cell> neighbors) {
        return isActive ? getForceActivatedEvents() : getForceDeactivatedEvents();
    }

    @Override
    public Map<Direction2D, Boolean> getActivatedEvents(final Direction2D fromToThis) {
        return Collections.emptyMap();
    }

    @Override
    public Map<Direction2D, Boolean> getForceActivatedEvents() {
        isActive = true;
        final Map<Direction2D, Boolean> events = new EnumMap<>(Direction2D.class);
        for (final Direction2D direction : Direction2D.values()) events.put(direction, true);
        return events;
    }

    @Override
    public Map<Direction2D, Boolean> getDeactivatedEvents(final Direction2D fromToThis) {
        return isActive
                ? Collections.singletonMap(fromToThis.opposite(), true)
                : Collections.emptyMap();
    }

    @Override
    public Map<Direction2D, Boolean> getForceDeactivatedEvents() {
        isActive = false;
        final Map<Direction2D, Boolean> events = new EnumMap<>(Direction2D.class);
        for (final Direction2D direction : Direction2D.values()) events.put(direction, false);
        return events;
    }

    @Override
    public boolean isActivate(final Direction2D fromThisTo) {
        return isActive;
    }

    @Override
    public boolean canBeConnected(final Direction2D direction) {
        return true;
    }

    @Override
    public boolean equalsWithoutActive(final CellState state) {
        return this == state || state != null && getClass() == state.getClass();
    }
}
