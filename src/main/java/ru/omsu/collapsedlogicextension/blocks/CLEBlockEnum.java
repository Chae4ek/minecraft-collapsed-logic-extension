package ru.omsu.collapsedlogicextension.blocks;

import java.util.function.Supplier;
import ru.omsu.collapsedlogicextension.blocks.collapsedlogic.CollapsedLogicBlock;

public enum CLEBlockEnum {
    COLLAPSED_LOGIC_BLOCK("collapsed_logic_block", CollapsedLogicBlock::new);

    private final String name;
    private final Supplier<CLEBlock> constructor;

    CLEBlockEnum(final String name, final Supplier<CLEBlock> constructor) {
        this.name = name;
        this.constructor = constructor;
    }

    public String getName() {
        return name;
    }

    public Supplier<CLEBlock> getConstructor() {
        return constructor;
    }
}
