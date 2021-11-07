package ru.omsu.collapsedlogicextension.common.blocks.logicblock.board.tools;

import java.util.function.Function;
import ru.omsu.collapsedlogicextension.common.blocks.logicblock.board.Board.Cell;
import ru.omsu.collapsedlogicextension.common.blocks.logicblock.board.cellstates.CellState;
import ru.omsu.collapsedlogicextension.common.blocks.logicblock.util.TextureRegion;

public class Brush implements Tool {

    public final TextureRegion texture;
    public final Function<Cell, CellState> cellConstructor;

    public Brush(final Function<Cell, CellState> cellConstructor, final TextureRegion texture) {
        this.texture = texture;
        this.cellConstructor = cellConstructor;
    }

    @Override
    public CellState apply(final Cell cell) {
        return cellConstructor.apply(cell);
    }

    @Override
    public TextureRegion getTextureRegion() {
        return texture;
    }
}
