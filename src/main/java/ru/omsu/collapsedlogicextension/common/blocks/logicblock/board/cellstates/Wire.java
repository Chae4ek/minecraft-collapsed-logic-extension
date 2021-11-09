package ru.omsu.collapsedlogicextension.common.blocks.logicblock.board.cellstates;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;
import ru.omsu.collapsedlogicextension.common.blocks.logicblock.board.Board.Cell;
import ru.omsu.collapsedlogicextension.common.blocks.logicblock.util.CombinedTextureRegions;
import ru.omsu.collapsedlogicextension.common.blocks.logicblock.util.Direction2D;
import ru.omsu.collapsedlogicextension.common.blocks.logicblock.util.TextureRegion;

public class Wire extends CellState {

    /** Направления из этой клетки в стороны, к которым подключен этот провод */
    private final Set<Direction2D> connections = EnumSet.allOf(Direction2D.class);

    public Wire(final Cell parent) {
        super(parent);
    }

    @Override
    public CombinedTextureRegions getTexture() {
        final Set<TextureRegion> parts = new HashSet<>();
        parts.add(new TextureRegion(85, isActive ? 17 : 0));
        for (final Direction2D direction : connections) {
            if (parent.getCell(direction).canBeConnected(direction)) {
                parts.add(new TextureRegion(102 + (isActive ? 17 : 0), 17 * direction.id));
            }
        }
        return new CombinedTextureRegions(parts);
    }

    @Override
    public CellState getRotated() {
        return this; // TODO: отключать подключенные направления
    }

    @Override
    public void activate(final Cell from, final Direction2D fromToThis) {
        if (!isActive) forceActivate();
    }

    @Override
    public void forceActivate() {
        isActive = true;
        for (final Direction2D connectedDirection : connections) {
            final Cell connectedCell = parent.getCell(connectedDirection);
            if (connectedCell.canBeConnected(connectedDirection)) {
                connectedCell.activate(parent, connectedDirection);
            }
        }
    }

    @Override
    public void deactivate(final Cell from, final Direction2D fromToThis) {
        if (isActive) forceDeactivate();
    }

    @Override
    public void forceDeactivate() {
        isActive = false;
        for (final Direction2D connectedDirection : connections) {
            final Cell connectedCell = parent.getCell(connectedDirection);
            if (connectedCell.canBeConnected(connectedDirection)) {
                connectedCell.deactivate(parent, connectedDirection);
            }
        }
    }

    @Override
    public boolean canBeConnected(final Direction2D fromToThis) {
        return connections.contains(fromToThis.opposite());
    }
}