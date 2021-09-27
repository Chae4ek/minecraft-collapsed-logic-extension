package ru.omsu.collapsedlogicextension.items.collapsedlogic;

import net.minecraft.item.ItemGroup;
import ru.omsu.collapsedlogicextension.items.CLEItem;

import net.minecraft.item.Item;

public class LogicFloppyDisk extends CLEItem {
    public LogicFloppyDisk() {
        super(new Item.Properties().group(ItemGroup.TOOLS).maxStackSize(1));
    }
}
