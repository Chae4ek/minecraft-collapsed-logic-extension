package ru.omsu.collapsedlogicextension.logicblock.board.cellstates;

import ru.omsu.collapsedlogicextension.logicblock.board.Board.Cell;
import ru.omsu.collapsedlogicextension.logicblock.util.CombinedTextureRegions;
import ru.omsu.collapsedlogicextension.logicblock.util.Direction2D;

import java.util.HashMap;
import java.util.Map;

public class EmptyCell extends CellState {

    private static final CombinedTextureRegions texture = new CombinedTextureRegions(0, 0);

    private final Map<Direction2D, Boolean> map;

    public EmptyCell(final Cell parent) {
        super(parent);
        map = new HashMap<>(4);
        for(Direction2D direction : Direction2D.values()){
            map.put(direction, false);
        }
    }

    @Override
    public CombinedTextureRegions getTexture() {
        return texture;
    }

    @Override
    public CellState getRotated() {
        return this;
    }

    @Override
    public Map<Direction2D, Boolean> update() {return map;}

    @Override
    public Map<Direction2D, Boolean> activate(final Direction2D fromToThis) {return map;}

    @Override
    public Map<Direction2D, Boolean> forceActivate() {return map;}

    @Override
    public Map<Direction2D, Boolean> deactivate(final Direction2D fromToThis) {return map;}

    @Override
    public Map<Direction2D, Boolean> forceDeactivate() {return map;}

    @Override
    public boolean isActivate(final Direction2D fromThisTo) {
        return false;
    }

    @Override
    public boolean canBeConnected(final Direction2D fromToThis) {
        return false;
    }

    @Override
    public boolean equalsWithoutActive(final CellState state) {
        return this == state || state != null && getClass() == state.getClass();
    }
}
