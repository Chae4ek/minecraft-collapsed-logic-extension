package ru.omsu.collapsedlogicextension.blocks.logicblock.util.board;

public enum Direction {
    NORTH(0),
    WEST(3),
    EAST(1),
    SOUTH(2);

    private int meta;

    Direction(int meta){
        this.meta = meta;
    }

    public int getMeta() {
        return meta;
    }
}
