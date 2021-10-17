package ru.omsu.collapsedlogicextension.blocks.logicblock.util.board;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import ru.omsu.collapsedlogicextension.blocks.logicblock.gui.Tool;
/**класс занимается логикой борды*/
public class LogicBoardEntity {

    private Cell[][] cells = new Cell[9][13];

    public LogicBoardEntity(){
        for(int yPos = 0; yPos < 9; yPos++) {
            for (int xPos = 0; xPos < 13; xPos++) {
                cells[yPos][xPos] = new EmptyCell(xPos, yPos);
            }
        }
    }

    public void updateBoard(Tool tool, int x, int y){
        cells[y][x].setType(tool);
    }

    public Block getBlock(int x, int y){
        return cells[y][x].type.getBlock();
    }

}
