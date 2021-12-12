package ru.omsu.collapsedlogicextension.logicblock.board.cellstates;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.omsu.collapsedlogicextension.logicblock.board.Board.Cell;
import ru.omsu.collapsedlogicextension.logicblock.util.CombinedTextureRegions;
import ru.omsu.collapsedlogicextension.logicblock.util.Direction2D;
import ru.omsu.collapsedlogicextension.logicblock.util.TextureRegion;

public class Wire extends CellState {

    private boolean isActive;

    public Wire(final Cell parent) {
        super(parent);
    }

    @Override
    public CombinedTextureRegions getTexture() {
        final List<TextureRegion> parts = new ArrayList<>(5);
        parts.add(new TextureRegion(85, isActive ? 17 : 0));
        for (final Direction2D direction : Direction2D.values()) {
            if (parent.getCell(direction).canBeConnected(direction)) {
                parts.add(new TextureRegion(102 + (isActive ? 17 : 0), 17 * direction.id));
            }
        }
        return new CombinedTextureRegions(parts);
    }

    @Override
    public CellState getRotated() {
        return this;
    }

    @Override
    public Map<Direction2D, Boolean> update() {
        for (final Direction2D connectedDirection : Direction2D.values()) {
            final Cell connectedCell = parent.getCell(connectedDirection);
            if (connectedCell.isActivate(connectedDirection.opposite())) {
                return forceActivate();
            }
        }
        return forceDeactivate();
    }

    @Override
    public Map<Direction2D, Boolean> activate(final Direction2D fromToThis) {
        if(!isActive) {
            isActive = true;
            final Direction2D from = fromToThis.opposite();
            Map<Direction2D, Boolean> map = new HashMap<>(3);
            for (Direction2D direction : Direction2D.values()) {
                if (direction != from) {
                    map.put(direction, true);
                }
            }
            return map;
        }
        return new HashMap<>();
    }

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
        if(isActive) {
            isActive = false;
            final Direction2D from = fromToThis.opposite();
            Map<Direction2D, Boolean> map = new HashMap<>(3);
            for (Direction2D direction : Direction2D.values()) {
                if (direction != from) {
                    map.put(direction, false);
                }
            }
            return map;
        }
        return new HashMap<>();
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
    public boolean canBeConnected(final Direction2D fromToThis) {
        return true;
    }

    @Override
    public boolean equalsWithoutActive(final CellState state) {
        return this == state || state != null && getClass() == state.getClass();
    }
}
