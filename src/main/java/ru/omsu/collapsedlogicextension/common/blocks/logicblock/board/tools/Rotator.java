package ru.omsu.collapsedlogicextension.common.blocks.logicblock.board.tools;

import ru.omsu.collapsedlogicextension.common.blocks.logicblock.board.Board.Cell;
import ru.omsu.collapsedlogicextension.common.blocks.logicblock.board.cellstates.CellState;
import ru.omsu.collapsedlogicextension.common.blocks.logicblock.util.TextureRegion;

public class Rotator implements Tool {

    public final TextureRegion texture;

    public Rotator(final TextureRegion texture) {
        this.texture = texture;
    }

    @Override
    public CellState apply(final Cell cell) {
        return cell.getRotated();
    }

    @Override
    public TextureRegion getTextureRegion() {
        return texture;
    }
}
