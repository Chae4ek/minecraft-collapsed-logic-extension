package ru.omsu.collapsedlogicextension.logicblock;

import com.mojang.blaze3d.systems.RenderSystem;
import java.awt.Color;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.ImageButton;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import ru.omsu.collapsedlogicextension.init.ModInit;
import ru.omsu.collapsedlogicextension.logicblock.board.Board;
import ru.omsu.collapsedlogicextension.logicblock.board.tools.ToolEnum;
import ru.omsu.collapsedlogicextension.logicblock.util.CombinedTextureRegions;
import ru.omsu.collapsedlogicextension.logicblock.util.FieldButton;

/** Отрисовка GUI блока */
public class LogicBlockScreen extends ContainerScreen<LogicBlockContainer> {

    private static final ResourceLocation GUI_BACKGROUND_TEXTURE =
            new ResourceLocation(ModInit.MOD_ID, "textures/gui/collapsed_logic_block_gui.png");
    private static final ResourceLocation FIELD_BUTTON_TEXTURE =
            new ResourceLocation(ModInit.MOD_ID, "textures/gui/board/field.png");

    private final Board board;

    private ToolEnum selectedTool = ToolEnum.ERASER;

    public LogicBlockScreen(
            final LogicBlockContainer logicBlockContainer,
            final PlayerInventory inv,
            final ITextComponent titleIn) {
        super(logicBlockContainer, inv, titleIn);
        xSize = 256;
        ySize = 192;
        board = logicBlockContainer.getBoard();
    }

    /**
     * Вызывается при инициализации GUI. ДОЛЖЕН создавать элементы именно здесь, а не в конструкторе
     */
    @Override
    public void init() {
        super.init();
        // панель инструментов
        int i = 0;
        for (final ToolEnum toolEnum : ToolEnum.values()) {
            final CombinedTextureRegions texture = toolEnum.tool.getTexture();
            addButton(
                    new ImageButton(
                            225,
                            18 + 18 * i++,
                            19,
                            18,
                            21 + texture.getParts().get(0).x,
                            193 + texture.getParts().get(0).y,
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

    /** Добавляет новую кнопку на GUI */
    @Override
    protected <T extends Widget> T addButton(final T button) {
        button.x += guiLeft;
        button.y += guiTop;
        return super.addButton(button);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(
            final float partialTicks, final int mouseX, final int mouseY) {
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        minecraft.getTextureManager().bindTexture(GUI_BACKGROUND_TEXTURE);
        blit(guiLeft, guiTop, 0, 0, xSize, ySize);
    }

    /** Вызывается при отрисовке GUI */
    @Override
    protected void drawGuiContainerForegroundLayer(final int mouseX, final int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
        font.drawString(title.getFormattedText(), 8.0f, 8.0f, 0x404040);
        font.drawStringWithShadow(selectedTool.name.getFormattedText(), 150, 7, Color.RED.getRGB());
    }

    @Override
    public void render(final int mouseX, final int mouseY, final float partialTicks) {
        renderBackground();
        super.render(mouseX, mouseY, partialTicks);
        renderHoveredToolTip(mouseX, mouseY);
    }
}
