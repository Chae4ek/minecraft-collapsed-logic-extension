package ru.omsu.collapsedlogicextension.logicblock.board.cellstates;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import ru.omsu.collapsedlogicextension.logicblock.board.Cell;
import ru.omsu.collapsedlogicextension.logicblock.util.CombinedTextureRegions;
import ru.omsu.collapsedlogicextension.logicblock.util.Direction2D;
import ru.omsu.collapsedlogicextension.logicblock.util.TextureRegion;

public class Wire implements CellState {

    private static final int ATLAS_TEXTURE_X = 85;
    private static final int ATLAS_TEXTURE_ACTIVE_Y = 17;
    private static final int ATLAS_TEXTURE_DEACTIVE_Y = 0;

    private static final int ATLAS_TEXTURE_EDGE_ACTIVE_X = 119;
    private static final int ATLAS_TEXTURE_EDGE_DEACTIVE_X = 102;
    private static final int ATLAS_TEXTURE_PART_Y = 17;

    private static final int TEXTURE_PARTS_COUNT = 5;

    private boolean isActive;

    @Override
    public CombinedTextureRegions getTexture(final Map<Direction2D, Cell> neighbors) {
        final List<TextureRegion> parts = new ArrayList<>(TEXTURE_PARTS_COUNT);
        parts.add(
                new TextureRegion(
                        ATLAS_TEXTURE_X,
                        isActive ? ATLAS_TEXTURE_ACTIVE_Y : ATLAS_TEXTURE_DEACTIVE_Y));

        for (final Map.Entry<Direction2D, Cell> DirectionToneighbor : neighbors.entrySet()) {
            if (DirectionToneighbor.getValue().canBeConnected(DirectionToneighbor.getKey())) {
                final int ATLAS_TEXTURE_WITH_ROTATION =
                        ATLAS_TEXTURE_PART_Y * DirectionToneighbor.getKey().id;
                parts.add(
                        new TextureRegion(
                                isActive
                                        ? ATLAS_TEXTURE_EDGE_ACTIVE_X
                                        : ATLAS_TEXTURE_EDGE_DEACTIVE_X,
                                ATLAS_TEXTURE_WITH_ROTATION));
            }
        }

        return new CombinedTextureRegions(parts);
    }

    @Override
    public CellState getRotated() {
        return this;
    }

    @Override
    public Map<Direction2D, Boolean> getUpdatedEvents(final Map<Direction2D, Cell> neighbors) {
        for (final Direction2D connectedDirection : Direction2D.values()) {
            final Cell connectedCell = neighbors.get(connectedDirection);
            if (connectedCell.isActivate(connectedDirection.opposite())) {
                return getForceActivatedEvents();
            }
        }
        return getForceDeactivatedEvents();
    }

    @Override
    public Map<Direction2D, Boolean> getActivatedEvents(final Direction2D fromToThis) {
        if (!isActive) {
            isActive = true;
            final Direction2D from = fromToThis.opposite();
            final Map<Direction2D, Boolean> events = new EnumMap<>(Direction2D.class);
            for (final Direction2D direction : Direction2D.values()) {
                if (direction != from) {
                    events.put(direction, true);
                }
            }
            return events;
        }
        return Collections.emptyMap();
    }

    @Override
    public Map<Direction2D, Boolean> getForceActivatedEvents() {
        isActive = true;
        final Map<Direction2D, Boolean> events = new EnumMap<>(Direction2D.class);
        for (final Direction2D direction : Direction2D.values()) {
            events.put(direction, true);
        }
        return events;
    }

    @Override
    public Map<Direction2D, Boolean> getDeactivatedEvents(final Direction2D fromToThis) {
        if (isActive) {
            isActive = false;
            final Direction2D from = fromToThis.opposite();
            final Map<Direction2D, Boolean> events = new EnumMap<>(Direction2D.class);
            for (final Direction2D direction : Direction2D.values()) {
                if (direction != from) {
                    events.put(direction, false);
                }
            }
            return events;
        }
        return Collections.emptyMap();
    }

    @Override
    public Map<Direction2D, Boolean> getForceDeactivatedEvents() {
        isActive = false;
        final Map<Direction2D, Boolean> events = new EnumMap<>(Direction2D.class);
        for (final Direction2D direction : Direction2D.values()) {
            events.put(direction, false);
        }
        return events;
    }

    @Override
    public boolean isActivate(final Direction2D fromThisTo) {
        return isActive;
    }

    @Override
    public boolean canBeConnected(final Direction2D fromToThis) {
        return true;
    }

    @Override
    public boolean equalsWithoutActive(final CellState state) {
        return this == state || state != null && getClass() == state.getClass();
    }
}
