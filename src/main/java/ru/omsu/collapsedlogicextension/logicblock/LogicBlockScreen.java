package ru.omsu.collapsedlogicextension.logicblock;

import com.mojang.blaze3d.systems.RenderSystem;
import java.awt.Color;
import java.util.function.Supplier;
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

    private static final int TOOLS_X_POSITION = 225;
    private static final int TOOLS_Y_POSITION = 18;
    private static final int TOOL_BUTTON_WIDTH = 19;
    private static final int TOOL_BUTTON_HEIGHT = 18;
    private static final int TOOL_TEXTURE_START_X = 21;
    private static final int TOOL_TEXTURE_START_Y = 193;
    private static final int TOOL_TEXTURE_OFFSET = 19;

    private static final int FIELD_X_START_POSITION = 5;
    private static final int FIELD_Y_START_POSITION = 18;

    private static final int ACTIVATION_BUTTON_X_POSITION = -13;
    private static final int ACTIVATION_BUTTON_Y_POSITION = 81;
    private static final int ACTIVATION_BUTTON_WIDTH = 21;
    private static final int ACTIVATION_BUTTON_HEIGHT = 18;
    private static final int ACTIVATION_TEXTURE_START_X = 155;
    private static final int ACTIVATION_TEXTURE_START_Y = 193;
    private static final int ACTIVATION_TEXTURE_OFFSET = 19;

    private static final int START_NAME_X = 150;
    private static final int START_NAME_Y = 7;

    private static final ResourceLocation GUI_BACKGROUND_TEXTURE =
            new ResourceLocation(ModInit.MOD_ID, "textures/gui/collapsed_logic_block_gui.png");
    private static final ResourceLocation FIELD_BUTTON_TEXTURE =
            new ResourceLocation(ModInit.MOD_ID, "textures/gui/board/field.png");

    private final Supplier<Board> boardGetter;

    private ToolEnum selectedTool = ToolEnum.ERASER;

    public LogicBlockScreen(
            final LogicBlockContainer logicBlockContainer,
            final PlayerInventory inv,
            final ITextComponent titleIn) {
        super(logicBlockContainer, inv, titleIn);
        xSize = 256;
        ySize = 192;
        boardGetter = logicBlockContainer.getBoardGetter();
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
                            TOOLS_X_POSITION,
                            TOOLS_Y_POSITION + 18 * i++,
                            TOOL_BUTTON_WIDTH,
                            TOOL_BUTTON_HEIGHT,
                            TOOL_TEXTURE_START_X + texture.getPartsOfTexture().get(0).x,
                            TOOL_TEXTURE_START_Y + texture.getPartsOfTexture().get(0).y,
                            TOOL_TEXTURE_OFFSET,
                            GUI_BACKGROUND_TEXTURE,
                            button -> selectedTool = toolEnum));
        }

        // поле клеток
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 13; x++) {
                final int finalX = x;
                final int finalY = y;
                final FieldButton fieldButton =
                        new FieldButton(
                                FIELD_X_START_POSITION + x * 16,
                                FIELD_Y_START_POSITION + y * 16,
                                FIELD_BUTTON_TEXTURE,
                                button ->
                                        boardGetter
                                                .get()
                                                .applyTool(selectedTool.tool, finalX, finalY),
                                boardGetter.get().getTextureUpdaterForCell(x, y));
                addButton(fieldButton);
            }
        }

        // кнопка активации тока
        addButton(
                new ImageButton(
                        ACTIVATION_BUTTON_X_POSITION,
                        ACTIVATION_BUTTON_Y_POSITION,
                        ACTIVATION_BUTTON_WIDTH,
                        ACTIVATION_BUTTON_HEIGHT,
                        ACTIVATION_TEXTURE_START_X,
                        ACTIVATION_TEXTURE_START_Y,
                        ACTIVATION_TEXTURE_OFFSET,
                        GUI_BACKGROUND_TEXTURE,
                        button -> boardGetter.get().switchSchemeActive()));
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
        font.drawStringWithShadow(
                selectedTool.name.getFormattedText(),
                START_NAME_X,
                START_NAME_Y,
                Color.RED.getRGB());
    }

    @Override
    public void render(final int mouseX, final int mouseY, final float partialTicks) {
        renderBackground();
        super.render(mouseX, mouseY, partialTicks);
        renderHoveredToolTip(mouseX, mouseY);
    }
}
