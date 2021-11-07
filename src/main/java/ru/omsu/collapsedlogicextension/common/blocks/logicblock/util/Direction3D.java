package ru.omsu.collapsedlogicextension.common.blocks.logicblock.util;

import net.minecraft.util.Direction;

public enum Direction3D {
    UP,
    DOWN,
    NORTH,
    SOUTH,
    WEST,
    EAST;

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
}
