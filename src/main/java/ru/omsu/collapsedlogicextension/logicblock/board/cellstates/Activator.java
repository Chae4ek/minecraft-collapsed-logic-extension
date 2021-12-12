package ru.omsu.collapsedlogicextension.logicblock.board.cellstates;

import ru.omsu.collapsedlogicextension.logicblock.board.Board.Cell;
import ru.omsu.collapsedlogicextension.logicblock.util.CombinedTextureRegions;
import ru.omsu.collapsedlogicextension.logicblock.util.Direction2D;

public class Activator extends CellState {

    private boolean isActive;

    public Activator(final Cell parent) {
        super(parent);
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
    public void update() {
        if (isActive) forceActivate();
        else forceDeactivate();
    }

    @Override
    public void activate(final Direction2D fromToThis) {}

    @Override
    public void forceActivate() {
        isActive = true;
        for (final Direction2D direction : Direction2D.values()) {
            parent.getCell(direction).activate(direction);
        }
    }

    @Override
    public void deactivate(final Direction2D fromToThis) {
        if (isActive) parent.getCell(fromToThis.opposite()).activate(fromToThis.opposite());
    }

    @Override
    public void forceDeactivate() {
        isActive = false;
        for (final Direction2D direction : Direction2D.values()) {
            parent.getCell(direction).deactivate(direction);
        }
    }

    @Override
    public boolean isActivate(final Direction2D fromThisTo) {
        return isActive;
    }

    @Override
    public boolean canBeConnected(final Direction2D direction) {
        return true;
    }

    @Override
    public boolean equalsWithoutActive(final CellState state) {
        return this == state || state != null && getClass() == state.getClass();
    }
}
