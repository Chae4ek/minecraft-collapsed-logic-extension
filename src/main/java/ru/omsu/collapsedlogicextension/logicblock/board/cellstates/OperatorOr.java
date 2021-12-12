package ru.omsu.collapsedlogicextension.logicblock.board.cellstates;

// TODO: добавь etxends CellState
public class OperatorOr {
    /*
       private Direction2D input1 = Direction2D.LEFT;
       private Direction2D input2 = Direction2D.RIGHT;
       private Direction2D output = Direction2D.UP;

       private boolean firstInputActive, secondInputActive, outputActive;

       public OperatorOr(final Cell parent) {
           super(parent);
       }

       @Override
       public CombinedTextureRegions getTexture() {
           return new CombinedTextureRegions(34, 17 * output.id);
       }

       @Override
       public CellState getRotated() {
           final OperatorOr newState = new OperatorOr(parent);
           newState.output = output.rotate();
           newState.input1 = input1.rotate();
           newState.input2 = input2.rotate();
           return newState;
       }

       @Override
       public void update() {
           firstInputActive = parent.getCell(input1).isActivate(input1.opposite());
           secondInputActive = parent.getCell(input2).isActivate(input2.opposite());
           outputActive = firstInputActive || secondInputActive;
           if (outputActive) forceActivate();
           else forceDeactivate();
       }

       @Override
       public void activate(final Direction2D fromToThis) {
           final Direction2D fromThisTo = fromToThis.opposite();
           if (fromThisTo == input1 && !firstInputActive) update();
           else if (fromThisTo == input2 && !secondInputActive) update();
       }

       @Override
       public void forceActivate() {
           outputActive = true;
           parent.getCell(output).activate(output);
       }

       @Override
       public void deactivate(final Direction2D fromToThis) {
           final Direction2D fromThisTo = fromToThis.opposite();
           if (fromThisTo == input1 && firstInputActive) update();
           else if (fromThisTo == input2 && secondInputActive) update();
           else if (fromThisTo == output && outputActive) forceActivate();
       }

       @Override
       public void forceDeactivate() {
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
           final OperatorOr that = (OperatorOr) state;
           return input1 == that.input1 && input2 == that.input2 && output == that.output;
       }

    */
}
