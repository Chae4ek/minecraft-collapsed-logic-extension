package ru.omsu.collapsedlogicextension.blocks.logicblock.util.board;

import java.util.HashMap;
import java.util.Map;

public class Wire implements State {

    private boolean isActive;

    private Map<Integer, Boolean> directions;

    public Wire() {
        this.isActive = false;
        directions = new HashMap<>(4);
    }

    @Override
    public void activate(Direction from) {
        isActive = true;
        for(Map.Entry<Integer, Boolean> entry : directions.entrySet()){
            entry.setValue(true);
        }
    }

    @Override
    public void deactivate(Direction from) {
        isActive = false;
        for(Map.Entry<Integer, Boolean> entry : directions.entrySet()){
            entry.setValue(false);
        }
    }

    @Override
    public void addDirection(Direction direction) {
        directions.put(direction.getMeta(), isActive);
    }

    @Override
    public void removeDirection(Direction direction) {
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
    public boolean isConnectableFrom(Direction direction) { return true; }

    @Override
    public boolean isActive() {
        return isActive;
    }

    @Override
    public boolean isActiveAt(Direction direction) {
        return isActive;
    }

}

