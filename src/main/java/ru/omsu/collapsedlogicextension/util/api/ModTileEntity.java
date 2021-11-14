package ru.omsu.collapsedlogicextension.util.api;

import net.minecraft.nbt.CompoundNBT;

/** Основной класс для всех tile entities мода */
public abstract class ModTileEntity<E extends ModTileEntity<E>> {

    /** Читает данные из NBT и записывает их в этот tile entity */
    public void read(final CompoundNBT compound) {}

    /** Записывает данные в NBT из этого tile entity */
    public CompoundNBT write(final CompoundNBT compound) {
        return compound;
    }

    /** Вызывается с каждым игровым тиком (примерно 20 раз в секунду) */
    public void update() {}

    public interface ModTileEntityFactory<E extends ModTileEntity<E>> {
        E create();
    }
}
