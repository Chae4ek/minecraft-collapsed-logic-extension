package ru.omsu.collapsedlogicextension.blocks.logicblock.util.board;


public class OperatorAnd extends Accumulator{

    public OperatorAnd() {
        super();
    }

    @Override
    public void activate(Direction from) {
        super.activate(from);
        if(allInputsActive()){
            isOutputActive = true;
        }
    }

    @Override
    public void deactivate(Direction from) {
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
