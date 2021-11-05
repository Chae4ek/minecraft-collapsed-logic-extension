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
import ru.omsu.collapsedlogicextension.util.api.ModTileEntity;
import ru.omsu.collapsedlogicextension.util.api.Unsafe;

/** Перехватывает методы GUI клиента */
@OnlyIn(Dist.CLIENT)
public class ContainerScreenAdapter<E extends ModContainerScreen<E>>
        extends ContainerScreen<ContainerAdapter<?>> {

    private final E containerScreen;
    private final ContainerAdapter<?> containerAdapter;

    public ContainerScreenAdapter(
            final ModObject<?, ?, ?, ?, E> modObject,
            final ContainerAdapter<?> containerAdapter,
            final PlayerInventory inv,
            final ITextComponent titleIn) {
        super(containerAdapter, inv, titleIn);
        this.containerAdapter = containerAdapter;
        containerScreen = modObject.containerScreenFactory.create(this);
        xSize = containerScreen.getWidth();
        ySize = containerScreen.getHeight();
    }

    /** @return tile entity этого объекта, если он есть, иначе крашнется игра */
    @Unsafe
    @SuppressWarnings("unchecked")
    public <T extends ModTileEntity<T>> T getModTileEntity() {
        return (T) containerAdapter.getTileEntityAdapterForThis(null, null).tileEntity;
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
