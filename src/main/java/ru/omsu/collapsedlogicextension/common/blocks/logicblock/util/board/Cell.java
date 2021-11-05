package ru.omsu.collapsedlogicextension.common.blocks.logicblock.util.board;

public class Cell {

    private State state;

    public Cell() {
        state = new EmptyCell();
    }

    public State getState() {
        return state;
    }

    public void changeState(final State state) {
        this.state = state;
    }
}
