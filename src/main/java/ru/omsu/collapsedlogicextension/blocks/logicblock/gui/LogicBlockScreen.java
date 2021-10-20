package ru.omsu.collapsedlogicextension.blocks.logicblock.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import java.awt.Color;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.button.ImageButton;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import ru.omsu.collapsedlogicextension.ModInit;
import ru.omsu.collapsedlogicextension.blocks.logicblock.util.LogicBlockContainer;
import ru.omsu.collapsedlogicextension.blocks.logicblock.util.LogicBlockTileEntity;
import ru.omsu.collapsedlogicextension.blocks.logicblock.util.board.LogicBoardEntity;

/** Отрисовка GUI блока */
public class LogicBlockScreen extends ContainerScreen<LogicBlockContainer> {

    private static final ResourceLocation TEXTURE =
            new ResourceLocation(ModInit.MOD_ID, "textures/gui/collapsed_logic_block_gui.png");
    private static final ResourceLocation FIELD =
            new ResourceLocation(ModInit.MOD_ID, "textures/gui/board/field.png");
    private final LogicBlockTileEntity tileEntity;
    private ITextComponent buildSchemeStatus = new StringTextComponent("");

    private final LogicBoardEntity boardTileEntity; // логическая сущность 2д доски

    Tool selectedTool = Tool.ERASER;

    public LogicBlockScreen(
            final LogicBlockContainer screenContainer,
            final PlayerInventory inv,
            final ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
        guiLeft = 0;
        guiTop = 0;
        xSize = 256;
        ySize = 192;

        tileEntity = screenContainer.getTileEntity();
        boardTileEntity = new LogicBoardEntity();
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(
            final float partialTicks, final int mouseX, final int mouseY) {
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        minecraft.getTextureManager().bindTexture(TEXTURE);
        blit(guiLeft, guiTop, 0, 0, xSize, ySize);
    }

    @Override
    protected void init() {
        super.init();
        addButton(
                new ImageButton(
                        guiLeft + 34,
                        guiTop + 167,
                        20,
                        18,
                        0,
                        194,
                        19,
                        TEXTURE,
                        button -> buildSchemeStatus = tileEntity.buildScheme(boardTileEntity)));

        int i = 0;

        int xTool = 0;

        for (final Tool tool : Tool.values()) {
            addButton(
                    new ImageButton(
                            guiLeft + 225,
                            guiTop + 18 + 18 * i,
                            19,
                            18,
                            21 + xTool,
                            193,
                            19,
                            TEXTURE,
                            button -> selectedTool = tool));
            i++;
            xTool += 19;
        }

        final int xStart = 5;
        final int yStart = 18;

        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 13; x++) {
                final int finalX = x;
                final int finalY = y;
                addButton(
                        new FieldButton(
                                selectedTool,
                                guiLeft + xStart + x * 16,
                                guiTop + yStart + y * 16,
                                0,
                                0,
                                FIELD,
                                button -> {
                                    ((FieldButton) button).setTexture(selectedTool);
                                    ((FieldButton) button)
                                            .setCell(
                                                    boardTileEntity.updateBoard(
                                                            selectedTool, finalX, finalY));
                                }));
            }
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer(final int mouseX, final int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
        font.drawString(title.getFormattedText(), 8.0f, 8.0f, 0x404040);
        font.drawString(buildSchemeStatus.getFormattedText(), 57, 170, 0x404040);
        font.drawStringWithShadow(selectedTool.getType(), 150, 7, Color.MAGENTA.getRGB());
    }

    @Override
    public void render(final int mouseX, final int mouseY, final float partialTicks) {
        renderBackground();
        super.render(mouseX, mouseY, partialTicks);
        renderHoveredToolTip(mouseX, mouseY);
    }
}
