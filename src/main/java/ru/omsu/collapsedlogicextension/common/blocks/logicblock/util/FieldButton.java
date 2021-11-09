package ru.omsu.collapsedlogicextension.common.blocks.logicblock.util;

import com.mojang.blaze3d.systems.RenderSystem;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.ResourceLocation;
import ru.omsu.collapsedlogicextension.init.Registrator;

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
     */
    public FieldButton(
            final int xIn,
            final int yIn,
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

        final RenderHelper renderHelper = new RenderHelper();
        renderHelper.setSettings(x, y, width, height, getBlitOffset());
        renderHelper.begin();

        for (final TextureRegion textureRegion : textureUpdater.get().getParts()) {
            renderHelper.draw(textureRegion, textureWidth, textureHeight);
        }

        renderHelper.end();

        RenderSystem.enableDepthTest();
    }

    @Override
    public void playDownSound(final SoundHandler soundHandler) {
        soundHandler.play(SimpleSound.master(Registrator.buttonClick.get(), 1f, 1f));
    }
}
