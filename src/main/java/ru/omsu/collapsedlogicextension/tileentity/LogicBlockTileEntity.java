package ru.omsu.collapsedlogicextension.tileentity;

import javax.annotation.Nullable;

import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import ru.omsu.collapsedlogicextension.CLEMod;
import ru.omsu.collapsedlogicextension.container.LogicBlockContainer;
import ru.omsu.collapsedlogicextension.init.ModTileEntityTypes;
import ru.omsu.collapsedlogicextension.util.ExampleItemHandler;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;

public class LogicBlockTileEntity extends TileEntity implements INamedContainerProvider {

	private ITextComponent customName;
	private ExampleItemHandler inventory;

	public LogicBlockTileEntity(TileEntityType<?> tileEntityTypeIn) {
		super(tileEntityTypeIn);

		this.inventory = new ExampleItemHandler(2);
	}

	public LogicBlockTileEntity() {
		this(ModTileEntityTypes.LOGIC_BLOCK.get());
	}

	@Override
	public Container createMenu(final int windowID, final PlayerInventory playerInv, final PlayerEntity playerIn) {
		return new LogicBlockContainer(windowID, playerInv, this);
	}

	public void setCustomName(ITextComponent name) {
		this.customName = name;
	}

	public ITextComponent getName() {
		return this.customName != null ? this.customName : this.getDefaultName();
	}

	private ITextComponent getDefaultName() {
		return new TranslationTextComponent("container." + CLEMod.MOD_ID + ".logic_block");
	}

	@Override
	public ITextComponent getDisplayName() {
		return this.getName();
	}

	@Nullable
	public ITextComponent getCustomName() {
		return this.customName;
	}

	@Override
	public void read(CompoundNBT compound) {
		super.read(compound);
		if (compound.contains("CustomName", Constants.NBT.TAG_STRING)) {
			this.customName = ITextComponent.Serializer.fromJson(compound.getString("CustomName"));
		}

		NonNullList<ItemStack> inv = NonNullList.<ItemStack>withSize(this.inventory.getSlots(), ItemStack.EMPTY);
		ItemStackHelper.loadAllItems(compound, inv);
		this.inventory.setNonNullList(inv);
	}

	@Override
	public CompoundNBT write(CompoundNBT compound) {
		super.write(compound);
		if (this.customName != null) {
			compound.putString("CustomName", ITextComponent.Serializer.toJson(this.customName));
		}

		ItemStackHelper.saveAllItems(compound, this.inventory.toNonNullList());

		return compound;
	}

	public final IItemHandlerModifiable getInventory() {
		return this.inventory;
	}

	@Nullable
	@Override
	public SUpdateTileEntityPacket getUpdatePacket() {
		CompoundNBT nbt = new CompoundNBT();
		this.write(nbt);
		return new SUpdateTileEntityPacket(this.pos, 0, nbt);
	}

	@Override
	public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
		this.read(pkt.getNbtCompound());
	}

	@Override
	public CompoundNBT getUpdateTag() {
		CompoundNBT nbt = new CompoundNBT();
		this.write(nbt);
		return nbt;
	}

	@Override
	public void handleUpdateTag(CompoundNBT nbt) {
		this.read(nbt);
	}

	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
		return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.orEmpty(cap, LazyOptional.of(() -> this.inventory));
	}

	public ITextComponent buildScheme(){
		BlockPos pos = this.pos.east().south();
		for(int y = 0; y < 6; y++){
			for(int x = 0; x < 9; x++){
				if((this.world.getBlockState(pos).isSolid() || this.world.getBlockState(pos).isTransparent()
					&& this.world.getBlockState(pos.down()).getBlock() != Blocks.AIR)){
					return new StringTextComponent("Unable to place scheme!");
				}
				pos = Math.abs(pos.getX() - this.pos.east().south().getX()) > 1 ? pos.east() : pos.west();
			}
			pos = pos.south();
		}
		return new StringTextComponent("Able to place scheme");
	}
}
