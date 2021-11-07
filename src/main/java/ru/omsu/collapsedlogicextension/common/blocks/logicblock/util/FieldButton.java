package ru.omsu.collapsedlogicextension.common.blocks.logicblock.util;

import com.mojang.blaze3d.systems.RenderSystem;
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
    private int xTexStart;
    private int yTexStart;

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
            final Button.IPressable onPressIn) {
        super(xIn, yIn, 16, 16, "", onPressIn);
        textureWidth = 256;
        textureHeight = 256;
        xTexStart = xTexStartIn;
        yTexStart = yTexStartIn;
        resourceLocation = resourceLocationIn;
    }

    public void setTexture(final TextureRegion texture) {
        xTexStart = texture.x;
        yTexStart = texture.y;
    }

    @Override
    public void renderButton(final int mouseX, final int mouseY, final float partialTicks) {
        final Minecraft minecraft = Minecraft.getInstance();
        minecraft.getTextureManager().bindTexture(resourceLocation);
        RenderSystem.disableDepthTest();
        if (isHovered()) {
            // TODO: сделать адекватный hover
        }
        blit(x, y, xTexStart, yTexStart, width, height, textureWidth, textureHeight);
        RenderSystem.enableDepthTest();
    }

    @Override
    public void playDownSound(final SoundHandler soundHandler) {}
}