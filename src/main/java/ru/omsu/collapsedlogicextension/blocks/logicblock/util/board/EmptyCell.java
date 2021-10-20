package ru.omsu.collapsedlogicextension.blocks.logicblock.util.board;

public class EmptyCell extends Cell {
    public EmptyCell(int x, int y) {
        super(x, y);
    }

    public EmptyCell() {
        this(0, 0);
    }
}
