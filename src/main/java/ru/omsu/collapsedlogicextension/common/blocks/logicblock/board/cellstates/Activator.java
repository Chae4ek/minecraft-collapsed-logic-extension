package ru.omsu.collapsedlogicextension.common.blocks.logicblock.board.cellstates;

import ru.omsu.collapsedlogicextension.common.blocks.logicblock.board.Board.Cell;
import ru.omsu.collapsedlogicextension.common.blocks.logicblock.util.Direction2D;

public class Activator extends EmptyCell {

    public Activator(final Cell parent) {
        super(parent);
    }

    @Override
    public void activate(final Cell from, final Direction2D fromToThis) {
        isActive = true;
        final Cell cell = parent.getCell(Direction2D.RIGHT);
        if (!cell.isActive()) cell.activate(parent, Direction2D.RIGHT);
    }

    @Override
    public void deactivate(final Cell from, final Direction2D fromToThis) {
        deactivateAllForce();
    }

    @Override
    public void deactivateAllForce() {
        isActive = false;
        final Cell cell = parent.getCell(Direction2D.RIGHT);
        if (cell.isActive()) cell.deactivate(parent, Direction2D.RIGHT);
    }

    @Override
    public boolean isGenerator() {
        return true;
    }
}
