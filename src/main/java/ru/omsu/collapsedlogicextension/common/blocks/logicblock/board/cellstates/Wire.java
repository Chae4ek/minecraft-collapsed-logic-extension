package ru.omsu.collapsedlogicextension.common.blocks.logicblock.board.cellstates;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;
import ru.omsu.collapsedlogicextension.common.blocks.logicblock.board.Board.Cell;
import ru.omsu.collapsedlogicextension.common.blocks.logicblock.util.CombinedTextureRegions;
import ru.omsu.collapsedlogicextension.common.blocks.logicblock.util.Direction2D;
import ru.omsu.collapsedlogicextension.common.blocks.logicblock.util.TextureRegion;

public class Wire extends CellState {

    /**
     * Стороны, из которых ИДЕТ ток по направлению к этой клетке, причем в этих сторонах находятся
     * генераторы тока
     */
    private final Set<Direction2D> generatorToThis = EnumSet.noneOf(Direction2D.class);

    /** Направления из этой клетки в стороны, к которым подключен этот провод */
    private final Set<Direction2D> connections = EnumSet.allOf(Direction2D.class);

    public Wire(final Cell parent) {
        super(parent);
    }

    @Override
    public CombinedTextureRegions getTexture() {
        final Set<TextureRegion> parts = new HashSet<>();
        for (final Direction2D direction : connections) {
            if (parent.getCell(direction).canBeConnected(direction)) {
                parts.add(new TextureRegion(102 + (isActive ? 17 : 0), 17 * direction.id));
            }
        }
        parts.add(new TextureRegion(85, isActive ? 17 : 0));
        return new CombinedTextureRegions(parts);
    }

    @Override
    public CellState getRotated() {
        return this; // TODO: отключать подключенные направления
    }

    @Override
    public void activate(final Cell from, final Direction2D fromToThis) {
        if (from.isGenerator()) generatorToThis.add(fromToThis);
        isActive = true;
        for (final Direction2D connectedDirection : connections) {
            final Cell connectedCell = parent.getCell(connectedDirection);
            if (!connectedCell.isActive()) connectedCell.activate(parent, connectedDirection);
        }
    }

    @Override
    public void forceActivate() {
        isActive = true;
        for (final Direction2D connectedDirection : connections) {
            final Cell connectedCell = parent.getCell(connectedDirection);
            connectedCell.activate(parent, connectedDirection);
        }
    }

    @Override
    public void deactivate(final Cell from, final Direction2D fromToThis) {
        if (from.isGenerator()) generatorToThis.remove(fromToThis);
        if (generatorToThis.isEmpty()) {
            isActive = false;
            for (final Direction2D connectedDirection : connections) {
                final Cell connectedCell = parent.getCell(connectedDirection);
                if (connectedCell.isActive()) connectedCell.deactivate(parent, connectedDirection);
            }
        }
    }

    @Override
    public void forceDeactivate() {
        isActive = false;
        for (final Direction2D connectedDirection : connections) {
            final Cell connectedCell = parent.getCell(connectedDirection);
            connectedCell.deactivate(parent, connectedDirection);
        }
    }

    @Override
    public boolean isGenerator() {
        return false;
    }

    @Override
    public boolean canBeConnected(final Direction2D fromToThis) {
        return connections.contains(fromToThis.opposite());
    }
}
