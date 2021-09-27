package ru.omsu.collapsedlogicextension.items;

import ru.omsu.collapsedlogicextension.blocks.CLEBlock;
import ru.omsu.collapsedlogicextension.items.collapsedlogic.LogicFloppyDisk;

import java.util.function.Supplier;

public enum CLEItemEnum {
    LOGIC_FLOPPY_DISK("logic_floppy_disk", LogicFloppyDisk::new);

    /** Имя должно совпадать со всеми файлами ресурсов */
    private final String registryName;
    /** При каждом вызове должен возвращать новый экземпляр предмета */
    private final Supplier<CLEItem> constructor;

    CLEItemEnum(final String registryName, final Supplier<CLEItem> constructor) {
        this.registryName = registryName;
        this.constructor = constructor;
    }

    /** @return Регистрируемое имя предмета */
    public String getRegistryName() {
        return registryName;
    }

    /** @return Конструктор предмета */
    public Supplier<CLEItem> getConstructor() {
        return constructor;
    }
}
