package ru.omsu.collapsedlogicextension.common.blocks.logicblock.gui;

import java.awt.Color;
import net.minecraft.client.gui.widget.button.ImageButton;
import net.minecraft.util.ResourceLocation;
import ru.omsu.collapsedlogicextension.common.blocks.logicblock.util.LogicBlockTileEntity;
import ru.omsu.collapsedlogicextension.common.blocks.logicblock.util.board.LogicBoard;
import ru.omsu.collapsedlogicextension.common.blocks.logicblock.util.board.Tool;
import ru.omsu.collapsedlogicextension.init.ModInit;
import ru.omsu.collapsedlogicextension.util.adapter.ContainerScreenAdapter;
import ru.omsu.collapsedlogicextension.util.api.ModContainerScreen;

/** Отрисовка GUI блока */
public class LogicBlockScreen extends ModContainerScreen<LogicBlockScreen> {

    private static final ResourceLocation GUI_BACKGROUND_TEXTURE =
            new ResourceLocation(ModInit.MOD_ID, "textures/gui/collapsed_logic_block_gui.png");

    private static final ResourceLocation FIELD_BUTTON_TEXTURE =
            new ResourceLocation(ModInit.MOD_ID, "textures/gui/board/field.png");

    private final LogicBoard logicBoard;

    // TODO: зачем это?
    // private final State currentState = null;
    // TODO: перенести в logicBoard
    private Tool currentTool = Tool.ERASER;

    public LogicBlockScreen(final ContainerScreenAdapter<LogicBlockScreen> containerScreenAdapter) {
        super(containerScreenAdapter, 256, 192);
        logicBoard = this.<LogicBlockTileEntity>getModTileEntity().logicBoard;
    }

    @Override
    public void init() {
        int i = 0;
        int xTool = 0;

        // tools
        for (final Tool tool : Tool.values()) {
            addButton(
                    new ImageButton(
                            225,
                            18 + 18 * i,
                            19,
                            18,
                            21 + xTool,
                            193,
                            19,
                            GUI_BACKGROUND_TEXTURE,
                            button -> currentTool = tool));
            i++;
            xTool += 19;
        }

        final int xStart = 5;
        final int yStart = 18;

        // field
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 13; x++) {
                final int finalX = x;
                final int finalY = y;
                final FieldButton fieldButton =
                        new FieldButton(
                                finalX,
                                finalY,
                                xStart + x * 16,
                                yStart + y * 16,
                                0,
                                0,
                                FIELD_BUTTON_TEXTURE,
                                button -> {
                                    if (currentTool != Tool.ROTATION) {
                                        ((FieldButton) button)
                                                .setTexture(
                                                        logicBoard.updateBoard(
                                                                currentTool, finalX, finalY));
                                    } else {
                                        logicBoard.rotate(finalX, finalY);
                                        ((FieldButton) button).rotate();
                                    }
                                });
                fieldButton.setTexture(logicBoard.getCell(x, y));
                addButton(fieldButton);
            }
        }

        // activateScheme
        addButton(
                new ImageButton(
                        -13,
                        81,
                        21,
                        18,
                        155,
                        193,
                        19,
                        GUI_BACKGROUND_TEXTURE,
                        button -> logicBoard.activateScheme()));
    }

    @Override
    public ResourceLocation getGuiBackgroundTexture() {
        return GUI_BACKGROUND_TEXTURE;
    }

    @Override
    public void onGuiDraw() {
        drawString(currentTool.getConstructor().get().getType(), 150, 7, Color.RED.getRGB());
    }
}
