package ru.omsu.collapsedlogicextension.container;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IWorldPosCallable;
import ru.omsu.collapsedlogicextension.blocks.CLEBlockEnum;
import ru.omsu.collapsedlogicextension.registry.ContainerRegistrator;
import ru.omsu.collapsedlogicextension.tileentity.LogicBlockTileEntity;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Objects;

public class LogicBlockContainer extends Container {

    private final LogicBlockTileEntity te;


    public LogicBlockContainer(final int id, final PlayerInventory inv, final LogicBlockTileEntity te){
        super(ContainerRegistrator.COLLAPSED_LOGIC_BLOCK_CONTAINER.get(), id);

        this.te = te;

        for(int i = 0; i < 9; i++){
            this.addSlot(new Slot(inv, i, 48+18*i, 168));
        }

        //выходной слот
        //this.addSlot(new SlotItemHandler(new ItemStackHandler(), 10, 15, 168));
    }

    public LogicBlockContainer(final int id, final PlayerInventory inv, final PacketBuffer buffer) {
        this(id, inv, getTileEntity(inv, buffer));
    }

    private static LogicBlockTileEntity getTileEntity(final PlayerInventory inv, final PacketBuffer buffer){
        Objects.requireNonNull(inv);
        Objects.requireNonNull(buffer);
        final TileEntity tileAtPos = inv.player.world.getTileEntity(buffer.readBlockPos());
        if(tileAtPos instanceof LogicBlockTileEntity){
            return (LogicBlockTileEntity) tileAtPos;
        }
        throw new IllegalStateException("Tile entity invalid: " + tileAtPos);
    }

    @ParametersAreNonnullByDefault
    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        Slot slot = this.inventorySlots.get(index);
        if(slot != null && slot.getHasStack()){
            ItemStack stack1 = slot.getStack();
            if(index < 36 && !this.mergeItemStack(stack1, LogicBlockTileEntity.getSlots(), this.inventorySlots.size(), true)){
                return ItemStack.EMPTY;
            }
            if(!this.mergeItemStack(stack1, 0, this.inventorySlots.size(), false)){
                return ItemStack.EMPTY;
            }
            if(stack1.isEmpty()){
                slot.putStack(ItemStack.EMPTY);
            }
            else{
                slot.onSlotChanged();
            }

        }
        return super.transferStackInSlot(playerIn, index);
    }

    @ParametersAreNonnullByDefault
    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        //TODO: разобраться почему te.getWorld() == null, такое не должно быть
        return isWithinUsableDistance(IWorldPosCallable.of(te.getWorld(), te.getPos()), playerIn, CLEBlockEnum.COLLAPSED_LOGIC_BLOCK.getConstructor().get());
    }
}
