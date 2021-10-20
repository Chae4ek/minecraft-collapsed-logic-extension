package ru.omsu.collapsedlogicextension.blocks.logicblock.util.board;

import net.minecraft.block.Block;
import ru.omsu.collapsedlogicextension.blocks.logicblock.gui.Tool;

public abstract class Cell {

    // TODO весь enum Tool перенести вот сюда, таким образом мы избавимся от енума
    protected int x, y; // координаты поля
    protected Block block; // блок который пародирует клетка
    protected int xTex, yTex; // координаты клетки поля на атласе
    protected Tool type;

    public Cell(final int x, final int y) {
        this.x = x;
        this.y = y;
        type = Tool.ERASER;
    }

    void activate(final Direction from, final Direction to) {}

    void deactivate(final Direction from, final Direction to) {}

    void rotate() {}

    public Tool getType() {
        return type;
    }

    public void setType(final Tool type) {
        this.type = type;
    }
}
