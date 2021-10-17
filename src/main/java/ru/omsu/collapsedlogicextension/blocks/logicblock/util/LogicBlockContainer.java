package ru.omsu.collapsedlogicextension.blocks.logicblock.util;

import java.util.Objects;
import javax.annotation.Nonnull;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IWorldPosCallable;
import net.minecraftforge.items.SlotItemHandler;
import ru.omsu.collapsedlogicextension.ModInit;
import ru.omsu.collapsedlogicextension.ModInit.ModObjectEnum;

/** Хранит для группы слотов под предметы */
public class LogicBlockContainer extends Container {

    private final LogicBlockTileEntity tileEntity;
    private final IWorldPosCallable canInteractWithCallable;

    // Server Constructor
    public LogicBlockContainer(
            final int windowID, final PlayerInventory playerInv, final LogicBlockTileEntity tile) {
        super(ModInit.getContainerType(ModObjectEnum.LOGIC_BLOCK), windowID);

        tileEntity = tile;
        canInteractWithCallable = IWorldPosCallable.of(tile.getWorld(), tile.getPos());

        // Save Slot
        addSlot(new SlotItemHandler(tile.getInventory(), 0, 15, 168));

    }

    // Client Constructor
    public LogicBlockContainer(
            final int windowID, final PlayerInventory playerInv, final PacketBuffer data) {
        this(windowID, playerInv, getTileEntity(playerInv, data));
    }

    private static LogicBlockTileEntity getTileEntity(
            final PlayerInventory playerInv, final PacketBuffer data) {
        Objects.requireNonNull(playerInv, "playerInv cannot be null");
        Objects.requireNonNull(data, "data cannot be null");
        final TileEntity tileAtPos = playerInv.player.world.getTileEntity(data.readBlockPos());
        if (tileAtPos instanceof LogicBlockTileEntity) {
            return (LogicBlockTileEntity) tileAtPos;
        }
        throw new IllegalStateException("TileEntity is not correct " + tileAtPos);
    }

    public LogicBlockTileEntity getTileEntity() {
        return tileEntity;
    }

    @Override
    public boolean canInteractWith(final PlayerEntity playerIn) {
        return isWithinUsableDistance(
                canInteractWithCallable, playerIn, ModInit.getBlock(ModObjectEnum.LOGIC_BLOCK));
    }

    @Nonnull
    @Override
    public ItemStack transferStackInSlot(final PlayerEntity player, final int index) {
        ItemStack returnStack = ItemStack.EMPTY;
        final Slot slot = inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            final ItemStack slotStack = slot.getStack();
            returnStack = slotStack.copy();

            final int containerSlots =
                    inventorySlots.size() - player.inventory.mainInventory.size();
            if (index < containerSlots) {
                if (!mergeItemStack(slotStack, containerSlots, inventorySlots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!mergeItemStack(slotStack, 0, containerSlots, false)) {
                return ItemStack.EMPTY;
            }
            if (slotStack.getCount() == 0) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }
            if (slotStack.getCount() == returnStack.getCount()) {
                return ItemStack.EMPTY;
            }
            slot.onTake(player, slotStack);
        }
        return returnStack;
    }
}
