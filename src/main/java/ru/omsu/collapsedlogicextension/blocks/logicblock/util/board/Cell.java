package ru.omsu.collapsedlogicextension.blocks.logicblock.util.board;

public class Cell {

    private State state;

    public Cell(){
        state = new EmptyCell();
    }

    public State getState() {
        return state;
    }

    public void changeState(State state){ this.state = state; }

}
