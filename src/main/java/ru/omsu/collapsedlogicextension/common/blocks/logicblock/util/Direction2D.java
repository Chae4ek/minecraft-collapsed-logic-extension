package ru.omsu.collapsedlogicextension.common.blocks.logicblock.util;

public enum Direction2D {
    UP(0, -1),
    DOWN(0, 1),
    LEFT(-1, 0),
    RIGHT(1, 0);

    public final int xShift;
    public final int yShift;

    Direction2D(final int xShift, final int yShift) {
        this.xShift = xShift;
        this.yShift = yShift;
    }
}
