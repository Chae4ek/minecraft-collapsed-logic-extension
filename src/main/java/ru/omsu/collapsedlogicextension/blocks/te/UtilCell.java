package ru.omsu.collapsedlogicextension.blocks.te;

import java.util.Map;

public abstract class UtilCell implements LogicCell{

    protected Map<Direction, Boolean> inputs, outputs;


    @Override
    public void activate(LogicCell from, Direction to) {

    }

    @Override
    public void deactivate(LogicCell from, Direction to) {

    }
    public void rotate(){

    }
    public void conduct(){

    }
}
