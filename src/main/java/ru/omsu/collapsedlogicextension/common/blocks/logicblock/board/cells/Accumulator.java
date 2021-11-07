package ru.omsu.collapsedlogicextension.common.blocks.logicblock.board.cells;

import java.util.HashMap;
import java.util.Map;
import ru.omsu.collapsedlogicextension.common.blocks.logicblock.board.Board.Cell;
import ru.omsu.collapsedlogicextension.common.blocks.logicblock.util.Direction2D;

public abstract class Accumulator extends Cell {

    protected Map<Direction2D, Boolean> directions;
    protected Direction2D output;
    protected boolean isOutputActive;

    public Accumulator() {
        output = Direction2D.UP;
        directions = new HashMap<>();
        isOutputActive = false;
    }

    @Override
    public void rotate() {
        output = Direction2D.rotate(output);
        System.out.println(directions);
        final Map<Boolean, Direction2D> reversedMap = reverse(directions);

        for (final Map.Entry<Boolean, Direction2D> entry : reversedMap.entrySet()) {
            entry.setValue(Direction2D.rotate(entry.getValue()));
        }

        directions = reverse(reversedMap);
        System.out.println(directions);
    }

    @Override
    public boolean isConnectableFrom(final Direction2D direction2D) {
        return directions.containsKey(direction2D);
    }

    @Override
    public Map<Integer, Boolean> getDirections() {
        final Map<Integer, Boolean> map = new HashMap<>();
        for (final Map.Entry<Direction2D, Boolean> entry : directions.entrySet()) {
            map.put(entry.getKey().getMeta(), entry.getValue());
        }
        return map;
    }

    @Override
    public boolean isActiveAt(final Direction2D direction2D) {
        return directions.get(direction2D);
    }

    private <K, V> HashMap<V, K> reverse(final Map<K, V> map) {
        final HashMap<V, K> rev = new HashMap<>();
        for (final Map.Entry<K, V> entry : map.entrySet())
            rev.put(entry.getValue(), entry.getKey());
        return rev;
    }
}
