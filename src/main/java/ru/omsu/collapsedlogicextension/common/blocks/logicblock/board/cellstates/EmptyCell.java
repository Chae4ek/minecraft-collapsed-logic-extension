package ru.omsu.collapsedlogicextension.common.blocks.logicblock.board.cellstates;

import ru.omsu.collapsedlogicextension.common.blocks.logicblock.board.Board.Cell;
import ru.omsu.collapsedlogicextension.common.blocks.logicblock.util.BakedTexture;
import ru.omsu.collapsedlogicextension.common.blocks.logicblock.util.Direction2D;
import ru.omsu.collapsedlogicextension.common.blocks.logicblock.util.TextureRegion;

public class EmptyCell extends CellState {

    private static final BakedTexture texture = new BakedTexture(0, 0);

    public EmptyCell(final Cell parent) {
        super(parent);
    }

    @Override
    public BakedTexture getTexture() {
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

    @Override
    public boolean canBeConnectedFrom(Direction2D direction) {
        return false;
    }
}
