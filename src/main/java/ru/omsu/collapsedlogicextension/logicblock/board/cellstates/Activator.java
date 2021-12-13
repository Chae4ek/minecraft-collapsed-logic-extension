package ru.omsu.collapsedlogicextension.logicblock.board.cellstates;

import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import ru.omsu.collapsedlogicextension.logicblock.board.Board.Cell;
import ru.omsu.collapsedlogicextension.logicblock.util.CombinedTextureRegions;
import ru.omsu.collapsedlogicextension.logicblock.util.Direction2D;

public class Activator implements CellState {

    private boolean isActive;

    @Override
    public CombinedTextureRegions getTexture(final Map<Direction2D, Cell> neighbors) {
        return new CombinedTextureRegions(153, isActive ? 17 : 0);
    }

    @Override
    public CellState getRotated() {
        return this;
    }

    @Override
    public Map<Direction2D, Boolean> update(final Map<Direction2D, Cell> neighbors) {
        if (isActive) return forceActivate();
        return forceDeactivate();
    }

    @Override
    public Map<Direction2D, Boolean> activate(final Direction2D fromToThis) {
        return new HashMap<>();
    }

    @Override
    public Map<Direction2D, Boolean> forceActivate() {
        isActive = true;
        final Map<Direction2D, Boolean> map = new EnumMap<>(Direction2D.class);
        for (final Direction2D direction : Direction2D.values()) map.put(direction, true);
        return map;
    }

    @Override
    public Map<Direction2D, Boolean> deactivate(final Direction2D fromToThis) {
        if (isActive) return Collections.singletonMap(fromToThis.opposite(), true);
        return Collections.emptyMap();
    }

    @Override
    public Map<Direction2D, Boolean> forceDeactivate() {
        isActive = false;
        final Map<Direction2D, Boolean> map = new EnumMap<>(Direction2D.class);
        for (final Direction2D direction : Direction2D.values()) map.put(direction, false);
        return map;
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
