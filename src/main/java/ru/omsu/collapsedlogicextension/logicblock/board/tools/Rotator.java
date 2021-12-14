package ru.omsu.collapsedlogicextension.logicblock.board.tools;

import ru.omsu.collapsedlogicextension.logicblock.board.Cell;
import ru.omsu.collapsedlogicextension.logicblock.board.cellstates.CellState;
import ru.omsu.collapsedlogicextension.logicblock.util.CombinedTextureRegions;

public class Rotator implements Tool {

    public final CombinedTextureRegions texture;

    public Rotator(final CombinedTextureRegions texture) {
        this.texture = texture;
    }

    @Override
    public CellState apply(final Cell cell) {
        return cell.getRotated();
    }

    @Override
    public CombinedTextureRegions getTexture() {
        return texture;
    }
}
