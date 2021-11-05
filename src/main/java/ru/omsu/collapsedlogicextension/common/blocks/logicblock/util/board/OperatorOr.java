package ru.omsu.collapsedlogicextension.common.blocks.logicblock.util.board;

public class OperatorOr extends Accumulator {

    public OperatorOr() {
        super();
    }

    @Override
    public void activate(final Direction from) {
        super.activate(from);
        isOutputActive = true;
    }

    @Override
    public void deactivate(final Direction from) {
        super.deactivate(from);
        if (allInputsNotActive()) {
            isOutputActive = false;
        }
    }

    @Override
    public int getXTex() {
        return 34;
    }

    @Override
    public String getType() {
        return "OPERATOR OR";
    }
}
