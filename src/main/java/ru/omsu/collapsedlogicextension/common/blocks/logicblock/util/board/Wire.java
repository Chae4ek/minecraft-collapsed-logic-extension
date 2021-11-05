package ru.omsu.collapsedlogicextension.common.blocks.logicblock.util.board;

import java.util.HashMap;
import java.util.Map;

public class Wire implements State {

    private boolean isActive;

    private final Map<Integer, Boolean> directions;

    public Wire() {
        isActive = false;
        directions = new HashMap<>(4);
    }

    @Override
    public void activate(final Direction from) {
        isActive = true;
        for (final Map.Entry<Integer, Boolean> entry : directions.entrySet()) {
            entry.setValue(true);
        }
    }

    @Override
    public void deactivate(final Direction from) {
        isActive = false;
        for (final Map.Entry<Integer, Boolean> entry : directions.entrySet()) {
            entry.setValue(false);
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
    public boolean isActive() {
        return isActive;
    }

    @Override
    public boolean isActiveAt(final Direction direction) {
        return isActive;
    }
}
