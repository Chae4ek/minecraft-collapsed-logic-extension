package ru.omsu.collapsedlogicextension.common.blocks.logicblock.util.board;

public class EmptyCell implements State {

    @Override
    public int getXTex() {
        return 0;
    }

    @Override
    public String getType() {
        return "ERASER";
    }
}
