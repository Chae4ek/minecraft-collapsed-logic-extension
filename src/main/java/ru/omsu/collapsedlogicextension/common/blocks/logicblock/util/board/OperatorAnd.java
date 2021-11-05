package ru.omsu.collapsedlogicextension.common.blocks.logicblock.util.board;

import java.util.HashMap;
import java.util.Map;

public class OperatorAnd extends Accumulator {

    private Direction input1;
    private Direction input2;

    private boolean isFirstInputActive;
    private boolean isSecondInputActive;

    public OperatorAnd() {
        super();
        input1 = Direction.EAST;
        input2 = Direction.WEST;
        isFirstInputActive = false;
        isSecondInputActive = false;
    }

    @Override
    public void activate(final Direction from, final boolean isActive) {
        if (from == input1) {
            isFirstInputActive = isActive;
        } else if (from == input2) {
            isSecondInputActive = isActive;
        }
        isOutputActive = isFirstInputActive && isSecondInputActive;
    }

    @Override
    public void rotate() {
        output = Direction.rotate(output);
        input1 = Direction.rotate(input1);
        input2 = Direction.rotate(input2);
    }

    @Override
    public boolean isActiveAt(final Direction direction) {
        if (direction == input1) {
            return isFirstInputActive;
        } else if (direction == input2) {
            return isSecondInputActive;
        }
        return isOutputActive;
    }

    @Override
    public Map<Integer, Boolean> getDirections() {
        final Map<Integer, Boolean> map = new HashMap<>();
        map.put(output.getMeta(), isOutputActive);
        map.put(input1.getMeta(), isFirstInputActive);
        map.put(input2.getMeta(), isSecondInputActive);
        return map;
    }

    @Override
    public boolean isConnectableFrom(final Direction direction) {
        return direction != Direction.oppositeOf(output);
    }

    @Override
    public int getXTex() {
        return 17;
    }

    @Override
    public String getType() {
        return "OPERATOR AND";
    }
}
