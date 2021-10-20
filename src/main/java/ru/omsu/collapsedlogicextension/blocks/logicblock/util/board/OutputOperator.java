package ru.omsu.collapsedlogicextension.blocks.logicblock.util.board;

public class OutputOperator extends Cell{

    Direction direction;
    boolean isActivated;

    public OutputOperator(int x, int y) {
        super(x, y);
        direction = Direction.EAST;
        isActivated = false;
    }

    @Override
    void activate(Direction from, Direction to) {
        isActivated = true;
    }

    @Override
    void deactivate(Direction from, Direction to) {
        isActivated = false;
    }

    public boolean isValidPosition(){
        return x == 0;
    }
}
