package ru.omsu.collapsedlogicextension.common.blocks.logicblock.board.cells;

import ru.omsu.collapsedlogicextension.common.blocks.logicblock.util.Direction2D;

public class OperatorNot extends Accumulator {

    public OperatorNot() {
        super();
        directions.put(output, true);
        directions.put(Direction2D.oppositeOf(output), false);
    }

    @Override
    public void activate(final Direction2D from, final boolean isInputActive) {
        directions.replace(from, isInputActive);
        directions.replace(Direction2D.oppositeOf(from), !isInputActive);
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
    public boolean isActiveAt(final Direction2D direction2D) {
        return directions.get(direction2D);
    }
}
