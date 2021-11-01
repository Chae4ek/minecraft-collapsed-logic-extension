package ru.omsu.collapsedlogicextension.blocks.logicblock.gui;

import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.button.ImageButton;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

import ru.omsu.collapsedlogicextension.ModInit;
import ru.omsu.collapsedlogicextension.blocks.logicblock.util.LogicBlockContainer;
import ru.omsu.collapsedlogicextension.blocks.logicblock.util.LogicBlockTileEntity;
import ru.omsu.collapsedlogicextension.blocks.logicblock.util.board.LogicBoardEntity;
import ru.omsu.collapsedlogicextension.blocks.logicblock.util.board.State;

import java.awt.*;

/** Отрисовка GUI блока */
public class LogicBlockScreen extends ContainerScreen<LogicBlockContainer> {

    private static final ResourceLocation TEXTURE =
            new ResourceLocation(ModInit.MOD_ID, "textures/gui/collapsed_logic_block_gui.png");
    private static final ResourceLocation FIELD =
            new ResourceLocation(ModInit.MOD_ID, "textures/gui/board/field.png");
    private final LogicBlockTileEntity tileEntity;

    private LogicBoardEntity boardTileEntity;

    private int xOut, yOut;

    private Tool currentTool = Tool.ERASER;

    private State currentState = null;

    public LogicBlockScreen(
            final LogicBlockContainer screenContainer,
            final PlayerInventory inv,
            final ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
        guiLeft = 0;
        guiTop = 0;
        xSize = 256;
        ySize = 192;

        tileEntity = screenContainer.getTileEntity();
        boardTileEntity = new LogicBoardEntity();

        this.xOut = -1;
        this.yOut = -1;

    }

    @Override
    protected void drawGuiContainerBackgroundLayer(
            final float partialTicks, final int mouseX, final int mouseY) {
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        minecraft.getTextureManager().bindTexture(TEXTURE);
        blit(guiLeft, guiTop, 0, 0, xSize, ySize);
    }

    @Override
    protected void init() {
        super.init();

        int i = 0;

        int xTool = 0;

        //tools
        for(Tool tool : Tool.values()){
            addButton(
                    new ImageButton(
                            guiLeft + 225,
                            guiTop + 18 + 18 * i,
                            19,
                            18,
                            21 + xTool,
                            193,
                            19,
                            TEXTURE,
                            button -> currentTool = tool));
            i++;
            xTool+=19;
        }

        int xStart = 5;
        int yStart = 18;

        //field
        for(int y = 0; y < 9; y++){
            for(int x = 0; x < 13; x++){
                int finalX = x;
                int finalY = y;
                addButton(new FieldButton(
                        finalX,
                        finalY,
                        guiLeft+xStart+x*16,
                        guiTop+yStart+y*16,
                        0,
                        0,
                        FIELD,
                        button -> {
                            if(currentTool!=Tool.ROTATION) {
                                ((FieldButton) button).setTexture(boardTileEntity.updateBoard(currentTool, finalX, finalY));
                            }
                            else{
                                boardTileEntity.rotate(finalX, finalY);
                                ((FieldButton)button).rotate();
                            }
                        }
                ));
            }
        }

        //activateScheme
        addButton(
                new ImageButton(
                        guiLeft-13,
                        guiTop+81,
                        21,
                        18,
                        155,
                        193,
                        19,
                        TEXTURE,
                        (button) -> boardTileEntity.activateScheme()
                )
        );
    }

    @Override
    protected void drawGuiContainerForegroundLayer(final int mouseX, final int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
        font.drawString(title.getFormattedText(), 8.0f, 8.0f, 0x404040);
        font.drawStringWithShadow(currentTool.getConstructor().get().getType(), 150, 7, Color.RED.getRGB());
    }

    @Override
    public void render(final int mouseX, final int mouseY, final float partialTicks) {
        renderBackground();
        super.render(mouseX, mouseY, partialTicks);
        renderHoveredToolTip(mouseX, mouseY);
    }

}
