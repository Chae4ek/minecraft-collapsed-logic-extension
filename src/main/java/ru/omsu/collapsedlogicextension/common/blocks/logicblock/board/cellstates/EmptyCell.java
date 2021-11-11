package ru.omsu.collapsedlogicextension.common.blocks.logicblock.board.cellstates;

import ru.omsu.collapsedlogicextension.common.blocks.logicblock.board.Board.Cell;
import ru.omsu.collapsedlogicextension.common.blocks.logicblock.util.CombinedTextureRegions;
import ru.omsu.collapsedlogicextension.common.blocks.logicblock.util.Direction2D;

public class EmptyCell extends CellState {

    private static final CombinedTextureRegions texture = new CombinedTextureRegions(0, 0);

    public EmptyCell(final Cell parent) {
        super(parent);
    }

    @Override
    public CombinedTextureRegions getTexture() {
        return texture;
    }

    @Override
    public CellState getRotated() {
        return this;
    }

    @Override
    public void activate(final Cell from, final Direction2D fromToThis) {}

    @Override
    public void forceActivate() {}

    @Override
    public void deactivate(final Cell from, final Direction2D fromToThis) {}

    @Override
    public void forceDeactivate() {}

    @Override
    public boolean canBeConnected(final Direction2D fromToThis) {
        return false;
    }

    @Override
    public boolean isConductive() {
        return false;
    }
}
