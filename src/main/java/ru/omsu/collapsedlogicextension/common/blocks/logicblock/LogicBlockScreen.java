package ru.omsu.collapsedlogicextension.common.blocks.logicblock;

import java.awt.Color;
import net.minecraft.client.gui.widget.button.ImageButton;
import net.minecraft.util.ResourceLocation;
import ru.omsu.collapsedlogicextension.common.blocks.logicblock.board.Board;
import ru.omsu.collapsedlogicextension.common.blocks.logicblock.board.tools.ToolEnum;
import ru.omsu.collapsedlogicextension.common.blocks.logicblock.util.FieldButton;
import ru.omsu.collapsedlogicextension.common.blocks.logicblock.util.TextureRegion;
import ru.omsu.collapsedlogicextension.init.ModInit;
import ru.omsu.collapsedlogicextension.util.proxy.ContainerScreenProxy;
import ru.omsu.collapsedlogicextension.util.api.ModContainerScreen;

/** Отрисовка GUI блока */
public class LogicBlockScreen extends ModContainerScreen<LogicBlockScreen> {

    private static final ResourceLocation GUI_BACKGROUND_TEXTURE =
            new ResourceLocation(ModInit.MOD_ID, "textures/gui/collapsed_logic_block_gui.png");
    private static final ResourceLocation FIELD_BUTTON_TEXTURE =
            new ResourceLocation(ModInit.MOD_ID, "textures/gui/board/field.png");

    private final Board board;
    private ToolEnum selectedTool = ToolEnum.ERASER;

    public LogicBlockScreen(final ContainerScreenProxy<LogicBlockScreen> containerScreenProxy) {
        super(containerScreenProxy, 256, 192);
        board = this.<LogicBlockTileEntity>getModTileEntity().getBoard();
    }

    @Override
    public void init() {
        // панель инструментов
        int i = 0;
        for (final ToolEnum toolEnum : ToolEnum.values()) {
            final TextureRegion texture = toolEnum.tool.getTextureRegion();
            addButton(
                    new ImageButton(
                            225,
                            18 + 18 * i++,
                            19,
                            18,
                            21 + texture.x,
                            193 + texture.y,
                            19,
                            GUI_BACKGROUND_TEXTURE,
                            button -> selectedTool = toolEnum));
        }

        // поле клеток
        final int xStart = 5;
        final int yStart = 18;
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 13; x++) {
                final int finalX = x;
                final int finalY = y;
                final FieldButton fieldButton =
                        new FieldButton(
                                xStart + x * 16,
                                yStart + y * 16,
                                FIELD_BUTTON_TEXTURE,
                                button -> board.applyTool(selectedTool.tool, finalX, finalY),
                                board.getTextureUpdaterForCell(x, y));
                addButton(fieldButton);
            }
        }

        // кнопка активации тока
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
                        button -> board.switchSchemeActive()));
    }

    @Override
    public ResourceLocation getGuiBackgroundTexture() {
        return GUI_BACKGROUND_TEXTURE;
    }

    @Override
    public void onGuiDraw() {
        drawString(selectedTool.name.getFormattedText(), 150, 7, Color.RED.getRGB());
    }
}
