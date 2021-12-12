package ru.omsu.collapsedlogicextension.logicblock.board.cellstates;

import java.util.HashMap;
import java.util.Map;
import ru.omsu.collapsedlogicextension.logicblock.board.Board.Cell;
import ru.omsu.collapsedlogicextension.logicblock.util.CombinedTextureRegions;
import ru.omsu.collapsedlogicextension.logicblock.util.Direction2D;

public class EmptyCell extends CellState {

    private static final CombinedTextureRegions texture = new CombinedTextureRegions(0, 0);
    private static final Map<Direction2D, Boolean> map = new HashMap<>();

    public EmptyCell(final Cell parent) {
        super(parent);
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
    public Map<Direction2D, Boolean> update() {
        return map;
    }

    @Override
    public Map<Direction2D, Boolean> activate(final Direction2D fromToThis) {
        return map;
    }

    @Override
    public Map<Direction2D, Boolean> forceActivate() {
        return map;
    }

    @Override
    public Map<Direction2D, Boolean> deactivate(final Direction2D fromToThis) {
        return map;
    }

    @Override
    public Map<Direction2D, Boolean> forceDeactivate() {
        return map;
    }

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
