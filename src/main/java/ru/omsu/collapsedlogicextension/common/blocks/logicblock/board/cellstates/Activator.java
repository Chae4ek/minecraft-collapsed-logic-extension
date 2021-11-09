package ru.omsu.collapsedlogicextension.common.blocks.logicblock.board.cellstates;

import ru.omsu.collapsedlogicextension.common.blocks.logicblock.board.Board.Cell;
import ru.omsu.collapsedlogicextension.common.blocks.logicblock.util.CombinedTextureRegions;
import ru.omsu.collapsedlogicextension.common.blocks.logicblock.util.Direction2D;

public class Activator extends CellState {

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
    public void activate(final Cell from, final Direction2D fromToThis) {}

    @Override
    public void forceActivate() {
        isActive = true;
        for (final Direction2D direction : Direction2D.values()) {
            parent.getCell(direction).activate(parent, direction);
        }
    }

    @Override
    public void deactivate(final Cell from, final Direction2D fromToThis) {
        if (isActive) parent.getCell(fromToThis.opposite()).activate(parent, fromToThis.opposite());
    }

    @Override
    public void forceDeactivate() {
        isActive = false;
        for (final Direction2D direction : Direction2D.values()) {
            parent.getCell(direction).deactivate(parent, direction);
        }
    }

    @Override
    public boolean canBeConnected(final Direction2D direction) {
        return true;
    }
}
