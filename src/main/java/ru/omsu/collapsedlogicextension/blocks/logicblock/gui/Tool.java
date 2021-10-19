package ru.omsu.collapsedlogicextension.blocks.logicblock.gui;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import ru.omsu.collapsedlogicextension.blocks.logicblock.util.board.Cell;
import ru.omsu.collapsedlogicextension.blocks.logicblock.util.board.EmptyCell;
import ru.omsu.collapsedlogicextension.blocks.logicblock.util.board.Wire;

import java.util.function.Supplier;

public enum Tool {
    /**
     * первое поле это идентификатор
     * второе и третье это координаты на атласе
     */
    ERASER(null, "ERASER", 0),
    OPERATOR_AND(Blocks.REDSTONE_WIRE, "AND", 17),
    OPERATOR_OR(Blocks.REDSTONE_WIRE, "OR", 34),
    OPERATOR_XOR(Blocks.REDSTONE_WIRE, "XOR", 51),
    OPERATOR_NOT(Blocks.REDSTONE_WIRE, "NOT", 68),
    LOGIC_WIRE(Blocks.REDSTONE_WIRE, "WIRE", 85),
    ROTATION(null, "ROTATION", 0);

    private final String type;
    private final Block block;
    private final int xText;
    Tool(Block block, String type, int xText){
        this.type = type;
        this.xText = xText;
        this.block = block;
    }

    public String getType() {
        return type;
    }

    public int getX() {
        return xText;
    }

    public Block getBlock() {
        return block;
    }
}
