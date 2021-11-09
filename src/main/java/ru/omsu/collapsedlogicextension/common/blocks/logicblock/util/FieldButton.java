package ru.omsu.collapsedlogicextension.common.blocks.logicblock.util;

import com.mojang.blaze3d.systems.RenderSystem;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.ResourceLocation;

/**
 * Отличие этого класса от ImageButton в том, что координату текстуры наведенной кнопки мы можем
 * ловить где угодно на атласе
 */
public class FieldButton extends Button {

    private final ResourceLocation resourceLocation;
    private final int textureWidth;
    private final int textureHeight;

    private final Supplier<CombinedTextureRegions> textureUpdater;

    /**
     * @param xIn координата на гуи
     * @param yIn координата на гуи
     * @param xTexStartIn координата на атласе
     * @param yTexStartIn координата на атласе
     */
    public FieldButton(
            final int xIn,
            final int yIn,
            final int xTexStartIn,
            final int yTexStartIn,
            final ResourceLocation resourceLocationIn,
            final Button.IPressable onPressIn,
            final Supplier<CombinedTextureRegions> textureUpdater) {
        super(xIn, yIn, 16, 16, "", onPressIn);
        textureWidth = 256;
        textureHeight = 256;
        resourceLocation = resourceLocationIn;
        this.textureUpdater = textureUpdater;
    }

    @Override
    public void renderButton(final int mouseX, final int mouseY, final float partialTicks) {
        final Minecraft minecraft = Minecraft.getInstance();
        minecraft.getTextureManager().bindTexture(resourceLocation);
        RenderSystem.disableDepthTest();
        if (isHovered()) {
            // TODO: сделать адекватный hover
        }

        for (final TextureRegion textureRegion : textureUpdater.get().getParts()) {
            blit(
                    x,
                    y,
                    textureRegion.x,
                    textureRegion.y,
                    width,
                    height,
                    textureWidth,
                    textureHeight);
        }

        RenderSystem.enableDepthTest();
    }

    @Override
    public void playDownSound(final SoundHandler soundHandler) {}
}
