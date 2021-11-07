package ru.omsu.collapsedlogicextension.common.blocks.logicblock.board.tools;

import ru.omsu.collapsedlogicextension.common.blocks.logicblock.board.Board.Cell;
import ru.omsu.collapsedlogicextension.common.blocks.logicblock.util.TextureRegion;

public interface Tool {

    Cell apply(final Cell cell);

    TextureRegion getTextureRegion();
}
