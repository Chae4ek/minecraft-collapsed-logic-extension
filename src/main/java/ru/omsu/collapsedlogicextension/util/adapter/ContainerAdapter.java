package ru.omsu.collapsedlogicextension.util.adapter;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.IWorldPosCallable;
import ru.omsu.collapsedlogicextension.init.ModObjectEnum;
import ru.omsu.collapsedlogicextension.init.Registrator;
import ru.omsu.collapsedlogicextension.util.api.ModTileEntity;
import ru.omsu.collapsedlogicextension.util.api.Unsafe;

/** Перехватывает методы физического контейнера */
public class ContainerAdapter<E extends ModTileEntity<E>> extends Container {

    private final TileEntityAdapter<E> tileEntityAdapter;
    private final IWorldPosCallable canInteractWithCallable;
    private final ModObjectEnum modObjectEnum;

    public ContainerAdapter(
            final ModObjectEnum modObjectEnum,
            final int windowId,
            final TileEntityAdapter<E> tileEntityAdapter) {
        super(Registrator.getContainerType(modObjectEnum), windowId);
        this.tileEntityAdapter = tileEntityAdapter;
        canInteractWithCallable =
                IWorldPosCallable.of(tileEntityAdapter.getWorld(), tileEntityAdapter.getPos());
        this.modObjectEnum = modObjectEnum;
    }

    public ContainerAdapter(
            final ModObjectEnum modObjectEnum,
            final int windowId,
            final PlayerInventory inventory,
            final PacketBuffer data) {
        this(
                modObjectEnum,
                windowId,
                (TileEntityAdapter<E>) inventory.player.world.getTileEntity(data.readBlockPos()));
    }

    /**
     * @return физический (майнкрафтовский) tile entity этого объекта, если он есть, иначе крашнется
     *     игра
     */
    @Unsafe
    @SuppressWarnings("unchecked")
    public <T extends ModTileEntity<T>> TileEntityAdapter<T> getTileEntityAdapterForThis() {
        return (TileEntityAdapter<T>) tileEntityAdapter;
    }

    @Override
    public boolean canInteractWith(final PlayerEntity playerIn) {
        return Container.isWithinUsableDistance(
                canInteractWithCallable, playerIn, Registrator.getBlock(modObjectEnum));
    }

    /** Нужен только для того, чтобы майн правильно регистрировал адаптер для разных типов блоков */
    public interface ModContainerFactory<E extends ModTileEntity<E>> {
        ContainerAdapter<E> create(
                final ModObjectEnum modObjectEnum,
                final int windowId,
                final TileEntityAdapter<E> tileEntityAdapter);
    }
}
