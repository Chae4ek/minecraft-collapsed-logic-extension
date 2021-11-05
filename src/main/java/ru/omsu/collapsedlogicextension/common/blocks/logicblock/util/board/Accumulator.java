package ru.omsu.collapsedlogicextension.common.blocks.logicblock.util.board;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class Accumulator implements State {

    protected Map<Direction, Boolean> directions;
    protected Direction output;
    protected boolean isOutputActive;

    public Accumulator() {
        output = Direction.NORTH;
        directions = new HashMap<>();
        isOutputActive = false;
    }

    @Override
    public void rotate() {
        output = Direction.rotate(output);

        final Map<Boolean, Direction> reversedMap = reverse(directions);

        for (final Map.Entry<Boolean, Direction> entry : reversedMap.entrySet()) {
            entry.setValue(Direction.rotate(entry.getValue()));
        }

        directions = reverse(reversedMap);
        System.out.println(directions);
    }

    @Override
    public void activate(final Direction from) {
        if (from != output) {
            directions.replace(from, true);
        }
    }

    @Override
    public void deactivate(final Direction from) {
        if (from != output) {
            directions.replace(from, false);
        }
    }

    public boolean allInputsActive() {
        for (final Map.Entry<Direction, Boolean> entry : directions.entrySet()) {
            if (entry.getKey() != output && !entry.getValue()) {
                return false;
            }
        }
        return true;
    }

    public boolean allInputsNotActive() {
        for (final Map.Entry<Direction, Boolean> entry : directions.entrySet()) {
            if (entry.getKey() != output && entry.getValue()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean isConnectableFrom(final Direction direction) {
        return direction != Direction.oppositeOf(output);
    }

    @Override
    public boolean isActive() {
        return directions.containsValue(true);
    }

    @Override
    public boolean isActiveAt(final Direction direction) {
        return directions.get(direction);
    }

    @Override
    public boolean isLogical() {
        return true;
    }

    private <T, V> Map<V, T> reverse(final Map<T, V> map) {
        return map.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));
    }
}
