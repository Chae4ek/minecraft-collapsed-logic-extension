package ru.omsu.collapsedlogicextension.common.blocks.logicblock.board.tools;

import ru.omsu.collapsedlogicextension.common.blocks.logicblock.board.Board.Cell;
import ru.omsu.collapsedlogicextension.common.blocks.logicblock.util.TextureRegion;

public class Rotator implements Tool {

    public final TextureRegion texture;

    public Rotator(final TextureRegion texture) {
        this.texture = texture;
    }

    @Override
    public Cell apply(final Cell cell) {
        cell.rotate();
        return cell;
    }

    @Override
    public TextureRegion getTextureRegion() {
        return texture;
    }
}
