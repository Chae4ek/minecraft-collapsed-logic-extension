package ru.omsu.collapsedlogicextension.util.api;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.IWorldPosCallable;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;
import ru.omsu.collapsedlogicextension.init.ModObjectEnum;
import ru.omsu.collapsedlogicextension.init.Registrator;
import ru.omsu.collapsedlogicextension.util.adapter.ContainerAdapter;
import ru.omsu.collapsedlogicextension.util.adapter.TileEntityAdapter;

/** Основной класс для всех контейнеров мода */
public abstract class ModContainer<E extends ModContainer<E>> {

    private final ContainerAdapter<E> containerAdapter;
    private int lastSlotIndex;

    public ModContainer(
            final ContainerAdapter<E> containerAdapter,
            final TileEntityAdapter<?> tileEntityAdapter) {
        this.containerAdapter = containerAdapter;
    }

    /** @return true, если блок находится на юзабельном расстоянии от игрока */
    public static boolean isWithinUsableDistance(
            final IWorldPosCallable canInteractWithCallable,
            final PlayerEntity playerIn,
            final ModObjectEnum modObjectEnum) {
        return ContainerAdapter.isWithinUsableDistance(
                canInteractWithCallable, playerIn, Registrator.getBlock(modObjectEnum));
    }

    /** @return все слоты для этого контейнера */
    public final ItemStackHandler getItemStackHandler() {
        return containerAdapter.getItemStackHandler();
    }

    /**
     * Этот метод определяет, когда именно игрок может взаимодействовать с контейнером. Например,
     * когда игрок кликает ПКМ по предмету или блоку, может открыться меню с контейнерами для
     * взаимодействия предметов в инветаре игрока. Вероятно, может привести к неожиданному
     * поведению, если использовать без GUI
     *
     * @return true, если контейнер интерактивен с игроком в текущий момент
     */
    public boolean canInteractWith(final PlayerEntity playerIn) {
        return false;
    }

    /** Добавляет новый слот в контейнер на координаты (x,y), его индекс увеличится */
    public final void addNextSlot(final IItemHandler allSlots, final int x, final int y) {
        containerAdapter.addSlot(new SlotItemHandler(allSlots, lastSlotIndex++, x, y));
    }

    public interface ModContainerFactory<E extends ModContainer<E>> {
        E create(ContainerAdapter<E> containerAdapter, TileEntityAdapter<?> tileEntityAdapter);
    }
}
