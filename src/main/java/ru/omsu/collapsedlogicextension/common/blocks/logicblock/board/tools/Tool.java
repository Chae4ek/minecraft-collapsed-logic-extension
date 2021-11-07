package ru.omsu.collapsedlogicextension.common.blocks.logicblock.board.tools;

import ru.omsu.collapsedlogicextension.common.blocks.logicblock.board.Board.Cell;
import ru.omsu.collapsedlogicextension.common.blocks.logicblock.board.cellstates.CellState;
import ru.omsu.collapsedlogicextension.common.blocks.logicblock.util.TextureRegion;

public interface Tool {

    CellState apply(final Cell cell);

    TextureRegion getTextureRegion();
}
