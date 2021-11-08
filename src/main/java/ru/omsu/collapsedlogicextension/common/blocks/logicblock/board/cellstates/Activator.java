package ru.omsu.collapsedlogicextension.common.blocks.logicblock.board.cellstates;

import ru.omsu.collapsedlogicextension.common.blocks.logicblock.board.Board.Cell;
import ru.omsu.collapsedlogicextension.common.blocks.logicblock.util.Direction2D;

public class Activator extends EmptyCell {

    public Activator(final Cell parent) {
        super(parent);
    }

    @Override
    public void activate(final Cell from, final Direction2D fromToThis) {
        forceActivate();
    }

    @Override
    public void forceActivate() {
        isActive = true;
        parent.getCell(Direction2D.RIGHT).activate(parent, Direction2D.RIGHT);
    }

    @Override
    public void deactivate(final Cell from, final Direction2D fromToThis) {
        forceDeactivate();
    }

    @Override
    public void forceDeactivate() {
        isActive = false;
        parent.getCell(Direction2D.RIGHT).deactivate(parent, Direction2D.RIGHT);
    }

    @Override
    public boolean isGenerator() {
        return true;
    }
}
