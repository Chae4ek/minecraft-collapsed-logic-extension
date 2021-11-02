package ru.omsu.collapsedlogicextension.blocks.logicblock.util.board;

public class OperatorXor extends Accumulator{

    public OperatorXor(){
        super();
    }

    @Override
    public void activate(Direction from) {
        super.activate(from);
        if(allInputsActive()){
            isOutputActive = false;
        }
        else{
            isOutputActive = true;
        }
    }

    @Override
    public void deactivate(Direction from) {
        super.deactivate(from);
        if(allInputsNotActive()){
            isOutputActive = false;
        }
        else{
            isOutputActive = true;
        }
    }

    @Override
    public int getXTex() {
        return 51;
    }

    @Override
    public String getType() {
        return "OPERATOR XOR";
    }
}
