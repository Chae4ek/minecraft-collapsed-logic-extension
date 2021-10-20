package ru.omsu.collapsedlogicextension.blocks.logicblock.util.board;


public class OperatorAnd extends Accumulator{
    public OperatorAnd(int x, int y) {
        super(x, y);
    }

    @Override
    public void activate(Direction from, Direction to) {
        super.activate(from, to);
    }

    @Override
    public void deactivate(Direction from, Direction to) {
        super.deactivate(from, to);
    }

    /**
     * по часовой на 90 градусов
     */
}
