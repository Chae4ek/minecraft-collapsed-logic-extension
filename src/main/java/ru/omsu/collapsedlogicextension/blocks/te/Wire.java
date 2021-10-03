package ru.omsu.collapsedlogicextension.blocks.te;

import java.util.Map;

public class Wire implements LogicCell{

    private boolean isActivated;
    private Map<Direction, Boolean> directions;

    public Wire(){

    }

    @Override
    public void activate(LogicCell from, Direction to) {

    }

    @Override
    public void deactivate(LogicCell from, Direction to) {

    }

    public void addDirection(Direction direction){

    }

    public void removeDirection(Direction direction){

    }
}
