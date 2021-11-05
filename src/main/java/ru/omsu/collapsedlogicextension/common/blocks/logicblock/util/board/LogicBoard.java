package ru.omsu.collapsedlogicextension.common.blocks.logicblock.util.board;

import static ru.omsu.collapsedlogicextension.common.blocks.logicblock.util.board.Direction.oppositeOf;

import ru.omsu.collapsedlogicextension.util.api.Serializer;
import ru.omsu.collapsedlogicextension.util.api.Serializer.Serializable;

/** класс занимается логикой борды */
public class LogicBoard implements Serializable {

    private Cell[][] cells = new Cell[9][13];

    public LogicBoard() {
        for (int yPos = 0; yPos < 9; yPos++) {
            for (int xPos = 0; xPos < 13; xPos++) {
                cells[yPos][xPos] = new Cell();
            }
        }
    }

    public Cell getCell(final int x, final int y) {
        return cells[y][x];
    }

    public Cell updateBoard(final Tool tool, final int x, final int y) {
        cells[y][x].changeState(tool.getConstructor().get());
        updateNeighbors(x, y);
        return cells[y][x];
    }

    public void rotate(final int x, final int y) {
        cells[y][x].getState().rotate();
        updateNeighbors(x, y);
    }

    private boolean notBorder(final int x, final int y) {
        return x < 13 && x >= 0 && y < 9 && y >= 0;
    }

    private void updateNeighbors(final int x, final int y) {
        if (x == 0 && y == 4) {
            cells[y][x].getState().addDirection(Direction.WEST);
        }

        for (final Direction direction : Direction.values()) {
            if (notBorder(x + direction.getX(), y + direction.getY())) {
                if (cells[y][x].getState().isConnectableFrom(direction)
                        && cells[y + direction.getY()][x + direction.getX()]
                                .getState()
                                .isConnectableFrom(oppositeOf(direction))) {
                    cells[y][x].getState().addDirection(direction);
                    cells[y + direction.getY()][x + direction.getX()]
                            .getState()
                            .addDirection(oppositeOf(direction));
                } else {
                    cells[y][x].getState().removeDirection(direction);
                    cells[y + direction.getY()][x + direction.getX()]
                            .getState()
                            .removeDirection(oppositeOf(direction));
                }
            }
        }
    }

    /** начало в клетке (0, 4) */
    public void activateScheme() {
        if (cells[4][0].getState().isActive()) {
            cells[4][0].getState().deactivate(Direction.EAST);
            deactivateNeighbors(0, 4);
        } else {
            cells[4][0].getState().activate(Direction.EAST);
            activateNeighbors(0, 4);
        }
    }
    /** метод рекурсивный */
    private void activateNeighbors(final int x, final int y) {
        for (final Direction direction : Direction.values()) {
            if (notBorder(x + direction.getX(), y + direction.getY())) {
                if (cells[y][x].getState().isConnectableFrom(direction)
                        && cells[y + direction.getY()][x + direction.getX()]
                                .getState()
                                .isConnectableFrom(direction)
                        && !cells[y + direction.getY()][x + direction.getX()]
                                .getState()
                                .isActiveAt(direction)) {
                    cells[y + direction.getY()][x + direction.getX()]
                            .getState()
                            .activate(direction);
                    activateNeighbors(x + direction.getX(), y + direction.getY());
                }
            }
        }
    }
    /** и этот */
    private void deactivateNeighbors(final int x, final int y) {
        for (final Direction direction : Direction.values()) {
            if (notBorder(x + direction.getX(), y + direction.getY())) {
                if (cells[y][x].getState().isConnectableFrom(direction)
                        && cells[y + direction.getY()][x + direction.getX()]
                                .getState()
                                .isConnectableFrom(direction)
                        && cells[y + direction.getY()][x + direction.getX()]
                                .getState()
                                .isActiveAt(oppositeOf(direction))
                        && cells[y][x].getState().isActiveAt(direction)) {
                    cells[y + direction.getY()][x + direction.getX()]
                            .getState()
                            .deactivate(direction);
                    deactivateNeighbors(x + direction.getX(), y + direction.getY());
                    updateNeighbors(x + direction.getX(), y + direction.getY());
                }
            }
        }
    }

    @Override
    public String serialize() {
        return Serializer.serialize(cells);
    }

    @Override
    public void deserialize(final String data) {
        cells = Serializer.deserialize(data, Cell[][].class);
    }
}
