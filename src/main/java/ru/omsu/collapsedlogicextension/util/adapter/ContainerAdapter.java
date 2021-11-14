package ru.omsu.collapsedlogicextension.util.adapter;

import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.IWorldPosCallable;
import net.minecraftforge.items.ItemStackHandler;
import ru.omsu.collapsedlogicextension.init.ModObjectEnum.ModObject;
import ru.omsu.collapsedlogicextension.init.Registrator;
import ru.omsu.collapsedlogicextension.util.api.ModContainer;
import ru.omsu.collapsedlogicextension.util.api.ModTileEntity;
import ru.omsu.collapsedlogicextension.util.api.Unsafe;

/** Перехватывает методы физического контейнера */
public class ContainerAdapter<E extends ModContainer<E>> extends Container {

    private final E container;
    private TileEntityAdapter<?> tileEntityAdapter;

    public ContainerAdapter(
            final ModObject<?, ?, E, ?> modObject,
            final int windowId,
            final TileEntityAdapter<?> tileEntityAdapter) {
        super(Registrator.getContainerType(modObject.thisEnum), windowId);
        this.tileEntityAdapter = tileEntityAdapter;
        container = modObject.containerFactory.create(this, tileEntityAdapter);
    }

    public ContainerAdapter(
            final ModObject<?, ?, E, ?> modObject,
            final int windowId,
            final PlayerInventory inventory,
            final PacketBuffer data) {
        super(Registrator.getContainerType(modObject.thisEnum), windowId);
        container =
                modObject.containerFactory.create(
                        this, getTileEntityAdapterForThis(inventory, data));
    }

    public static boolean isWithinUsableDistance(
            final IWorldPosCallable worldPos,
            final PlayerEntity playerIn,
            final Block targetBlock) {
        return Container.isWithinUsableDistance(worldPos, playerIn, targetBlock);
    }

    /**
     * @return физический (майнкрафтовский) tile entity этого объекта, если он есть, иначе крашнется
     *     игра
     */
    @Unsafe
    @SuppressWarnings("unchecked")
    public <T extends ModTileEntity<T>> TileEntityAdapter<T> getTileEntityAdapterForThis(
            final PlayerInventory inventory, final PacketBuffer data) {
        if (tileEntityAdapter == null) {
            tileEntityAdapter =
                    (TileEntityAdapter<?>)
                            inventory.player.world.getTileEntity(data.readBlockPos());
        }
        return (TileEntityAdapter<T>) tileEntityAdapter;
    }

    public final ItemStackHandler getItemStackHandler() {
        return tileEntityAdapter.slots;
    }

    @Override
    public Slot addSlot(final Slot slotIn) {
        return super.addSlot(slotIn);
    }

    @Override
    public boolean canInteractWith(final PlayerEntity playerIn) {
        return container.canInteractWith(playerIn);
    }

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
                if (!mergeItemStack(slotStack, containerSlots, inventorySlots.size(), true))
                    return ItemStack.EMPTY;
            } else if (!mergeItemStack(slotStack, 0, containerSlots, false)) return ItemStack.EMPTY;

            if (slotStack.getCount() == 0) slot.putStack(ItemStack.EMPTY);
            else slot.onSlotChanged();

            if (slotStack.getCount() == returnStack.getCount()) return ItemStack.EMPTY;
            slot.onTake(player, slotStack);
        }
        return returnStack;
    }
}
