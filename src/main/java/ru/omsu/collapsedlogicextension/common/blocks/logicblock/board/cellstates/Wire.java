package ru.omsu.collapsedlogicextension.common.blocks.logicblock.board.cellstates;

import java.util.ArrayList;
import java.util.List;
import ru.omsu.collapsedlogicextension.common.blocks.logicblock.board.Board.Cell;
import ru.omsu.collapsedlogicextension.common.blocks.logicblock.util.CombinedTextureRegions;
import ru.omsu.collapsedlogicextension.common.blocks.logicblock.util.Direction2D;
import ru.omsu.collapsedlogicextension.common.blocks.logicblock.util.TextureRegion;

public class Wire extends CellState {

    private boolean isActive;

    public Wire(final Cell parent) {
        super(parent);
    }

    @Override
    public CombinedTextureRegions getTexture() {
        final List<TextureRegion> parts = new ArrayList<>(5);
        parts.add(new TextureRegion(85, isActive ? 17 : 0));
        for (final Direction2D direction : Direction2D.values()) {
            if (parent.getCell(direction).canBeConnected(direction)) {
                parts.add(new TextureRegion(102 + (isActive ? 17 : 0), 17 * direction.id));
            }
        }
        return new CombinedTextureRegions(parts);
    }

    @Override
    public CellState getRotated() {
        return this;
    }

    @Override
    public void update() {
        for (final Direction2D connectedDirection : Direction2D.values()) {
            final Cell connectedCell = parent.getCell(connectedDirection);
            if (connectedCell.isActivate(connectedDirection.opposite())) {
                forceActivate();
                return;
            }
        }
        forceDeactivate();
    }

    @Override
    public void activate(final Direction2D fromToThis) {
        if (!isActive) {
            isActive = true;
            final Direction2D fromThisTo = fromToThis.opposite();
            for (final Direction2D direction : Direction2D.values()) {
                if (fromThisTo != direction) parent.getCell(direction).activate(direction);
            }
        }
    }

    @Override
    public void forceActivate() {
        isActive = true;
        for (final Direction2D direction : Direction2D.values()) {
            parent.getCell(direction).activate(direction);
        }
    }

    @Override
    public void deactivate(final Direction2D fromToThis) {
        if (isActive) {
            isActive = false;
            final Direction2D fromThisTo = fromToThis.opposite();
            for (final Direction2D direction : Direction2D.values()) {
                if (fromThisTo != direction) parent.getCell(direction).deactivate(direction);
            }
        }
    }

    @Override
    public void forceDeactivate() {
        isActive = false;
        for (final Direction2D direction : Direction2D.values()) {
            parent.getCell(direction).deactivate(direction);
        }
    }

    @Override
    public boolean isActivate(final Direction2D fromThisTo) {
        return isActive;
    }

    @Override
    public boolean canBeConnected(final Direction2D fromToThis) {
        return true;
    }

    @Override
    public boolean isConductive() {
        return true;
    }

    @Override
    public boolean equalsWithoutActive(final CellState state) {
        return this == state || state != null && getClass() == state.getClass();
    }
}
