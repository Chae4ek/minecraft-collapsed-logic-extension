package ru.omsu.collapsedlogicextension.common.blocks.logicblock.util.board;

import java.util.HashMap;
import java.util.Map;

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
        System.out.println(directions);
        final Map<Boolean, Direction> reversedMap = reverse(directions);

        for (final Map.Entry<Boolean, Direction> entry : reversedMap.entrySet()) {
            entry.setValue(Direction.rotate(entry.getValue()));
        }

        directions = reverse(reversedMap);
        System.out.println(directions);
    }

    @Override
    public boolean isConnectableFrom(final Direction direction) {
        return directions.containsKey(direction);
    }

    @Override
    public Map<Integer, Boolean> getDirections() {
        final Map<Integer, Boolean> map = new HashMap<>();
        for (final Map.Entry<Direction, Boolean> entry : directions.entrySet()) {
            map.put(entry.getKey().getMeta(), entry.getValue());
        }
        return map;
    }

    @Override
    public boolean isActiveAt(final Direction direction) {
        return directions.get(direction);
    }

    private <K, V> HashMap<V, K> reverse(final Map<K, V> map) {
        final HashMap<V, K> rev = new HashMap<>();
        for (final Map.Entry<K, V> entry : map.entrySet())
            rev.put(entry.getValue(), entry.getKey());
        return rev;
    }
}
