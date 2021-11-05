package ru.omsu.collapsedlogicextension.common.blocks.logicblock.util.board;

public class OperatorAnd extends Accumulator {

    public OperatorAnd() {
        super();
    }

    @Override
    public void activate(final Direction from) {
        super.activate(from);
        if (allInputsActive()) {
            isOutputActive = true;
        }
    }

    @Override
    public void deactivate(final Direction from) {
        super.deactivate(from);
        isOutputActive = false;
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
