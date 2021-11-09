package ru.omsu.collapsedlogicextension.common.blocks.logicblock.util;

public enum Direction2D {
    UP(0, -1, 0),
    RIGHT(1, 0, 1),
    DOWN(0, 1, 2),
    LEFT(-1, 0, 3);

    public final int xShift;
    public final int yShift;
    public final int id;

    Direction2D(final int xShift, final int yShift, final int id) {
        this.xShift = xShift;
        this.yShift = yShift;
        this.id = id;
    }

    public Direction2D opposite() {
        return values()[(id + 2) % 4];
    }

    public Direction2D rotate(){
        return values()[(id+1)%4];
    }
}
