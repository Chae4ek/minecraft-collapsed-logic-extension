package ru.omsu.collapsedlogicextension.blocks.logicblock.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.button.ImageButton;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import ru.omsu.collapsedlogicextension.ModInit;
import ru.omsu.collapsedlogicextension.blocks.logicblock.util.LogicBlockContainer;
import ru.omsu.collapsedlogicextension.blocks.logicblock.util.LogicBlockTileEntity;

/** Отрисовка GUI блока */
public class LogicBlockScreen extends ContainerScreen<LogicBlockContainer> {

    private static final ResourceLocation TEXTURE =
            new ResourceLocation(ModInit.MOD_ID, "textures/gui/collapsed_logic_block_gui.png");
    private final LogicBlockTileEntity tileEntity;
    private ITextComponent buildSchemeStatus = new StringTextComponent("");

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

        System.err.println("LOGIC BLOCK ~~SCREEN~~ CREATED");
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
        /**
         * xIn yIn - координаты по гуи width height - размер кнопки x(y)TexStartIn - координаты
         * кнопки в атласе текстур(в TEXTURE крч) yDifTextIn - на сколько надо сдвинуться по y чтобы
         * поймать текстуру кнопки в наведенном состоянии то есть кнопка в обычном и заряженном
         * состоянии находятся друг под другом
         */
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
                        button -> buildSchemeStatus = tileEntity.buildScheme()));

        int i = 0;

        // TODO: сделать более адекватно
        for (int x = 0; x < 134; x += 20) {
            addButton(
                    new ImageButton(
                            guiLeft + 235,
                            guiTop + 4 + 19 * i,
                            20,
                            18,
                            22 + x,
                            194,
                            19,
                            TEXTURE,
                            null));
            i++;
        }
        // TODO: нарисовать поле тож циклом
    }

    @Override
    protected void drawGuiContainerForegroundLayer(final int mouseX, final int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
        font.drawString(title.getFormattedText(), 8.0f, 8.0f, 0x404040);
        font.drawString(buildSchemeStatus.getFormattedText(), 57, 170, 0x404040);
    }

    @Override
    public void render(final int mouseX, final int mouseY, final float partialTicks) {
        renderBackground();
        super.render(mouseX, mouseY, partialTicks);
        renderHoveredToolTip(mouseX, mouseY);
    }
}
