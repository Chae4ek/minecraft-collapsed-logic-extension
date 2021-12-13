package ru.omsu.collapsedlogicextension.logicblock.board.cellstates;

import ru.omsu.collapsedlogicextension.logicblock.board.Board.Cell;
import ru.omsu.collapsedlogicextension.logicblock.util.CombinedTextureRegions;
import ru.omsu.collapsedlogicextension.logicblock.util.Direction2D;

import java.util.HashMap;
import java.util.Map;

//TODO: оператор неправильно работает, тут багофикс нужен
public class OperatorNot implements CellState{
       private Direction2D output = Direction2D.UP;
       private Direction2D input = Direction2D.DOWN;
       private boolean outputActive;

       private Cell parent;

       public OperatorNot(final Cell parent) {
           this.parent = parent;
       }

       @Override
       public CombinedTextureRegions getTexture(Map<Cell, Direction2D> neighbors) {
           return new CombinedTextureRegions(68, 17 * output.id);
       }

       @Override
       public CellState getRotated() {
           final OperatorNot newState = new OperatorNot(parent);
           newState.input = input.rotate();
           newState.output = output.rotate();
           return newState;
       }

       @Override
       public Map<Direction2D, Boolean> update(Map<Cell, Direction2D> neighbors) {
           for(Map.Entry<Cell, Direction2D> entry : neighbors.entrySet()){
                if(input == entry.getValue() && entry.getKey().isActivate(input.opposite())){
                    return forceDeactivate();
                }
                else if(output == entry.getValue()){
                    return forceActivate();
                }
           }
           return new HashMap<>();
       }

       @Override
       public Map<Direction2D, Boolean> activate(final Direction2D fromToThis) {
           if (!outputActive && fromToThis.opposite() == input) return forceDeactivate();
           else if (outputActive && fromToThis.opposite() == output) return forceActivate();
           return new HashMap<>();
       }

       @Override
       public Map<Direction2D, Boolean> forceActivate() {
           outputActive = true;
           Map<Direction2D, Boolean> map = new HashMap<>(2);
           map.put(input, false);
           map.put(output, true);
           return map;
       }

       @Override
       public Map<Direction2D, Boolean> deactivate(final Direction2D fromToThis) {
           if (!outputActive && fromToThis.opposite() == input) return forceActivate();
           else if (outputActive && fromToThis.opposite() == output) return forceDeactivate();
           return new HashMap<>();
       }

       @Override
       public Map<Direction2D, Boolean> forceDeactivate() {
           outputActive = false;
           Map<Direction2D, Boolean> map = new HashMap<>(2);
           map.put(input, true);
           map.put(output, false);
           return map;
       }

       @Override
       public boolean isActivate(final Direction2D fromThisTo) {
           return outputActive && fromThisTo == output;
       }

       @Override
       public boolean canBeConnected(final Direction2D fromToThis) {
           final Direction2D fromThisTo = fromToThis.opposite();
           return fromThisTo == input || fromThisTo == output;
       }

       @Override
       public boolean equalsWithoutActive(final CellState state) {
           if (this == state) return true;
           if (state == null || getClass() != state.getClass()) return false;
           final OperatorNot that = (OperatorNot) state;
           return output == that.output && input == that.input;
       }
}
