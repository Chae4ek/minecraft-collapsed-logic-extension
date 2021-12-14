package ru.omsu.collapsedlogicextension.logicblock.util;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldVertexBufferUploader;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;

public class RenderHelper {

    private final BufferBuilder bufferbuilder = Tessellator.getInstance().getBuffer();
    private boolean isDrawing;

    private int x;
    private int y;
    private int width;
    private int height;
    private int x$plus$width;
    private int y$plus$height;
    private int blitOffset;

    public void setSettings(
            final int x, final int y, final int width, final int height, final int blitOffset) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.blitOffset = blitOffset;
        x$plus$width = x + width;
        y$plus$height = y + height;
    }

    public void begin() {
        if (isDrawing) throw new IllegalArgumentException("end() must be called before begin()");
        isDrawing = true;
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
    }

    public void end() {
        if (!isDrawing) throw new IllegalArgumentException("begin() must be called before end()");
        isDrawing = false;
        bufferbuilder.finishDrawing();
        RenderSystem.enableAlphaTest();
        WorldVertexBufferUploader.draw(bufferbuilder);
    }

    public void draw(final TextureRegion texture, final int textureWidth, final int textureHeight) {
        if (!isDrawing) throw new IllegalArgumentException("begin() must be called before draw()");
        final float minU = (float) texture.x / (float) textureWidth;
        final float maxU = ((float) texture.x + (float) width) / (float) textureWidth;
        final float minV = (float) texture.y / (float) textureHeight;
        final float maxV = ((float) texture.y + (float) height) / (float) textureHeight;
        bufferbuilder.pos(x, y$plus$height, blitOffset).tex(minU, maxV).endVertex();
        bufferbuilder.pos(x$plus$width, y$plus$height, blitOffset).tex(maxU, maxV).endVertex();
        bufferbuilder.pos(x$plus$width, y, blitOffset).tex(maxU, minV).endVertex();
        bufferbuilder.pos(x, y, blitOffset).tex(minU, minV).endVertex();
    }
}
