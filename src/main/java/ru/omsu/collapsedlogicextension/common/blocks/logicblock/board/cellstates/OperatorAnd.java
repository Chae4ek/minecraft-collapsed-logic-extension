package ru.omsu.collapsedlogicextension.common.blocks.logicblock.board.cellstates;

import ru.omsu.collapsedlogicextension.common.blocks.logicblock.board.Board.Cell;
import ru.omsu.collapsedlogicextension.common.blocks.logicblock.util.CombinedTextureRegions;
import ru.omsu.collapsedlogicextension.common.blocks.logicblock.util.Direction2D;

public class OperatorAnd extends CellState {

    private Direction2D input1 = Direction2D.LEFT;
    private Direction2D input2 = Direction2D.RIGHT;
    private Direction2D output = Direction2D.UP;

    private boolean firstInputActive, secondInputActive;

    public OperatorAnd(final Cell parent) {
        super(parent);
    }

    @Override
    public CombinedTextureRegions getTexture() {
        return new CombinedTextureRegions(17, 17 * output.id);
    }

    @Override
    public CellState getRotated() {
        final OperatorAnd newState = new OperatorAnd(parent);
        newState.output = output.rotate();
        newState.input1 = input1.rotate();
        newState.input2 = input2.rotate();
        return newState;
    }

    @Override
    public void update() {
        if (parent.getCell(input1).isActivate(input1.opposite())) firstInputActive = true;
        if (parent.getCell(input2).isActivate(input2.opposite())) secondInputActive = true;
        if (firstInputActive && secondInputActive) forceActivate();
        else parent.getCell(output).deactivate(output);
    }

    @Override
    public void activate(final Direction2D fromToThis) {
        final boolean wasActive = firstInputActive && secondInputActive;
        if (fromToThis.opposite() == input1) firstInputActive = true;
        else if (fromToThis.opposite() == input2) secondInputActive = true;
        if (!wasActive && firstInputActive && secondInputActive) forceActivate();
    }

    @Override
    public void forceActivate() {
        firstInputActive = secondInputActive = true;
        parent.getCell(output).activate(output);
    }

    @Override
    public void deactivate(final Direction2D fromToThis) {
        final boolean wasActive = firstInputActive && secondInputActive;
        if (fromToThis.opposite() == input1) firstInputActive = false;
        else if (fromToThis.opposite() == input2) secondInputActive = false;
        if (wasActive && (!firstInputActive || !secondInputActive)) {
            parent.getCell(output).deactivate(output);
        } else if (fromToThis.opposite() == output && firstInputActive && secondInputActive) {
            parent.getCell(output).activate(output);
        }
    }

    @Override
    public void forceDeactivate() {
        firstInputActive = secondInputActive = false;
        parent.getCell(output).deactivate(output);
    }

    @Override
    public boolean isActivate(final Direction2D fromThisTo) {
        return fromThisTo == output && firstInputActive && secondInputActive;
    }

    @Override
    public boolean canBeConnected(final Direction2D fromToThis) {
        final Direction2D fromThisTo = fromToThis.opposite();
        return fromThisTo == output || fromThisTo == input1 || fromThisTo == input2;
    }

    @Override
    public boolean isConductive() {
        return false;
    }
}
