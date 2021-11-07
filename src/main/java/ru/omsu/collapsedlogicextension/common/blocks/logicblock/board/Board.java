package ru.omsu.collapsedlogicextension.common.blocks.logicblock.board;

import ru.omsu.collapsedlogicextension.common.blocks.logicblock.board.tools.Tool;
import ru.omsu.collapsedlogicextension.common.blocks.logicblock.util.Direction2D;
import ru.omsu.collapsedlogicextension.common.blocks.logicblock.util.Direction3D;
import ru.omsu.collapsedlogicextension.common.blocks.logicblock.util.TextureRegion;
import ru.omsu.collapsedlogicextension.util.api.Serializer;
import ru.omsu.collapsedlogicextension.util.api.Serializer.Serializable;

public class Board implements Serializable {

    /** Фантомная клетка для активации начальной */
    private final Cell activator = new Cell();

    private Cell[][] cells = new Cell[9][13];

    // TODO: изменить на клетки-входы редстоун сигнала (?)
    private boolean isCurrentActive;

    public Board() {
        for (int yPos = 0; yPos < 9; yPos++) {
            for (int xPos = 0; xPos < 13; xPos++) {
                cells[yPos][xPos] = new Cell();
            }
        }
    }

    /** @return true, если сторона блока side может взаимодействовать с редстоуном */
    public boolean canConnectRedstone(final Direction3D side) {
        // TODO: сделать включение/отключение сторон блока (?)
        return true;
    }

    public Cell applyTool(final Tool tool, final int x, final int y) {
        final Cell cell = tool.apply(cells[y][x]);
        if (cells[y][x] != cell) {
            cells[y][x].onCellChange(cell);
            cells[y][x] = cell;
        }
        return cell;
    }

    public Cell getCell(final int x, final int y) {
        return cells[y][x];
    }

    public void switchSchemeActive() {
        isCurrentActive = !isCurrentActive;
        if (isCurrentActive) cells[4][0].activate(activator, Direction2D.RIGHT);
        else cells[4][0].deactivate(activator, Direction2D.RIGHT);
    }

    @Override
    public String serialize() {
        return Serializer.serialize(cells);
    }

    @Override
    public void deserialize(final String data) {
        cells = Serializer.deserialize(data, Cell[][].class);
    }

    public static class Cell {

        private static final TextureRegion emptyCellTexture = new TextureRegion(0, 0);

        /** @return текстура клетки */
        public TextureRegion getTextureRegion() {
            return emptyCellTexture;
        }

        /** Вращает клетку против часовой стрелки */
        public void rotate() {}

        /** Вызывается при активации клетки */
        protected void activate(final Cell from, final Direction2D fromToThis) {}

        /** Вызывается при деактивации клетки */
        protected void deactivate(final Cell from, final Direction2D fromToThis) {}

        /** Вызывается при изменении этой клетки на новую */
        private void onCellChange(final Cell newCell) {}
    }
}
