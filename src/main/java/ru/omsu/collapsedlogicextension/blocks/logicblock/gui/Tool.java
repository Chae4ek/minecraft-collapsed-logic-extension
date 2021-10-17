package ru.omsu.collapsedlogicextension.blocks.logicblock.gui;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

public enum Tool {
    /**
     * первое поле это идентификатор
     * второе и третье это координаты на атласе
     */
    ERASER(null, "ERASER", 0, 0),
    OPERATOR_AND(Blocks.REDSTONE_WIRE, "AND", 17, 0),
    OPERATOR_OR(Blocks.REDSTONE_WIRE, "OR", 17*2, 0),
    OPERATOR_XOR(Blocks.REDSTONE_WIRE, "XOR", 17*3, 0),
    OPERATOR_NOT(Blocks.REDSTONE_WIRE, "NOT", 17*4, 0),
    LOGIC_WIRE(Blocks.REDSTONE_WIRE, "WIRE", 17*5, 0),
    ROTATION(null, "ROTATION", 0, 0);

    private final String type;
    private final Block block;
    private final int xText, yText;
    Tool(Block block, String type, int xText, int yText){
        this.type = type;
        this.xText = xText;
        this.yText = yText;
        this.block = block;
    }

    public String getType() {
        return type;
    }

    public int getX() {
        return xText;
    }

    public int getY() {
        return yText;
    }

    public Block getBlock() {
        return block;
    }
}
