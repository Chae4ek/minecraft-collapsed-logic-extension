package ru.omsu.collapsedlogicextension.common.blocks.logicblock.board.cellstates;

import ru.omsu.collapsedlogicextension.common.blocks.logicblock.board.Board.Cell;
import ru.omsu.collapsedlogicextension.common.blocks.logicblock.util.Direction2D;
import ru.omsu.collapsedlogicextension.common.blocks.logicblock.util.TextureRegion;

public class OperatorOr extends CellState {

    /*private Direction2D input1;
    private Direction2D input2;

    private boolean isFirstInputActive;
    private boolean isSecondInputActive;*/

    public OperatorOr(final Cell parent) {
        super(parent);
        /*input1 = Direction2D.RIGHT;
        input2 = Direction2D.LEFT;
        isFirstInputActive = false;
        isSecondInputActive = false;*/
    }

    @Override
    public TextureRegion getTextureRegion() {
        return null;
    }

    @Override
    public CellState getRotated() {
        return null;
    }

    @Override
    public void activate(final Cell from, final Direction2D fromToThis) {}

    @Override
    public void deactivate(final Cell from, final Direction2D fromToThis) {}

    @Override
    public void deactivateAllForce() {}

    @Override
    public boolean isGenerator() {
        return false;
    }

    /*@Override
    public void activate(final Direction2D from, final boolean isActive) {
        if (from == input1) {
            isFirstInputActive = isActive;
        } else if (from == input2) {
            isSecondInputActive = isActive;
        }
        isOutputActive = isFirstInputActive || isSecondInputActive;
    }

    @Override
    public void getRotated() {
        output = Direction2D.rotate(output);
        input1 = Direction2D.rotate(input1);
        input2 = Direction2D.rotate(input2);
    }

    @Override
    public boolean isActiveAt(final Direction2D direction2D) {
        if (direction2D == input1) {
            return isFirstInputActive;
        } else if (direction2D == input2) {
            return isSecondInputActive;
        }
        return isOutputActive;
    }

    @Override
    public Map<Integer, Boolean> getDirections() {
        final Map<Integer, Boolean> map = new HashMap<>();
        map.put(output.getMeta(), isOutputActive);
        map.put(input1.getMeta(), isFirstInputActive);
        map.put(input2.getMeta(), isSecondInputActive);
        return map;
    }

    @Override
    public boolean isConnectableFrom(final Direction2D direction2D) {
        return direction2D != Direction2D.oppositeOf(output);
    }

    @Override
    public int getXTex() {
        return 34;
    }

    @Override
    public String getType() {
        return "OPERATOR OR";
    }*/
}
