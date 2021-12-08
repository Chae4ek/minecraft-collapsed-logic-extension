package ru.omsu.collapsedlogicextension.logicblock.board.cellstates;

import ru.omsu.collapsedlogicextension.logicblock.board.Board.Cell;
import ru.omsu.collapsedlogicextension.logicblock.util.CombinedTextureRegions;
import ru.omsu.collapsedlogicextension.logicblock.util.Direction2D;

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
    public void update() {}

    @Override
    public void activate(final Direction2D fromToThis) {}

    @Override
    public void forceActivate() {}

    @Override
    public void deactivate(final Direction2D fromToThis) {}

    @Override
    public void forceDeactivate() {}

    @Override
    public boolean isActivate(final Direction2D fromThisTo) {
        return false;
    }

    @Override
    public boolean canBeConnected(final Direction2D fromToThis) {
        return false;
    }

    @Override
    public boolean equalsWithoutActive(final CellState state) {
        return this == state || state != null && getClass() == state.getClass();
    }
}
