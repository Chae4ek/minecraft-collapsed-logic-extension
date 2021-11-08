package ru.omsu.collapsedlogicextension.common.blocks.logicblock.board.cellstates;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;
import ru.omsu.collapsedlogicextension.common.blocks.logicblock.board.Board.Cell;
import ru.omsu.collapsedlogicextension.common.blocks.logicblock.util.BakedTexture;
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
    public BakedTexture getTexture() {
        Set<TextureRegion> parts = new HashSet<>();
        for(Direction2D direction : connections){
            if(parent.getCell(direction).canBeConnectedFrom(direction.opposite())) {
                parts.add(new TextureRegion(102 + (isActive ? 17 : 0), 17 * direction.texShift));
            }
        }
        return new BakedTexture(
                new TextureRegion(85, isActive ? 17 : 0), parts
        );
    }

    @Override
    public CellState getRotated() {
        return this;
    }

    @Override
    public void activate(final Cell from, final Direction2D fromToThis) {
        if (from.isGenerator()) generatorToThis.add(fromToThis);
        if (!isActive) {
            isActive = true;
            for (final Direction2D connectedDirection : connections) {
                final Cell connectedCell = parent.getCell(connectedDirection);
                if (!connectedCell.isActive()) connectedCell.activate(parent, connectedDirection);
            }
        }
    }

    @Override
    public void deactivate(final Cell from, final Direction2D fromToThis) {
        if (from.isGenerator()) generatorToThis.remove(fromToThis);
        if (isActive && generatorToThis.isEmpty()) {
            isActive = false;
            for (final Direction2D connectedDirection : connections) {
                final Cell connectedCell = parent.getCell(connectedDirection);
                if (connectedCell.isActive()) connectedCell.deactivate(parent, connectedDirection);
            }
        }
    }

    @Override
    public void deactivateAllForce() {
        isActive = false;
        generatorToThis.clear();
        for (final Direction2D connectedDirection : connections) {
            final Cell connectedCell = parent.getCell(connectedDirection);
            if (connectedCell.isActive()) connectedCell.deactivate(parent, connectedDirection);
        }
    }

    @Override
    public boolean isGenerator() {
        return false;
    }

    @Override
    public boolean canBeConnectedFrom(Direction2D direction) {
        return true;
    }
}
