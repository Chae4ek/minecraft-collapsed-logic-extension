package ru.omsu.collapsedlogicextension.common.blocks.logicblock.util.board;

import java.util.HashMap;
import java.util.Map;

public class Wire implements State {

    private final Map<Integer, Boolean> directions;
    private boolean isActive;

    public Wire() {
        isActive = false;
        directions = new HashMap<>(4);
    }

    @Override
    public void activate(final Direction from, final boolean isActive) {
        this.isActive = isActive;
        for (final Map.Entry<Integer, Boolean> entry : directions.entrySet()) {
            entry.setValue(isActive);
        }
    }

    @Override
    public void addDirection(final Direction direction) {
        directions.put(direction.getMeta(), isActive);
    }

    @Override
    public void removeDirection(final Direction direction) {
        directions.remove(direction.getMeta());
    }

    @Override
    public Map<Integer, Boolean> getDirections() {
        return directions;
    }

    @Override
    public int getXTex() {
        return 85;
    }

    @Override
    public String getType() {
        return "WIRE";
    }

    @Override
    public boolean isConnectableFrom(final Direction direction) {
        return true;
    }

    @Override
    public boolean isActiveAt(final Direction direction) {
        return isActive;
    }
}
