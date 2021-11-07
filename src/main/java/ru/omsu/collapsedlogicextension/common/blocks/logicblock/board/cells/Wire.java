package ru.omsu.collapsedlogicextension.common.blocks.logicblock.board.cells;

import java.util.HashMap;
import java.util.Map;
import ru.omsu.collapsedlogicextension.common.blocks.logicblock.board.Board.Cell;
import ru.omsu.collapsedlogicextension.common.blocks.logicblock.util.Direction2D;

public class Wire extends Cell {

    private final Map<Integer, Boolean> directions;
    private boolean isActive;

    public Wire() {
        isActive = false;
        directions = new HashMap<>(4);
    }

    @Override
    public void activate(final Direction2D from, final boolean isActive) {
        this.isActive = isActive;
        for (final Map.Entry<Integer, Boolean> entry : directions.entrySet()) {
            entry.setValue(isActive);
        }
    }

    @Override
    public void addDirection(final Direction2D direction2D) {
        directions.put(direction2D.getMeta(), isActive);
    }

    @Override
    public void removeDirection(final Direction2D direction2D) {
        directions.remove(direction2D.getMeta());
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
    public boolean isConnectableFrom(final Direction2D direction2D) {
        return true;
    }

    @Override
    public boolean isActiveAt(final Direction2D direction2D) {
        return isActive;
    }
}
