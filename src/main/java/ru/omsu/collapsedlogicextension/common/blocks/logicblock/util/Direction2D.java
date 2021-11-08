package ru.omsu.collapsedlogicextension.common.blocks.logicblock.util;

public enum Direction2D {
    UP(0, -1, 0),
    RIGHT(1, 0, 1),
    DOWN(0, 1, 2),
    LEFT(-1, 0, 3);

    public final int xShift;
    public final int yShift;
    public final int texShift;

    Direction2D(final int xShift, final int yShift, final int texShift) {
        this.xShift = xShift;
        this.yShift = yShift;
        this.texShift = texShift;
    }

    public Direction2D opposite(){
        return Direction2D.values()[(this.texShift + 2)%4];
    }
}
