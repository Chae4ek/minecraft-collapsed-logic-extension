package ru.omsu.collapsedlogicextension.common.blocks.logicblock.util.board;

public enum Direction {
    NORTH(0, 0, -1),
    EAST(1, 1, 0),
    SOUTH(2, 0, 1),
    WEST(3, -1, 0);

    private static final Direction[] DIRECTIONS = Direction.values();

    private final int meta;
    private final int x;
    private final int y;

    Direction(final int meta, final int x, final int y) {
        this.meta = meta;
        this.x = x;
        this.y = y;
    }

    public static Direction rotate(final Direction direction) {
        return DIRECTIONS[(direction.meta + 1) % 4];
    }

    public static Direction getByMeta(final int meta) {
        return DIRECTIONS[meta];
    }

    public static Direction oppositeOf(final Direction direction) {
        return DIRECTIONS[(direction.meta + 2) % 4];
    }

    public int getMeta() {
        return meta;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
