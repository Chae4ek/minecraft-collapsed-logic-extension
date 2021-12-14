package ru.omsu.collapsedlogicextension.logicblock.board.tools;

import ru.omsu.collapsedlogicextension.logicblock.board.Cell;
import ru.omsu.collapsedlogicextension.logicblock.board.cellstates.CellState;
import ru.omsu.collapsedlogicextension.logicblock.util.CombinedTextureRegions;

public interface Tool {

    CellState apply(final Cell cell);

    CombinedTextureRegions getTexture();
}
