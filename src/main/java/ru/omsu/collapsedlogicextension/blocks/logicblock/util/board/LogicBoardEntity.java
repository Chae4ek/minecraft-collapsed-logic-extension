package ru.omsu.collapsedlogicextension.blocks.logicblock.util.board;

import ru.omsu.collapsedlogicextension.blocks.logicblock.gui.Tool;

import static ru.omsu.collapsedlogicextension.blocks.logicblock.util.board.Direction.*;

/**класс занимается логикой борды*/
public class LogicBoardEntity {

    private static Cell[][] cells = new Cell[9][13];

    public LogicBoardEntity(){
        for(int yPos = 0; yPos < 9; yPos++) {
            for (int xPos = 0; xPos < 13; xPos++) {
                cells[yPos][xPos] = new Cell();
            }
        }
    }

    public Cell updateBoard(Tool tool, int x, int y){
        cells[y][x].changeState(tool.getConstructor().get());
        updateNeighbors(x, y);
        return cells[y][x];
    }

    public void rotate(int x, int y){
        cells[y][x].getState().rotate();
        updateNeighbors(x, y);
    }

    private boolean notBorder(int x, int y){
        return x < 13 && x >= 0 && y < 9 && y >= 0;
    }

    private void updateNeighbors(int x, int y){
        if(x == 0 && y == 4){
            cells[y][x].getState().addDirection(Direction.WEST);
        }

        for(Direction direction : Direction.values()){
            if(notBorder(x + direction.getX(), y + direction.getY())){
                if(cells[y][x].getState().isConnectableFrom(direction)
                        && cells[y+direction.getY()][x+direction.getX()].getState().isConnectableFrom(oppositeOf(direction))
                        ){
                    cells[y][x].getState().addDirection(direction);
                    cells[y+direction.getY()][x+direction.getX()].getState().addDirection(oppositeOf(direction));
                }
                else {
                    cells[y][x].getState().removeDirection(direction);
                    cells[y+direction.getY()][x+direction.getX()].getState().removeDirection(oppositeOf(direction));
                }
            }
        }

    }

    /**начало в клетке (0, 4)*/
    public void activateScheme() {
        if(cells[4][0].getState().isActive()){
            cells[4][0].getState().deactivate(Direction.EAST);
            deactivateNeighbors(0, 4);
        }
        else {
            cells[4][0].getState().activate(Direction.EAST);
            activateNeighbors(0, 4);
        }
    }
    /**метод рекурсивный*/
    private void activateNeighbors(int x, int y){
        for(Direction direction : Direction.values()){
            if(notBorder(x + direction.getX(), y + direction.getY())){
                if(cells[y][x].getState().isConnectableFrom(direction)
                        && cells[y+direction.getY()][x+direction.getX()].getState().isConnectableFrom(direction)
                        && !cells[y+direction.getY()][x+direction.getX()].getState().isActiveAt(direction)){
                    cells[y+direction.getY()][x+direction.getX()].getState().activate(direction);
                    activateNeighbors(x+direction.getX(), y+direction.getY());
                }
            }
        }
    }
    /**и этот*/
    private void deactivateNeighbors(int x, int y){
        for(Direction direction : Direction.values()){
            if(notBorder(x + direction.getX(), y + direction.getY())){
                if(cells[y][x].getState().isConnectableFrom(direction)
                        && cells[y+direction.getY()][x+direction.getX()].getState().isConnectableFrom(direction)
                        && cells[y+direction.getY()][x+direction.getX()].getState().isActiveAt(oppositeOf(direction))
                        && cells[y][x].getState().isActiveAt(direction)
                        ){
                    cells[y+direction.getY()][x+direction.getX()].getState().deactivate(direction);
                    deactivateNeighbors(x+direction.getX(), y+direction.getY());
                    updateNeighbors(x+direction.getX(), y+direction.getY());
                }
            }
        }
    }
}
