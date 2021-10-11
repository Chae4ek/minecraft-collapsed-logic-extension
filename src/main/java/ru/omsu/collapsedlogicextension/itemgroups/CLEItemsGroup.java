package ru.omsu.collapsedlogicextension.itemgroups;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import ru.omsu.collapsedlogicextension.registry.ItemRegistrator;

/** Вкладка в инвентаре креатива для предметов мода */
public class CLEItemsGroup extends ItemGroup {

    public CLEItemsGroup(final String name) {
        super(name);
    }

    @Override
    public ItemStack createIcon() {
        return new ItemStack(ItemRegistrator.registryItems.get(0).get());
    }
}
