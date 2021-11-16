package ru.omsu.collapsedlogicextension.util.proxy;

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
public class ContainerProxy<E extends ModTileEntity<E>> extends Container {

    private final TileEntityProxy<E> tileEntityProxy;
    private final IWorldPosCallable canInteractWithCallable;
    private final ModObjectEnum modObjectEnum;

    public ContainerProxy(
            final ModObjectEnum modObjectEnum,
            final int windowId,
            final TileEntityProxy<E> tileEntityProxy) {
        super(Registrator.getContainerType(modObjectEnum), windowId);
        this.tileEntityProxy = tileEntityProxy;
        canInteractWithCallable =
                IWorldPosCallable.of(tileEntityProxy.getWorld(), tileEntityProxy.getPos());
        this.modObjectEnum = modObjectEnum;
    }

    public ContainerProxy(
            final ModObjectEnum modObjectEnum,
            final int windowId,
            final PlayerInventory inventory,
            final PacketBuffer data) {
        this(
                modObjectEnum,
                windowId,
                (TileEntityProxy<E>) inventory.player.world.getTileEntity(data.readBlockPos()));
    }

    /**
     * @return физический (майнкрафтовский) tile entity этого объекта, если он есть, иначе крашнется
     *     игра
     */
    @Unsafe
    @SuppressWarnings("unchecked")
    public <T extends ModTileEntity<T>> TileEntityProxy<T> getTileEntityAdapterForThis() {
        return (TileEntityProxy<T>) tileEntityProxy;
    }

    @Override
    public boolean canInteractWith(final PlayerEntity playerIn) {
        return Container.isWithinUsableDistance(
                canInteractWithCallable, playerIn, Registrator.getBlock(modObjectEnum));
    }

    /** Нужен только для того, чтобы майн правильно регистрировал адаптер для разных типов блоков */
    public interface ModContainerFactory<E extends ModTileEntity<E>> {
        ContainerProxy<E> create(
                final ModObjectEnum modObjectEnum,
                final int windowId,
                final TileEntityProxy<E> tileEntityProxy);
    }
}
