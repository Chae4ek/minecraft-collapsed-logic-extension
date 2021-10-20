package ru.omsu.collapsedlogicextension.blocks.logicblock.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.ResourceLocation;
import ru.omsu.collapsedlogicextension.blocks.logicblock.util.board.Direction;
import ru.omsu.collapsedlogicextension.blocks.logicblock.util.board.LogicBoardEntity;

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

    private Tool tool;

    /**
     * @param xIn координата на гуи
     * @param yIn координата на гуи
     * @param xTexStartIn координата на атласе
     * @param yTexStartIn координата на атласе
     * */
    public FieldButton(Tool tool, int xField, int yField, int xIn, int yIn, int xTexStartIn, int yTexStartIn,
                       ResourceLocation resourceLocationIn, Button.IPressable onPressIn) {
        super(xIn, yIn, 16, 16, "", onPressIn);
        this.textureWidth = 256;
        this.textureHeight = 256;
        this.xTexStart = xTexStartIn;
        this.yTexStart = yTexStartIn;
        this.resourceLocation = resourceLocationIn;

        this.xField = xField;
        this.yField = yField;

        this.tool = tool;
    }

    public void setTexture(Tool tool){
        if(tool == Tool.ROTATION){
            rotate();
        }
        else {
            this.xTexStart = tool.getX();
            this.yTexStart = 0;
        }
        this.tool = tool;
    }

    public void rotate(){
        yTexStart = (yTexStart + 17) % 68;
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

        blit(this.x, this.y, (float)x, (float)y, this.width, this.height, this.textureWidth, this.textureHeight);
        if(tool!=null && tool == Tool.LOGIC_WIRE){
            renderWire(xField, yField);
        }
        RenderSystem.enableDepthTest();
    }

    /**звук не играем))*/
    @Override
    public void playDownSound(SoundHandler p_playDownSound_1_) {}

    public void renderWire(int x, int y){

        Map<Direction, Boolean> directionMap = getDirections(x, y);

        for(Map.Entry<Direction, Boolean> entry : directionMap.entrySet()){
            blit(this.x, this.y, tool.getX()+17+(entry.getValue() ? 17 : 0),
                    entry.getKey().getMeta()*17,
                    this.width, this.height,
                    this.textureWidth, this.textureHeight);
        }
    }

    private Map<Direction, Boolean> getDirections(int x, int y){
        return LogicBoardEntity.getDirections(x, y);
    }


}
