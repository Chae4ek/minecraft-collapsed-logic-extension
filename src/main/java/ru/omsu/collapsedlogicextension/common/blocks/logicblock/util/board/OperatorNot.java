package ru.omsu.collapsedlogicextension.common.blocks.logicblock.util.board;

public class OperatorNot extends Accumulator {

    public OperatorNot() {
        super();
        directions.put(output, true);
        directions.put(Direction.oppositeOf(output), false);
    }

    @Override
    public void activate(final Direction from, final boolean isInputActive) {
        directions.replace(from, isInputActive);
        directions.replace(Direction.oppositeOf(from), !isInputActive);
    }

    @Override
    public int getXTex() {
        return 68;
    }

    @Override
    public String getType() {
        return "OPERATOR NOT";
    }

    @Override
    public boolean isActiveAt(final Direction direction) {
        return directions.get(direction);
    }
}
