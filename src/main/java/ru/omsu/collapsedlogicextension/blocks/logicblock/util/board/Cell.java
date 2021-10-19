package ru.omsu.collapsedlogicextension.blocks.logicblock.util.board;

import net.minecraft.block.Block;
import ru.omsu.collapsedlogicextension.blocks.logicblock.gui.Tool;

public abstract class Cell {

    //TODO весь enum Tool перенести вот сюда, таким образом мы избавимся от енума
    protected int x, y; //координаты поля
    protected Block block; //блок который пародирует клетка
    protected int xTex, yTex; //координаты клетки поля на атласе
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

    void rotate(){
    }


    public Tool getType() {
        return type;
    }

    public void setType(Tool type) {
        this.type = type;
    }
}
