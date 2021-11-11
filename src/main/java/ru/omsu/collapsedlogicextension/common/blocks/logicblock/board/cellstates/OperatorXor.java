package ru.omsu.collapsedlogicextension.common.blocks.logicblock.board.cellstates;

import ru.omsu.collapsedlogicextension.common.blocks.logicblock.board.Board.Cell;
import ru.omsu.collapsedlogicextension.common.blocks.logicblock.util.CombinedTextureRegions;
import ru.omsu.collapsedlogicextension.common.blocks.logicblock.util.Direction2D;

public class OperatorXor extends CellState {

    private Direction2D input1 = Direction2D.LEFT;
    private Direction2D input2 = Direction2D.RIGHT;
    private Direction2D output = Direction2D.UP;

    private boolean firstInputActive, secondInputActive, outputActive;

    public OperatorXor(final Cell parent) {
        super(parent);
        firstInputActive = false;
        secondInputActive = false;
        outputActive = false;
    }

    @Override
    public CombinedTextureRegions getTexture() {
        return new CombinedTextureRegions(51, 17*output.id);
    }

    @Override
    public CellState getRotated() {
        output = output.rotate();
        input1 = input1.rotate();
        input2 = input2.rotate();

        return this;
    }

    @Override
    public void activate(final Cell from, final Direction2D fromToThis) {

        if(fromToThis.opposite() == input1){
            firstInputActive = true;
        }
        if(fromToThis.opposite() == input2){
            secondInputActive = true;
        }

        if(!outputActive || this.canBeConnected(fromToThis)){
            forceActivate();
        }
    }

    @Override
    public void forceActivate() {
        outputActive = firstInputActive ^ secondInputActive;

        final Cell connectedCellFromOutput = parent.getCell(output);

        if(connectedCellFromOutput.canBeConnected(output.opposite())){
            if(outputActive){
                connectedCellFromOutput.activate(parent, output);
            }
            else{
                connectedCellFromOutput.deactivate(parent, output);
            }
        }

    }

    @Override
    public void deactivate(final Cell from, final Direction2D fromToThis) {

        if(fromToThis.opposite() == input1){
            firstInputActive = false;
        }
        if(fromToThis.opposite() == input2){
            secondInputActive = false;
        }

        if(!outputActive || this.canBeConnected(fromToThis)){
            forceDeactivate();
        }
    }

    @Override
    public void forceDeactivate() {
        outputActive = firstInputActive ^ secondInputActive;
        final Cell connectedCellFromOutput = parent.getCell(output);

        if(connectedCellFromOutput.canBeConnected(output.opposite())){
            connectedCellFromOutput.deactivate(parent, output);
        }
    }

    @Override
    public boolean canBeConnected(final Direction2D direction) {
        Direction2D direction1 = direction.opposite();
        return direction1 == output || direction1 == input1 || direction1 == input2;
    }

    @Override
    public boolean isConductive() {
        return false;
    }
}
