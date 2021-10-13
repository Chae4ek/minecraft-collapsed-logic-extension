package ru.omsu.collapsedlogicextension.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.widget.button.ImageButton;
import net.minecraft.util.text.StringTextComponent;
import ru.omsu.collapsedlogicextension.CLEMod;
import ru.omsu.collapsedlogicextension.container.LogicBlockContainer;

import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import ru.omsu.collapsedlogicextension.tileentity.LogicBlockTileEntity;


public class LogicBlockScreen extends ContainerScreen<LogicBlockContainer> {

	private LogicBlockTileEntity tileEntity;

	private ITextComponent buildSchemeStatus = new StringTextComponent("");

	private static final ResourceLocation TEXTURE = new ResourceLocation(CLEMod.MOD_ID,
			"textures/gui/collapsed_logic_block_gui.png");

	public LogicBlockScreen(LogicBlockContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
		super(screenContainer, inv, titleIn);
		this.guiLeft = 0;
		this.guiTop = 0;
		this.xSize = 256;
		this.ySize = 192;

		this.tileEntity = screenContainer.tileEntity;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
		this.minecraft.getTextureManager().bindTexture(TEXTURE);
		this.blit(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
	}
	@Override
	protected void init() {
		super.init();
		/**xln yln - координаты по гуи
		 * width height - размер кнопки
		 * x(y)TexStartIn - координаты кнопки в атласе текстур(в TEXTURE крч)
		 * yDifTextIn - на сколько надо сдвинуться по y чтобы поймать текстуру кнопки в наведенном состоянии
		 * то есть кнопка в обычном и заряженном состоянии находятся друг под другом*/
		addButton(new ImageButton(this.guiLeft + 34, this.guiTop + 167, 20, 18, 0, 194, 19, TEXTURE,
				button -> this.buildSchemeStatus = tileEntity.buildScheme()));

		int i = 0;

		//TODO: сделать более адекватно
		for(int x = 0; x < 134; x+=20){
			addButton(new ImageButton(this.guiLeft + 235, this.guiTop + 4+19*i, 20, 18, 22+x, 194, 19, TEXTURE, null));
			i++;
		}
		//TODO: нарисовать поле тож циклом
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		this.font.drawString(this.title.getFormattedText(), 8.0f, 8.0f, 0x404040);
		this.font.drawString(this.buildSchemeStatus.getFormattedText(), 57, 170, 0x404040);
	}

	@Override
	public void render(int mouseX, int mouseY, float partialTicks) {
		this.renderBackground();
		super.render(mouseX, mouseY, partialTicks);
		this.renderHoveredToolTip(mouseX, mouseY);
	}
}
