package ru.omsu.collapsedlogicextension.blocks.logicblock.util.board;

import java.util.HashMap;
import java.util.Map;

public class Wire extends Cell {

    boolean isActivated;

    private Map<Direction, Boolean> directions;

    public Wire(int x, int y) {
        super(x, y);
        directions = new HashMap<>(4);
    }

    public Wire() {
        this(0, 0);
    }

    @Override
    public void activate(Direction from, Direction to) {
        directions.replace(from, true);
        directions.replace(to, true);
    }

    @Override
    public void deactivate(Direction from, Direction to) {
        directions.replace(from, false);
        directions.replace(to, false);
    }

    public void addDirection(Direction direction) {
        directions.put(direction, false);
    }

    // TODO: прописать
    public void removeDirection(Direction direction) {
        directions.remove(direction);
    }

    public Map<Direction, Boolean> getDirections() {
        return directions;
    }
}
