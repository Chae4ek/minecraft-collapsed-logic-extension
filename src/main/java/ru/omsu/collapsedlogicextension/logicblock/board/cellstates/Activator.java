package ru.omsu.collapsedlogicextension.logicblock.board.cellstates;

import ru.omsu.collapsedlogicextension.logicblock.board.Board.Cell;
import ru.omsu.collapsedlogicextension.logicblock.util.CombinedTextureRegions;
import ru.omsu.collapsedlogicextension.logicblock.util.Direction2D;

import java.util.HashMap;
import java.util.Map;

public class Activator extends CellState {

    private boolean isActive;

    public Activator(final Cell parent) {
        super(parent);
    }

    @Override
    public CombinedTextureRegions getTexture() {
        return new CombinedTextureRegions(153, isActive ? 17 : 0);
    }

    @Override
    public CellState getRotated() {
        return this;
    }

    @Override
    public Map<Direction2D, Boolean> update() {
        if (isActive) return forceActivate();
        return forceDeactivate();
    }

    @Override
    public Map<Direction2D, Boolean> activate(final Direction2D fromToThis) {return forceActivate();}

    @Override
    public Map<Direction2D, Boolean> forceActivate() {
        isActive = true;
        Map<Direction2D, Boolean> map = new HashMap<>(4);
        for (final Direction2D direction : Direction2D.values()) {
            map.put(direction, true);
        }
        return map;
    }

    @Override
    public Map<Direction2D, Boolean> deactivate(final Direction2D fromToThis) {
        return forceDeactivate();
    }

    @Override
    public Map<Direction2D, Boolean> forceDeactivate() {
        isActive = false;
        Map<Direction2D, Boolean> map = new HashMap<>(4);
        for (final Direction2D direction : Direction2D.values()) {
            map.put(direction, true);
        }
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
