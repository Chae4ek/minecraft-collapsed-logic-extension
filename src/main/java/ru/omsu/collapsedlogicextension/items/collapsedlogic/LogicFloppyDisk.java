package ru.omsu.collapsedlogicextension.items.collapsedlogic;

import net.minecraft.item.Item;
import ru.omsu.collapsedlogicextension.CLEMod;
import ru.omsu.collapsedlogicextension.items.CLEItem;

/**
 * Хранит в себе редстоун схему для {@link
 * ru.omsu.collapsedlogicextension.blocks.collapsedlogic.CollapsedLogicBlock}
 */
public class LogicFloppyDisk extends CLEItem {

    public LogicFloppyDisk() {
        super(new Item.Properties().group(CLEMod.CLE_ITEMS_TAB).maxStackSize(1));
    }
}
