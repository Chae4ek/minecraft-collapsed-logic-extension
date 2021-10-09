package ru.omsu.collapsedlogicextension.tileentity;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import ru.omsu.collapsedlogicextension.CLEMod;
import ru.omsu.collapsedlogicextension.container.LogicBlockContainer;

import javax.annotation.Nullable;

public class LogicBlockTileEntity extends LockableLootTileEntity implements INamedContainerProvider{

    private static int slots = 1;

    protected NonNullList<ItemStack> items = NonNullList.withSize(slots, ItemStack.EMPTY);

    public LogicBlockTileEntity(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }
    public LogicBlockTileEntity(){
        this(TileEntityRegistrator.COLLAPSED_LOGIC_BLOCK_TILE_ENTITY.get());
    }

    @Override
    protected ITextComponent getDefaultName() {
        return new TranslationTextComponent("container." + CLEMod.MOD_ID + "collapsed_logic_block");
    }

    @Override
    protected Container createMenu(int id, PlayerInventory player) {
        return new LogicBlockContainer(id, player, this);
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return items;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> items) {
        this.items = items;
    }

    @Override
    public int getSizeInventory() {
        return items.size();
    }

    @Nullable
    @Override
    public World getWorld() {
        return super.getWorld();
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        if(!this.checkLootAndWrite(compound)){
            ItemStackHelper.saveAllItems(compound, this.items);
        }
        return compound;
    }

    @Override
    public void read(CompoundNBT compound) {
        super.read(compound);
        this.items = NonNullList.withSize(getSizeInventory(), ItemStack.EMPTY);
        if(!checkLootAndRead(compound)){
            ItemStackHelper.saveAllItems(compound, this.items);
        }
    }

    public static int getSlots() {
        return slots;
    }
}
