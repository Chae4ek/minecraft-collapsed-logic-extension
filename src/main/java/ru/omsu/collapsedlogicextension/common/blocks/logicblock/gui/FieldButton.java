package ru.omsu.collapsedlogicextension.common.blocks.logicblock.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.ResourceLocation;
import ru.omsu.collapsedlogicextension.common.blocks.logicblock.util.board.Accumulator;
import ru.omsu.collapsedlogicextension.common.blocks.logicblock.util.board.Cell;
import ru.omsu.collapsedlogicextension.common.blocks.logicblock.util.board.Direction;
import ru.omsu.collapsedlogicextension.common.blocks.logicblock.util.board.Wire;

/**
 * Отличие этого класса от ImageButton в том, что координату текстуры наведенной кнопки мы можем
 * ловить где угодно на атласе
 */
public class FieldButton extends Button {
    private final ResourceLocation resourceLocation;

    private final int xField;
    private final int yField;
    private final int textureWidth;
    private final int textureHeight;
    private int xTexStart;
    private int yTexStart;
    private Cell cell;

    /**
     * @param xIn координата на гуи
     * @param yIn координата на гуи
     * @param xTexStartIn координата на атласе
     * @param yTexStartIn координата на атласе
     */
    public FieldButton(
            final int xField,
            final int yField,
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

        this.xField = xField;
        this.yField = yField;

        cell = new Cell();
    }

    public void setTexture(final Cell cell) {
        if (cell.getState() == null) {
            rotate();
        } else {
            xTexStart = cell.getState().getXTex();
            yTexStart = 0;
            this.cell = cell;
        }
    }

    public void rotate() {
        if (cell.getState() instanceof Accumulator) {
            yTexStart = (yTexStart + 17) % 68;
        }
    }

    @Override
    public void renderButton(final int mouseX, final int mouseY, final float partialTicks) {
        final Minecraft minecraft = Minecraft.getInstance();
        minecraft.getTextureManager().bindTexture(resourceLocation);
        RenderSystem.disableDepthTest();
        final int x = xTexStart;
        int y = yTexStart;
        if (isHovered()) {
            // TODO: сделать адекватный hover
            // x += 0;
        }
        if (cell.getState() instanceof Wire && cell.getState().isActiveAt(Direction.NORTH)) {
            y += 17;
        }
        blit(this.x, this.y, (float) x, (float) y, width, height, textureWidth, textureHeight);
        if (cell.getState() instanceof Wire) {
            renderWires();
        }
        RenderSystem.enableDepthTest();
    }

    // звук не играем))
    @Override
    public void playDownSound(final SoundHandler soundHandler) {}

    private void renderWires() {
        final Map<Integer, Boolean> directionMap = cell.getState().getDirections();

        for (final Map.Entry<Integer, Boolean> entry : directionMap.entrySet()) {
            blit(
                    x,
                    y,
                    xTexStart + 17 + (entry.getValue() ? 17 : 0),
                    entry.getKey() * 17,
                    width,
                    height,
                    textureWidth,
                    textureHeight);
        }
    }
}
