package ru.omsu.collapsedlogicextension.blocks.logicblock.util.board;

import ru.omsu.collapsedlogicextension.blocks.logicblock.gui.Tool;

public abstract class Cell {

    protected int x, y;
    protected Tool type;

    public Cell(int x, int y){
        this.x = x;
        this.y = y;
        this.type = Tool.ERASER;
    }

    void activate(Direction from, Direction to){
    }
    void deactivate(Direction from, Direction to){
    }

    public Tool getType() {
        return type;
    }

    public void setType(Tool type) {
        this.type = type;
    }
}
