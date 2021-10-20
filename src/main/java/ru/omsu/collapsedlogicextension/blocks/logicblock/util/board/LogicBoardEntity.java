package ru.omsu.collapsedlogicextension.blocks.logicblock.util.board;

import net.minecraft.block.Block;
import ru.omsu.collapsedlogicextension.blocks.logicblock.gui.Tool;

/** класс занимается логикой борды */
public class LogicBoardEntity {

    private final Cell[][] cells = new Cell[9][13];

    public LogicBoardEntity() {
        for (int yPos = 0; yPos < 9; yPos++) {
            for (int xPos = 0; xPos < 13; xPos++) {
                cells[yPos][xPos] = new EmptyCell(xPos, yPos);
            }
        }
    }

    public Cell updateBoard(final Tool tool, final int x, final int y) {
        // TODO: привести к адекватному виду без if-ов
        if (tool != Tool.ROTATION) {
            if (tool == Tool.LOGIC_WIRE) {
                cells[y][x] = new Wire(x, y);
            }
            cells[y][x].setType(tool);
        }
        if (tool == Tool.LOGIC_WIRE) {
            if (x > 0 && isWire(x - 1, y) && cells[y][x - 1].type == Tool.LOGIC_WIRE) {
                ((Wire) cells[y][x - 1]).addDirection(Direction.RIGHT);
                ((Wire) cells[y][x]).addDirection(Direction.LEFT);
            }
            if (y > 0 && isWire(x, y - 1) && cells[y - 1][x].type == Tool.LOGIC_WIRE) {
                ((Wire) cells[y - 1][x]).addDirection(Direction.DOWN);
                ((Wire) cells[y][x]).addDirection(Direction.UP);
            }
            if (x < 12 && isWire(x + 1, y) && cells[y][x + 1].type == Tool.LOGIC_WIRE) {
                ((Wire) cells[y][x + 1]).addDirection(Direction.LEFT);
                ((Wire) cells[y][x]).addDirection(Direction.RIGHT);
            }
            if (y < 8 && isWire(x, y + 1) && cells[y + 1][x].type == Tool.LOGIC_WIRE) {
                ((Wire) cells[y + 1][x]).addDirection(Direction.UP);
                ((Wire) cells[y][x]).addDirection(Direction.DOWN);
            }
        }
        return cells[y][x];
    }

    public Block getBlock(final int x, final int y) {
        return cells[y][x].type.getBlock();
    }

    public boolean isWire(final int x, final int y) {
        return cells[y][x] instanceof Wire;
    }

    public void updateNeighborWire(final int x, final int y, final Direction direction) {
        if (cells[y][x].type == Tool.LOGIC_WIRE) {
            ((Wire) cells[y][x]).addDirection(direction);
        }
    }
}
