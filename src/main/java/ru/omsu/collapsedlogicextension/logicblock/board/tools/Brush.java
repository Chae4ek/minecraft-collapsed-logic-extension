package ru.omsu.collapsedlogicextension.logicblock.board.tools;

import java.util.function.Supplier;
import ru.omsu.collapsedlogicextension.logicblock.board.Board.Cell;
import ru.omsu.collapsedlogicextension.logicblock.board.cellstates.CellState;
import ru.omsu.collapsedlogicextension.logicblock.util.CombinedTextureRegions;

public class Brush implements Tool {

    public final CombinedTextureRegions texture;
    public final Supplier<CellState> cellConstructor;

    public Brush(final Supplier<CellState> cellConstructor, final CombinedTextureRegions texture) {
        this.texture = texture;
        this.cellConstructor = cellConstructor;
    }

    @Override
    public CellState apply(final Cell cell) {
        return cellConstructor.get();
    }

    @Override
    public CombinedTextureRegions getTexture() {
        return texture;
    }
}
