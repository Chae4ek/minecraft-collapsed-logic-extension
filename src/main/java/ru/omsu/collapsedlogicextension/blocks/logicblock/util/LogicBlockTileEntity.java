package ru.omsu.collapsedlogicextension.blocks.logicblock.util;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
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
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import ru.omsu.collapsedlogicextension.ModInit;
import ru.omsu.collapsedlogicextension.ModInit.ModObjectEnum;
import ru.omsu.collapsedlogicextension.blocks.logicblock.util.board.LogicBoardEntity;

public class LogicBlockTileEntity extends TileEntity implements INamedContainerProvider {

    private final ExampleItemHandler inventory;
    private ITextComponent customName;

    public LogicBlockTileEntity(final TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);

        inventory = new ExampleItemHandler(2);

    }

    public LogicBlockTileEntity() {
        this(ModInit.getTileEntityType(ModObjectEnum.LOGIC_BLOCK));
    }

    @Override
    public Container createMenu(
            final int windowID, final PlayerInventory playerInv, final PlayerEntity playerIn) {
        return new LogicBlockContainer(windowID, playerInv, this);
    }

    public ITextComponent getName() {
        return customName != null ? customName : getDefaultName();
    }

    private ITextComponent getDefaultName() {
        return new TranslationTextComponent("container." + ModInit.MOD_ID + ".logic_block");
    }

    @Override
    public ITextComponent getDisplayName() {
        return getName();
    }

    @Nullable
    public ITextComponent getCustomName() {
        return customName;
    }

    public void setCustomName(final ITextComponent name) {
        customName = name;
    }

    @Override
    public void read(final CompoundNBT compound) {
        super.read(compound);
        if (compound.contains("CustomName", Constants.NBT.TAG_STRING)) {
            customName = ITextComponent.Serializer.fromJson(compound.getString("CustomName"));
        }
        //TODO: прочитать матрицу доски и передать в сущность доски
        final NonNullList<ItemStack> inv =
                NonNullList.<ItemStack>withSize(inventory.getSlots(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(compound, inv);
        inventory.setNonNullList(inv);
    }

    @Override
    public CompoundNBT write(final CompoundNBT compound) {
        super.write(compound);
        if (customName != null) {
            compound.putString("CustomName", ITextComponent.Serializer.toJson(customName));
        }
        //TODO: записать матрицу доски либо интов, либо стрингов
        ItemStackHelper.saveAllItems(compound, inventory.toNonNullList());

        return compound;
    }

    public final IItemHandlerModifiable getInventory() {
        return inventory;
    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        final CompoundNBT nbt = new CompoundNBT();
        write(nbt);
        return new SUpdateTileEntityPacket(pos, 0, nbt);
    }

    @Override
    public void onDataPacket(final NetworkManager net, final SUpdateTileEntityPacket pkt) {
        read(pkt.getNbtCompound());
    }

    @Override
    public CompoundNBT getUpdateTag() {
        final CompoundNBT nbt = new CompoundNBT();
        write(nbt);
        return nbt;
    }

    @Override
    public void handleUpdateTag(final CompoundNBT nbt) {
        read(nbt);
    }

    @Override
    public <T> LazyOptional<T> getCapability(final Capability<T> cap, final Direction side) {
        return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.orEmpty(
                cap, LazyOptional.of(() -> inventory));
    }

    public ITextComponent buildScheme(LogicBoardEntity entity) {
        BlockPos pos = this.pos.east().south();
        for (int y = 0; y < 9; y++) {
            BlockPos anotherPos = pos;
            for (int x = 0; x < 13; x++) {
                if (world.getBlockState(anotherPos.down()).getBlock() == Blocks.AIR || world.getBlockState(anotherPos).isSolid()
                        || world.getBlockState(anotherPos).isTransparent()) {
                    return new StringTextComponent("Unable to place scheme!");
                }
                anotherPos = anotherPos.east();
            }
            pos = pos.south();
        }
        pos = this.pos.east().south();
        for (int y = 0; y < 9; y++) {
            BlockPos anotherPos = pos;
            for (int x = 0; x < 13; x++) {
                Block block = entity.getBlock(x, y);
                if(block!=null) {
                    world.setBlockState(anotherPos, block.getDefaultState());
                }
                anotherPos = anotherPos.east();
            }
            pos = pos.south();
        }
        return new StringTextComponent("Scheme was built!");
    }
}
