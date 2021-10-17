package ru.omsu.collapsedlogicextension.blocks.logicblock.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.ResourceLocation;

/**Отличие этого класса от ImageButton в том, что координату текстуры наведенной кнопки мы можем ловить
 * где угодно на атласе*/
public class FieldButton extends Button {
    private final ResourceLocation resourceLocation;
    private int xTexStart;
    private int yTexStart;
    private int xDiffText;
    private int yDiffText;
    private final int textureWidth;
    private final int textureHeight;

    /**
     * @param xIn координата на гуи
     * @param yIn координата на гуи
     * @param xTexStartIn координата на атласе
     * @param yTexStartIn координата на атласе
     * @param xDiffTextIn координата наведенного состояния на атласе
     * @param yDiffTextIn координата наведенного состояния на атласе
     * */
    public FieldButton(int xIn, int yIn, int xTexStartIn, int yTexStartIn, ResourceLocation resourceLocationIn, Button.IPressable onPressIn) {
        super(xIn, yIn, 16, 16, "", onPressIn);
        this.textureWidth = 256;
        this.textureHeight = 256;
        this.xTexStart = xTexStartIn;
        this.yTexStart = yTexStartIn;
        this.resourceLocation = resourceLocationIn;
    }

    public void setPosition(int xIn, int yIn) {
        this.x = xIn;
        this.y = yIn;
    }
    public void setTexture(int xText, int yText){
        this.xTexStart = xText;
        this.yTexStart = yText;
    }

    public void renderButton(int p_renderButton_1_, int p_renderButton_2_, float p_renderButton_3_) {
        Minecraft minecraft = Minecraft.getInstance();
        minecraft.getTextureManager().bindTexture(this.resourceLocation);
        RenderSystem.disableDepthTest();
        int x = this.xTexStart;
        int y = this.yTexStart;
        if (this.isHovered()) {
            x += this.xDiffText;
            y += this.yDiffText;
        }

        blit(this.x, this.y, (float)x, (float)y, this.width, this.height, this.textureWidth, this.textureHeight);
        RenderSystem.enableDepthTest();
    }

    /**звук не играем))*/
    @Override
    public void playDownSound(SoundHandler p_playDownSound_1_) {
    }

    public void setHoveredTexture(Tool selectedTool){
        this.xDiffText = selectedTool.getX();
        this.yDiffText = selectedTool.getY();
    }
}
