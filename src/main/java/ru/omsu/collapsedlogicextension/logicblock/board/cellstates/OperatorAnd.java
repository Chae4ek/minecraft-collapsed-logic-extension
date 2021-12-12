package ru.omsu.collapsedlogicextension.logicblock.board.cellstates;

import ru.omsu.collapsedlogicextension.logicblock.board.Board.Cell;
import ru.omsu.collapsedlogicextension.logicblock.util.CombinedTextureRegions;
import ru.omsu.collapsedlogicextension.logicblock.util.Direction2D;

import java.util.Map;
//TODO: добавь extends CellState
public class OperatorAnd  {
    /*
    private Direction2D input1 = Direction2D.LEFT;
    private Direction2D input2 = Direction2D.RIGHT;
    private Direction2D output = Direction2D.UP;

    private boolean firstInputActive, secondInputActive, outputActive;

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
    public Map<Direction2D, Boolean> update() {
        firstInputActive = parent.getCell(input1).isActivate(input1.opposite());
        secondInputActive = parent.getCell(input2).isActivate(input2.opposite());
        outputActive = firstInputActive && secondInputActive;
        if (outputActive) return forceActivate();
        else return forceDeactivate();
    }

    @Override
    public Map<Direction2D, Boolean> activate(final Direction2D fromToThis) {
        final Direction2D fromThisTo = fromToThis.opposite();
        if (fromThisTo == input1 && !firstInputActive) return update();
        else if (fromThisTo == input2 && !secondInputActive) return update();
    }

    @Override
    public Map<Direction2D, Boolean> forceActivate() {
        outputActive = true;
        parent.getCell(output).activate(output);
    }

    @Override
    public Map<Direction2D, Boolean> deactivate(final Direction2D fromToThis) {
        final Direction2D fromThisTo = fromToThis.opposite();
        if (fromThisTo == input1 && firstInputActive) update();
        else if (fromThisTo == input2 && secondInputActive) update();
        else if (fromThisTo == output && outputActive) forceActivate();
    }

    @Override
    public Map<Direction2D, Boolean> forceDeactivate() {
        outputActive = false;
        parent.getCell(output).deactivate(output);
    }

    @Override
    public boolean isActivate(final Direction2D fromThisTo) {
        return outputActive && fromThisTo == output;
    }

    @Override
    public boolean canBeConnected(final Direction2D fromToThis) {
        final Direction2D fromThisTo = fromToThis.opposite();
        return fromThisTo == output || fromThisTo == input1 || fromThisTo == input2;
    }

    @Override
    public boolean equalsWithoutActive(final CellState state) {
        if (this == state) return true;
        if (state == null || getClass() != state.getClass()) return false;
        final OperatorAnd that = (OperatorAnd) state;
        return input1 == that.input1 && input2 == that.input2 && output == that.output;
    }

 */
}
