package ru.omsu.collapsedlogicextension.container;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IWorldPosCallable;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import ru.omsu.collapsedlogicextension.blocks.CLEBlockEnum;
import ru.omsu.collapsedlogicextension.tileentity.LogicBlockTileEntity;

import java.util.Objects;

public class LogicBlockContainer extends Container {

    private LogicBlockTileEntity te;

    private IWorldPosCallable canInteractWithCallable;

    public LogicBlockContainer(final int id, final PlayerInventory inv, final LogicBlockTileEntity tileBoard){
        super(ContainerRegistrator.COLLAPSED_LOGIC_BLOCK_CONTAINER.get(), id);

        this.te = tileBoard;

        this.canInteractWithCallable = IWorldPosCallable.of(tileBoard.getWorld(), tileBoard.getPos());

        //пояс игрока
        for(int i = 0; i < 9; i++){
            this.addSlot(new Slot(inv, i, 48+18*i, 168));
        }

        //выходной слот
        this.addSlot(new SlotItemHandler((IItemHandler) this.getInventory(), 10, 15, 168));
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

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return Container.isWithinUsableDistance(canInteractWithCallable, playerIn, CLEBlockEnum.COLLAPSED_LOGIC_BLOCK.getConstructor().get());
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        ItemStack stack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);
        if(slot != null && slot.getHasStack()){
            ItemStack stack1 = slot.getStack();
            stack = stack1.copy();
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
}
