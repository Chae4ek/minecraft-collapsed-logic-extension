package ru.omsu.collapsedlogicextension.common.blocks.logicblock.board.cellstates;

import ru.omsu.collapsedlogicextension.common.blocks.logicblock.board.Board.Cell;
import ru.omsu.collapsedlogicextension.common.blocks.logicblock.util.Direction2D;
import ru.omsu.collapsedlogicextension.common.blocks.logicblock.util.TextureRegion;

public class EmptyCell extends CellState {

    private static final TextureRegion texture = new TextureRegion(0, 0);

    public EmptyCell(final Cell parent) {
        super(parent);
    }

    @Override
    public TextureRegion getTextureRegion() {
        return texture;
    }

    @Override
    public CellState getRotated() {
        return this;
    }

    @Override
    public void activate(final Cell from, final Direction2D fromToThis) {}

    @Override
    public void deactivate(final Cell from, final Direction2D fromToThis) {}

    @Override
    public void deactivateAllForce() {}

    @Override
    public boolean isGenerator() {
        return false;
    }
}
