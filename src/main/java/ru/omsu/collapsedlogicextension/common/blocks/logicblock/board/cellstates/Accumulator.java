package ru.omsu.collapsedlogicextension.common.blocks.logicblock.board.cellstates;

import ru.omsu.collapsedlogicextension.common.blocks.logicblock.board.Board.Cell;

public abstract class Accumulator extends CellState {

    /*protected Map<Direction2D, Boolean> directions;
    protected Direction2D output;
    protected boolean isOutputActive;*/

    public Accumulator(final Cell parent) {
        super(parent);
        /*output = Direction2D.UP;
        directions = new HashMap<>();
        isOutputActive = false;*/
    }

    /*@Override
    public void getRotated() {
        output = Direction2D.rotate(output);
        System.out.println(directions);
        final Map<Boolean, Direction2D> reversedMap = reverse(directions);

        for (final Map.Entry<Boolean, Direction2D> entry : reversedMap.entrySet()) {
            entry.setValue(Direction2D.rotate(entry.getValue()));
        }

        directions = reverse(reversedMap);
        System.out.println(directions);
    }*/
}
