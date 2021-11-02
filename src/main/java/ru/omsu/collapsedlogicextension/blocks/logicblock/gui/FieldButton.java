package ru.omsu.collapsedlogicextension.blocks.logicblock.gui;

import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.ResourceLocation;

import ru.omsu.collapsedlogicextension.blocks.logicblock.util.board.Accumulator;
import ru.omsu.collapsedlogicextension.blocks.logicblock.util.board.Cell;
import ru.omsu.collapsedlogicextension.blocks.logicblock.util.board.Wire;

import java.util.Map;

/**Отличие этого класса от ImageButton в том, что координату текстуры наведенной кнопки мы можем ловить
 * где угодно на атласе*/
public class FieldButton extends Button {
    private final ResourceLocation resourceLocation;

    private int xField, yField;

    private int xTexStart;
    private int yTexStart;
    private final int textureWidth;
    private final int textureHeight;

    private Cell cell;

    /**
     * @param xIn координата на гуи
     * @param yIn координата на гуи
     * @param xTexStartIn координата на атласе
     * @param yTexStartIn координата на атласе
     * */
    public FieldButton(int xField, int yField, int xIn, int yIn, int xTexStartIn, int yTexStartIn,
                       ResourceLocation resourceLocationIn, Button.IPressable onPressIn) {
        super(xIn, yIn, 16, 16, "", onPressIn);
        this.textureWidth = 256;
        this.textureHeight = 256;
        this.xTexStart = xTexStartIn;
        this.yTexStart = yTexStartIn;
        this.resourceLocation = resourceLocationIn;

        this.xField = xField;
        this.yField = yField;

        this.cell = new Cell();

    }

    public void setTexture(Cell cell){
        if(cell.getState() == null){
            rotate();
        }
        else {
            this.xTexStart = cell.getState().getXTex();
            this.yTexStart = 0;
            this.cell = cell;
        }
    }

    public void rotate(){
        if(cell.getState() instanceof Accumulator) {
            yTexStart = (yTexStart + 17) % 68;
        }
    }

    public void renderButton(int p_renderButton_1_, int p_renderButton_2_, float p_renderButton_3_) {
        Minecraft minecraft = Minecraft.getInstance();
        minecraft.getTextureManager().bindTexture(this.resourceLocation);
        RenderSystem.disableDepthTest();
        int x = this.xTexStart;
        int y = this.yTexStart;
        if (this.isHovered()) {
            //TODO: сделать адекватный hover
            //x += 0;
        }
        if (cell.getState() instanceof Wire && cell.getState().isActive()){
            y += 17;
        }
        blit(this.x, this.y, (float)x, (float)y, this.width, this.height, this.textureWidth, this.textureHeight);
        if(cell.getState() instanceof Wire){
            renderWires();
        }
        RenderSystem.enableDepthTest();
    }

    /**звук не играем))*/
    @Override
    public void playDownSound(SoundHandler p_playDownSound_1_) {}

    private void renderWires(){

        Map<Integer, Boolean> directionMap = cell.getState().getDirections();

        for(Map.Entry<Integer, Boolean> entry : directionMap.entrySet()){
            blit(this.x, this.y, xTexStart+17+(entry.getValue() ? 17 : 0),
                    entry.getKey()*17,
                    this.width, this.height,
                    this.textureWidth, this.textureHeight);
        }

    }

}
