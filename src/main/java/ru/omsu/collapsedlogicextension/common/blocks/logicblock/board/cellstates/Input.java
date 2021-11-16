package ru.omsu.collapsedlogicextension.common.blocks.logicblock.board.cellstates;

import ru.omsu.collapsedlogicextension.common.blocks.logicblock.board.Board;
import ru.omsu.collapsedlogicextension.common.blocks.logicblock.util.CombinedTextureRegions;
import ru.omsu.collapsedlogicextension.common.blocks.logicblock.util.Direction2D;

public class Input extends CellState {

    private boolean isActive;

    public Input(final Board.Cell parent) {
        super(parent);
        isActive = false;
    }

    @Override
    public CombinedTextureRegions getTexture() {
        return new CombinedTextureRegions(153, isActive ? 17 : 0);
    }

    @Override
    public CellState getRotated() {
        return this;
    }

    @Override
    public void update() {}

    @Override
    public void activate(final Direction2D fromToThis) {
        isActive = true;
    }

    @Override
    public void forceActivate() {}

    @Override
    public void deactivate(final Direction2D fromToThis) {
        isActive = false;
    }

    @Override
    public void forceDeactivate() {}

    @Override
    public boolean isActivate(final Direction2D fromThisTo) {
        return false;
    }

    @Override
    public boolean isConductive() {
        return true;
    }

    @Override
    public boolean canBeConnected(final Direction2D fromToThis) {
        return fromToThis.opposite() == Direction2D.LEFT;
    }

    @Override
    public boolean equalsWithoutActive(final CellState state) {
        return this == state || state != null && getClass() == state.getClass();
    }
}
