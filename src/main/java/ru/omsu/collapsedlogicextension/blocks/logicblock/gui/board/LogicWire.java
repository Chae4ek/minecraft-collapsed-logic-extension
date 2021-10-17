package ru.omsu.collapsedlogicextension.blocks.logicblock.gui.board;

import net.minecraft.util.ResourceLocation;
import ru.omsu.collapsedlogicextension.blocks.logicblock.util.board.Direction;

import java.util.List;

public class LogicWire {
    List<Direction> directions;

    ResourceLocation TEXTURE = new ResourceLocation("textures/gui/board/field.png");

    public void addDirection(Direction direction){
        directions.add(direction);
    }
}
