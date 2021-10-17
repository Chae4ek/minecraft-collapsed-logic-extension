package ru.omsu.collapsedlogicextension.blocks.logicblock.util.board;

import java.util.HashMap;
import java.util.Map;

public abstract class Accumulator extends Cell {

    protected Map<Direction, Boolean> outputs;
    protected Map<Direction, Boolean> inputs;

    public Accumulator(int x, int y) {
        super(x, y);
        outputs = new HashMap<>(1);
        inputs = new HashMap<>(2);
    }

    @Override
    public void activate(Direction from, Direction to) {
        inputs.replace(from, true);
    }

    @Override
    public void deactivate(Direction from, Direction to) {
        inputs.replace(from, false);
    }
    /**по часовой на 90 градусов*/
    public void rotate(){

    }
}
