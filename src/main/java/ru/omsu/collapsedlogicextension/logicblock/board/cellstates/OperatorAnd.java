package ru.omsu.collapsedlogicextension.logicblock.board.cellstates;

import ru.omsu.collapsedlogicextension.logicblock.board.Board.Cell;
import ru.omsu.collapsedlogicextension.logicblock.util.CombinedTextureRegions;
import ru.omsu.collapsedlogicextension.logicblock.util.Direction2D;

import java.util.HashMap;
import java.util.Map;

public class OperatorAnd implements CellState{
       private Direction2D input1 = Direction2D.LEFT;
       private Direction2D input2 = Direction2D.RIGHT;
       private Direction2D output = Direction2D.UP;

       private boolean firstInputActive, secondInputActive, outputActive;

       private Cell parent;

       public OperatorAnd(Cell parent){
           this.parent = parent;
       }

       @Override
       public CombinedTextureRegions getTexture(Map<Cell, Direction2D> neighbors) {
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
       public Map<Direction2D, Boolean> update(Map<Cell, Direction2D> neighbors) {
           for(Map.Entry<Cell, Direction2D> entry : neighbors.entrySet()){
               if(input1 == entry.getValue()) firstInputActive = entry.getKey().isActivate(entry.getValue().opposite());
               if(input2 == entry.getValue()) secondInputActive = entry.getKey().isActivate(entry.getValue().opposite());
           }

           outputActive = firstInputActive && secondInputActive;
           if (outputActive) return forceActivate();
           else return forceDeactivate();
       }

       @Override
       public Map<Direction2D, Boolean> activate(final Direction2D fromToThis) {
           final Direction2D fromThisTo = fromToThis.opposite();

           Map<Direction2D, Boolean> map = new HashMap<>(3);

           if (fromThisTo == input1 && !firstInputActive) {
                firstInputActive = true;
                map.put(input1, true);
           }
           else if (fromThisTo == input2 && !secondInputActive) {
               secondInputActive = true;
               map.put(input2, true);
           }
           map.put(output, firstInputActive && secondInputActive);
           return map;
       }

       @Override
       public Map<Direction2D, Boolean> forceActivate() {
           outputActive = true;
           Map<Direction2D, Boolean> map = new HashMap<>(3);
           map.put(output, true);
           map.put(input1, true);
           map.put(input2, true);
           return map;
       }

       @Override
       public Map<Direction2D, Boolean> deactivate(final Direction2D fromToThis) {
           final Direction2D fromThisTo = fromToThis.opposite();

           Map<Direction2D, Boolean> map = new HashMap<>(3);

           if (fromThisTo == input1 && firstInputActive) {
               firstInputActive = false;
               map.put(input1, false);
           }
           else if (fromThisTo == input2 && secondInputActive) {
               secondInputActive = false;
               map.put(input2, false);
           }
           else if (fromThisTo == output && outputActive) return forceDeactivate();
           map.put(output, firstInputActive && secondInputActive);
           return map;
       }

       @Override
       public Map<Direction2D, Boolean> forceDeactivate() {
           outputActive = false;
           Map<Direction2D, Boolean> map = new HashMap<>(3);
           map.put(output, false);
           map.put(input1, false);
           map.put(input2, false);
           return map;
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
}
