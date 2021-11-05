package ru.omsu.collapsedlogicextension.common.blocks.logicblock.util.board;

public class Cell {

    private State state;

    /** это нужно для избежания stackoverflow в случае когда цепь с циклами */
    private boolean isMarked = false;

    public Cell() {
        state = new EmptyCell();
    }

    public void mark() {
        isMarked = true;
    }

    public boolean isMarked() {
        return isMarked;
    }

    public void unmark() {
        isMarked = false;
    }

    public State getState() {
        return state;
    }

    public void changeState(final State state) {
        this.state = state;
    }
}
