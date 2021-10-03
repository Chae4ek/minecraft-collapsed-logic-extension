package ru.omsu.collapsedlogicextension;

import net.minecraft.block.BlastFurnaceBlock;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.EnchantingTableBlock;
import net.minecraft.inventory.container.EnchantmentContainer;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.Mod;
import ru.omsu.collapsedlogicextension.blocks.BlockRegistrator;
import ru.omsu.collapsedlogicextension.blocks.container.ContainerRegistrator;
import ru.omsu.collapsedlogicextension.itemgroups.CLEBlocksGroup;
import ru.omsu.collapsedlogicextension.itemgroups.CLEItemsGroup;
import ru.omsu.collapsedlogicextension.items.ItemRegistrator;
import ru.omsu.collapsedlogicextension.tileentity.TileEntityRegistrator;

import java.awt.*;

@Mod(CLEMod.MOD_ID)
public class CLEMod {

    public static final String MOD_ID = "collapsedlogicextension";

    public static final ItemGroup CLE_BLOCKS_TAB = new CLEBlocksGroup(MOD_ID + ".blocks");
    public static final ItemGroup CLE_ITEMS_TAB = new CLEItemsGroup(MOD_ID + ".items");

    public CLEMod() {
        BlockRegistrator.register();
        ItemRegistrator.register();
        TileEntityRegistrator.register();
        ContainerRegistrator.register();
    }
}
