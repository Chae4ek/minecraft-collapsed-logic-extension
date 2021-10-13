package ru.omsu.collapsedlogicextension.blocks;

import java.util.function.Supplier;

import net.minecraft.block.RedstoneWireBlock;
import ru.omsu.collapsedlogicextension.blocks.collapsedlogic.*;

public enum CLEBlockEnum {
    COLLAPSED_LOGIC_BLOCK("collapsed_logic_block", CollapsedLogicBlock::new);

    private final String registryName;
    private final Supplier<CLEBlock> constructor;

    CLEBlockEnum(final String registryName, final Supplier<CLEBlock> constructor) {
        this.registryName = registryName;
        this.constructor = constructor;
    }

    public String getRegistryName() {
        return registryName;
    }

    public Supplier<CLEBlock> getConstructor() {
        return constructor;
    }
}
