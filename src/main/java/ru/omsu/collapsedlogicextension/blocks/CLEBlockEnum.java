package ru.omsu.collapsedlogicextension.blocks;

import java.util.function.Supplier;
import ru.omsu.collapsedlogicextension.blocks.collapsedlogic.CollapsedLogicBlock;

/** Хранит все блоки мода */
public enum CLEBlockEnum {
    COLLAPSED_LOGIC_BLOCK("collapsed_logic_block", CollapsedLogicBlock::new);

    /** Имя должно совпадать со всеми файлами ресурсов */
    private final String registryName;
    /** При каждом вызове должен возвращать новый экземпляр блока */
    private final Supplier<CLEBlock> constructor;

    CLEBlockEnum(final String registryName, final Supplier<CLEBlock> constructor) {
        this.registryName = registryName;
        this.constructor = constructor;
    }

    /** @return Регистрируемое имя блока */
    public String getRegistryName() {
        return registryName;
    }

    /** @return Конструктор блока */
    public Supplier<CLEBlock> getConstructor() {
        return constructor;
    }
}
