package ru.omsu.collapsedlogicextension.logicblock.board.tools;

import ru.omsu.collapsedlogicextension.logicblock.board.Board.Cell;
import ru.omsu.collapsedlogicextension.logicblock.util.CombinedTextureRegions;

public class Rotator implements Tool {

    public final CombinedTextureRegions texture;

    public Rotator(final CombinedTextureRegions texture) {
        this.texture = texture;
    }

    @Override
    public void apply(final Cell cell) {
        cell.setCellState(cell.getRotated());
    }

    @Override
    public CombinedTextureRegions getTexture() {
        return texture;
    }
}
