package ru.omsu.collapsedlogicextension.blocks.logicblock.util.board;

import ru.omsu.collapsedlogicextension.blocks.logicblock.gui.Tool;

import java.util.HashMap;
import java.util.Map;

public abstract class Cell {

    //TODO весь enum Tool перенести вот сюда, таким образом мы избавимся от енума
    protected int x, y; //координаты поля
    protected Tool type;

    private Map<Direction, Boolean> directions;

    public Cell(int x, int y){
        this.x = x;
        this.y = y;
        this.type = Tool.ERASER;
        directions = new HashMap<>(4);
    }

    void activate(Direction from, Direction to){
    }
    void activate(Direction from){}
    void deactivate(Direction from, Direction to){
    }

    void rotate() {}

    public Tool getType() {
        return type;
    }

    public void setType(Tool type) {
        this.type = type;
    }

    public Map<Direction, Boolean> getDirections(){
        return directions;
    }

}
