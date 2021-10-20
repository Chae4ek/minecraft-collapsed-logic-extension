package ru.omsu.collapsedlogicextension.blocks.logicblock.util.board;

import net.minecraft.block.Block;
import ru.omsu.collapsedlogicextension.blocks.logicblock.gui.Tool;

import java.util.Map;

/**класс занимается логикой борды*/
public class LogicBoardEntity {

    private static Cell[][] cells = new Cell[9][13];

    public LogicBoardEntity(){
        for(int yPos = 0; yPos < 9; yPos++) {
            for (int xPos = 0; xPos < 13; xPos++) {
                cells[yPos][xPos] = new EmptyCell(xPos, yPos);
            }
        }
    }

    public Cell updateBoard(Tool tool, int x, int y){
        //TODO: этот метод боялись даже чеченцы
        if(tool!=Tool.ROTATION) {
            if(tool == Tool.LOGIC_WIRE) {
                cells[y][x] = new Wire(x, y);
            }
            if(tool == Tool.OPERATOR_INPUT){
                cells[y][x] = new OutputOperator(x, y);
            }
            if (tool == Tool.ERASER){
                cells[y][x] = new EmptyCell(x, y);
            }
            cells[y][x].setType(tool);
        }
        if (tool == Tool.ERASER){
            if(x>0 && isWire(x-1, y)) {
                ((Wire)cells[y][x-1]).removeDirection(Direction.EAST);
            }
            if(y>0 && isWire(x, y-1)) {
                ((Wire)cells[y-1][x]).removeDirection(Direction.SOUTH);
            }
            if(x<12 && isWire(x+1, y)) {
                ((Wire)cells[y][x+1]).removeDirection(Direction.WEST);
            }
            if(y<8 && isWire(x, y+1)) {
                ((Wire)cells[y+1][x]).removeDirection(Direction.NORTH);
            }
            if(x == 1 && isOutput(0, y)) {
                ((Wire)cells[y][x]).removeDirection(Direction.WEST);
            }
        }
        if(tool == Tool.LOGIC_WIRE){
            if(x>0 && isWire(x-1, y)) {
                ((Wire)cells[y][x-1]).addDirection(Direction.EAST);
                ((Wire)cells[y][x]).addDirection(Direction.WEST);
            }
            if(y>0 && isWire(x, y-1)) {
                ((Wire)cells[y-1][x]).addDirection(Direction.SOUTH);
                ((Wire)cells[y][x]).addDirection(Direction.NORTH);
            }
            if(x<12 && isWire(x+1, y)) {
                ((Wire)cells[y][x+1]).addDirection(Direction.WEST);
                ((Wire)cells[y][x]).addDirection(Direction.EAST);
            }
            if(y<8 && isWire(x, y+1)) {
                ((Wire)cells[y+1][x]).addDirection(Direction.NORTH);
                ((Wire)cells[y][x]).addDirection(Direction.SOUTH);
            }
            if(x == 1 && isOutput(0, y)) {
                ((Wire)cells[y][x]).addDirection(Direction.WEST);
            }
        }
        return cells[y][x];
    }

    public void activateScheme(int x, int y){

        ((OutputOperator)cells[y][x]).isActivated = true;
        activateScheme(x + 1, y, Direction.WEST);

    }

    private void activateScheme(int x, int y, Direction direction) {
        if (x > 0 && x < 13 && y > 0 && y < 9) {
            System.err.println(x + " " + y);
            cells[y][x].activate(direction);

            for (Map.Entry<Direction, Boolean> entry : ((Wire) cells[y][x]).getDirections().entrySet()) {
                if (entry.getValue() && entry.getKey()!=direction) {
                    activateScheme(x + entry.getKey().getX(), y + entry.getKey().getY(), entry.getKey());
                    break;
                }
            }
        }
    }

    public static Map<Direction, Boolean> getDirections(int x, int y){
        return cells[y][x].getDirections();
    }

    public Block getBlock(int x, int y){
        return cells[y][x].type.getBlock();
    }

    public boolean isWire(int x, int y){
        return cells[y][x] instanceof Wire;
    }

    public boolean isOutput(int x, int y){
        return cells[y][x] instanceof OutputOperator;
    }


}
