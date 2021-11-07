package ru.omsu.collapsedlogicextension.common.blocks.logicblock.board.tools;

import java.util.function.Supplier;
import ru.omsu.collapsedlogicextension.common.blocks.logicblock.board.Board.Cell;
import ru.omsu.collapsedlogicextension.common.blocks.logicblock.util.TextureRegion;

public class Brush implements Tool {

    public final TextureRegion texture;
    public final Supplier<Cell> cellConstructor;

    public Brush(final Supplier<Cell> cellConstructor, final TextureRegion texture) {
        this.texture = texture;
        this.cellConstructor = cellConstructor;
    }

    @Override
    public Cell apply(final Cell cell) {
        return cellConstructor.get();
    }

    @Override
    public TextureRegion getTextureRegion() {
        return texture;
    }
}
