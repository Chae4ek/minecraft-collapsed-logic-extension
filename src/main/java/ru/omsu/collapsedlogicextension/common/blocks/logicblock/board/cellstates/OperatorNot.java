package ru.omsu.collapsedlogicextension.common.blocks.logicblock.board.cellstates;

import java.util.EnumSet;
import java.util.Set;
import ru.omsu.collapsedlogicextension.common.blocks.logicblock.board.Board.Cell;
import ru.omsu.collapsedlogicextension.common.blocks.logicblock.util.CombinedTextureRegions;
import ru.omsu.collapsedlogicextension.common.blocks.logicblock.util.Direction2D;

public class OperatorNot extends CellState {

    private Direction2D output = Direction2D.UP;
    private Direction2D input = Direction2D.DOWN;
    private boolean isOutputActive = true;

    public OperatorNot(final Cell parent) {
        super(parent);
    }

    @Override
    public CombinedTextureRegions getTexture() {
        return new CombinedTextureRegions(68, 17 * output.id);
    }

    @Override
    public CellState getRotated() {
        output = output.rotate();
        input = input.rotate();

        return this;
    }

    @Override
    public void activate(final Cell from, final Direction2D fromToThis) {
        if(isOutputActive && this.canBeConnected(fromToThis)){
            forceActivate();
        }
    }

    @Override
    public void forceActivate() {
        isOutputActive = false;
        final Cell outputCell = parent.getCell(output);

        if(outputCell.canBeConnected(output.opposite())){
            outputCell.deactivate(parent, output);
        }
    }

    @Override
    public void deactivate(final Cell from, final Direction2D fromToThis) {
        if(!isOutputActive && this.canBeConnected(fromToThis)){
            forceDeactivate();
        }
    }

    @Override
    public void forceDeactivate() {
        isOutputActive = true;
        final Cell outputCell = parent.getCell(output);

        if(outputCell.canBeConnected(output.opposite())){
            outputCell.activate(parent, output);
        }
    }

    @Override
    public boolean canBeConnected(final Direction2D direction) {
        return direction == input || direction == output;
    }
}
