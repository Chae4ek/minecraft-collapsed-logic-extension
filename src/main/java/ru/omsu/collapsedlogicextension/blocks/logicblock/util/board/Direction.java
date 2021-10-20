package ru.omsu.collapsedlogicextension.blocks.logicblock.util.board;

public enum Direction {
    UP(0),
    RIGHT(1),
    DOWN(2),
    LEFT(3);

    private final int meta;

    Direction(final int meta) {
        this.meta = meta;
    }

    public int getMeta() {
        return meta;
    }
}
