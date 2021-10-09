package ru.omsu.collapsedlogicextension.container;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import ru.omsu.collapsedlogicextension.CLEMod;

@OnlyIn(Dist.CLIENT)
public class LogicBlockScreen extends ContainerScreen<LogicBlockContainer> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(CLEMod.MOD_ID, "gui/collapsed_logic_block_gui.png");

    public LogicBlockScreen(LogicBlockContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);

        this.guiLeft = 0;
        this.guiTop = 0;
        this.xSize = 256;
        this.ySize = 192;
    }

    /**Рендеринг заднего фона открывающегося окна, т.е. панель взаимодействия, слоты и т.д.*/
    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        RenderSystem.color4f(1,1,1,1);
        this.minecraft.getTextureManager().bindTexture(TEXTURE);
        LogicBlockScreen.blit(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize, 256, 256);
    }

    /**Рендеринг переднего фона открывающегося окна, т.е. предметы в слотах, текст и т.д.
     * Примечание: майнкрафт использует десятеричную систему исчисления для распознавания цветов*/
    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
        this.font.drawString(this.title.getFormattedText(), 8.0f, 8.0f, 4210752);
    }

    @Override
    protected void init() {
        super.init();
        this.addButton(new UploadButton(16, 16, ));
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return true;
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        this.renderBackground();
        super.render(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    public void renderBackground() {
        super.renderBackground();
    }
}
