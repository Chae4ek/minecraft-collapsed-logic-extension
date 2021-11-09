package ru.omsu.collapsedlogicextension.common.blocks.logicblock.board.cellstates;

import net.minecraft.client.gui.widget.Widget;
import ru.omsu.collapsedlogicextension.common.blocks.logicblock.board.Board.Cell;
import ru.omsu.collapsedlogicextension.common.blocks.logicblock.util.CombinedTextureRegions;
import ru.omsu.collapsedlogicextension.common.blocks.logicblock.util.Direction2D;
import ru.omsu.collapsedlogicextension.common.blocks.logicblock.util.TextureRegion;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class OperatorNot extends CellState {

    private Direction2D output = Direction2D.UP;
    private Set<Direction2D> connections = EnumSet.of(Direction2D.UP, Direction2D.DOWN);

    public OperatorNot(final Cell parent) {
        super(parent);
    }

    @Override
    public CombinedTextureRegions getTexture() {
        return new CombinedTextureRegions(34, 17* output.id);
    }

    @Override
    public CellState getRotated() {
        output = output.rotate();
        connections.forEach(direction -> direction = direction.rotate());
        return this;
    }

    @Override
    public void activate(final Cell from, final Direction2D fromToThis) {}

    @Override
    public void forceActivate() {}

    @Override
    public void deactivate(final Cell from, final Direction2D fromToThis) {}

    @Override
    public void forceDeactivate() {}

    @Override
    public boolean canBeConnected(final Direction2D direction) {
        return false;
    }

    /*@Override
    public void activate(final Direction2D from, final boolean isInputActive) {
        directions.replace(from, isInputActive);
        directions.replace(Direction2D.oppositeOf(from), !isInputActive);
    }

    @Override
    public int getXTex() {
        return 68;
    }

    @Override
    public String getType() {
        return "OPERATOR NOT";
    }

    @Override
    public boolean isActiveAt(final Direction2D direction2D) {
        return directions.get(direction2D);
    }*/
}
