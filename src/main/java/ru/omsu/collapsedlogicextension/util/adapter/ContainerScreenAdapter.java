package ru.omsu.collapsedlogicextension.util.adapter;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import ru.omsu.collapsedlogicextension.init.ModObjectEnum.ModObject;
import ru.omsu.collapsedlogicextension.util.api.ModContainerScreen;

/** Перехватывает методы GUI клиента */
@OnlyIn(Dist.CLIENT)
public class ContainerScreenAdapter<E extends ModContainerScreen<E>>
        extends ContainerScreen<ContainerAdapter<?>> {

    private final E containerScreen;

    public ContainerScreenAdapter(
            final ModObject<?, ?, ?, ?, E> modObject,
            final ContainerAdapter<?> screenContainer,
            final PlayerInventory inv,
            final ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
        containerScreen = modObject.containerScreenFactory.create(this);
        xSize = containerScreen.getWidth();
        ySize = containerScreen.getHeight();
    }

    @Override
    protected void init() {
        super.init();
        containerScreen.init();
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(
            final float partialTicks, final int mouseX, final int mouseY) {
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        minecraft.getTextureManager().bindTexture(containerScreen.getGuiBackgroundTexture());
        blit(guiLeft, guiTop, 0, 0, xSize, ySize);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(final int mouseX, final int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
        font.drawString(title.getFormattedText(), 8.0f, 8.0f, 0x404040);
        containerScreen.onGuiDraw();
    }

    public void drawString(final String text, final float x, final float y, final int color) {
        font.drawStringWithShadow(text, x, y, color);
    }

    @Override
    public void render(final int mouseX, final int mouseY, final float partialTicks) {
        renderBackground();
        super.render(mouseX, mouseY, partialTicks);
        renderHoveredToolTip(mouseX, mouseY);
    }

    public void addNewButton(final Widget widget) {
        widget.x += guiLeft;
        widget.y += guiTop;
        addButton(widget);
    }
}
