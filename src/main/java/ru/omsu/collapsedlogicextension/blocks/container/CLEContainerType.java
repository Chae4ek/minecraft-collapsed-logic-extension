package ru.omsu.collapsedlogicextension.blocks.container;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.network.IContainerFactory;
import ru.omsu.collapsedlogicextension.blocks.CLEBlock;
import ru.omsu.collapsedlogicextension.blocks.collapsedlogic.CollapsedLogicBlock;
import ru.omsu.collapsedlogicextension.blocks.collapsedlogic.LogicWireBlock;

import java.util.function.Supplier;

public enum CLEContainerType {
    COLLAPSED_LOGIC_BLOCK("collapsed_logic_block", () -> IForgeContainerType.create(LogicBlockContainer::new));

    /** Имя должно совпадать со всеми файлами ресурсов */
    private final String registryName;
    /** При каждом вызове должен возвращать новый экземпляр блока */
    private final Supplier<ContainerType<?>> constructor;

    CLEContainerType(final String registryName, final Supplier<ContainerType<?>> constructor) {
        this.registryName = registryName;
        this.constructor = constructor;
    }

    /** @return Регистрируемое имя блока */
    public String getRegistryName() {
        return registryName;
    }

    /** @return Конструктор блока */
    public Supplier<ContainerType<?>> getConstructor() {
        return constructor;
    }
}
