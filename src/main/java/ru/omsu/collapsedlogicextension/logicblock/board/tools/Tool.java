package ru.omsu.collapsedlogicextension.logicblock.board.tools;

import ru.omsu.collapsedlogicextension.logicblock.board.Board.Cell;
import ru.omsu.collapsedlogicextension.logicblock.util.CombinedTextureRegions;

public interface Tool {

    void apply(final Cell cell);

    CombinedTextureRegions getTexture();
}
