package ru.omsu.collapsedlogicextension.common.blocks.logicblock.util;

import net.minecraft.util.Direction;
import ru.omsu.collapsedlogicextension.common.blocks.logicblock.board.cellstates.CellState;

public enum Direction3D {
    UP(12, 0),
    DOWN(12, 2),
    NORTH(12, 4),
    SOUTH(-100, -100),
    WEST(12, 6),
    EAST(12, 8);

    public static Direction3D convert(final Direction direction) {
        switch (direction) {
            case UP:
                return Direction3D.UP;
            case DOWN:
                return Direction3D.DOWN;
            case NORTH:
                return Direction3D.NORTH;
            case SOUTH:
                return Direction3D.SOUTH;
            case WEST:
                return Direction3D.WEST;
            case EAST:
                return Direction3D.EAST;
            default:
                throw new IllegalStateException();
        }
    }

    public final int x, y;

    Direction3D(int x, int y){
        this.x = x;
        this.y = y;
    }

}
