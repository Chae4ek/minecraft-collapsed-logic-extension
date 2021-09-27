package ru.omsu.collapsedlogicextension;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraftforge.fml.common.Mod;
import ru.omsu.collapsedlogicextension.blocks.BlockRegistrator;
import ru.omsu.collapsedlogicextension.itemgroups.CLEBlocksGroup;
import ru.omsu.collapsedlogicextension.items.ItemRegistrator;

@Mod(CLEMod.MOD_ID)
public class CLEMod {

    public static final String MOD_ID = "collapsedlogicextension";

    public static final ItemGroup CLEBLOCKS_TAB = new CLEBlocksGroup(MOD_ID);

    public CLEMod() {
        BlockRegistrator.register();
        ItemRegistrator.register();
    }
}
