package ru.omsu.collapsedlogicextension.logicblock.board.tools;

import java.util.function.Function;
import ru.omsu.collapsedlogicextension.logicblock.board.Board.Cell;
import ru.omsu.collapsedlogicextension.logicblock.board.cellstates.CellState;
import ru.omsu.collapsedlogicextension.logicblock.util.CombinedTextureRegions;

public class Brush implements Tool {

    public final CombinedTextureRegions texture;
    public final Function<Cell, CellState> cellConstructor;

    public Brush(
            final Function<Cell, CellState> cellConstructor, final CombinedTextureRegions texture) {
        this.texture = texture;
        this.cellConstructor = cellConstructor;
    }

    //TODO: у нас нет в юмл такого чтобы состояние инкапсулировало родителя клетку, це норм? надо убрать
    @Override
    public CellState apply(final Cell cell) {
        return cellConstructor.apply(cell);
    }

    @Override
    public CombinedTextureRegions getTexture() {
        return texture;
    }
}
