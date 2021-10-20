package ru.omsu.collapsedlogicextension.blocks.logicblock.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.ResourceLocation;
import ru.omsu.collapsedlogicextension.blocks.logicblock.util.board.Cell;
import ru.omsu.collapsedlogicextension.blocks.logicblock.util.board.Direction;
import ru.omsu.collapsedlogicextension.blocks.logicblock.util.board.Wire;

/**
 * Отличие этого класса от ImageButton в том, что координату текстуры наведенной кнопки мы можем
 * ловить где угодно на атласе
 */
public class FieldButton extends Button {
    private final ResourceLocation resourceLocation;
    private int xTexStart;
    private int yTexStart;
    private final int textureWidth;
    private final int textureHeight;
    private Cell cell;
    private Tool tool;

    /**
     * @param xIn координата на гуи
     * @param yIn координата на гуи
     * @param xTexStartIn координата на атласе
     * @param yTexStartIn координата на атласе
     */
    public FieldButton(
            final Tool tool,
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

        this.tool = tool;
    }

    public void setCell(final Cell cell) {
        this.cell = cell;
    }

    public void setTexture(final Tool tool) {
        if (tool == Tool.ROTATION) {
            rotate();
        } else {
            xTexStart = tool.getX();
            yTexStart = 0;
        }
        this.tool = tool;
    }

    public void rotate() {
        yTexStart = (yTexStart + 17) % 68;
    }

    @Override
    public void renderButton(
            final int p_renderButton_1_,
            final int p_renderButton_2_,
            final float p_renderButton_3_) {
        final Minecraft minecraft = Minecraft.getInstance();
        minecraft.getTextureManager().bindTexture(resourceLocation);
        RenderSystem.disableDepthTest();
        int x = xTexStart;
        final int y = yTexStart;
        if (isHovered()) {
            // TODO: сделать адекватный hover
            x += 0;
        }

        blit(this.x, this.y, (float) x, (float) y, width, height, textureWidth, textureHeight);
        if (cell != null
                && tool != null
                && tool == Tool.LOGIC_WIRE
                && cell.getType() == Tool.LOGIC_WIRE) {
            renderWire((Wire) cell);
        }
        RenderSystem.enableDepthTest();
    }

    /** звук не играем)) */
    @Override
    public void playDownSound(final SoundHandler p_playDownSound_1_) {}

    public void renderWire(final Wire wire) {
        for (final Map.Entry<Direction, Boolean> entry : wire.getDirections().entrySet()) {
            blit(
                    x,
                    y,
                    tool.getX() + 17 + (entry.getValue() ? 17 : 0),
                    entry.getKey().getMeta() * 17,
                    width,
                    height,
                    textureWidth,
                    textureHeight);
        }
    }
}
