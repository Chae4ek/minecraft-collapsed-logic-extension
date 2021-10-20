package ru.omsu.collapsedlogicextension.blocks.logicblock.util.board;

public enum Direction {
    NORTH(0, 0, -1),
    WEST(3, -1, 0),
    EAST(1, 1, 0),
    SOUTH(2, 0, 1);

    //meta -смещение на атласе
    //x y смещение на поле
    private int meta, x, y;

    Direction(int meta, int x, int y){
        this.meta = meta;
        this.x = x;
        this.y = y;
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
