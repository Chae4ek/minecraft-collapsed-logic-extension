package ru.omsu.collapsedlogicextension.itemgroups;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import ru.omsu.collapsedlogicextension.blocks.BlockRegistrator;

/** Вкладка в инвентаре креатива для блоков мода */
public class CLEBlocksGroup extends ItemGroup {

    public CLEBlocksGroup(final String name) {
        super(name);
    }

    @Override
    public ItemStack createIcon() {
        return new ItemStack(BlockRegistrator.registryBlocks.get(0).get());
    }
}
