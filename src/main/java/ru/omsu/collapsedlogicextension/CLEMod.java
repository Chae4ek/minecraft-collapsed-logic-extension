package ru.omsu.collapsedlogicextension;

import net.minecraft.item.ItemGroup;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import ru.omsu.collapsedlogicextension.registry.BlockRegistrator;
import ru.omsu.collapsedlogicextension.registry.ContainerRegistrator;
import ru.omsu.collapsedlogicextension.itemgroups.CLEBlocksGroup;
import ru.omsu.collapsedlogicextension.itemgroups.CLEItemsGroup;
import ru.omsu.collapsedlogicextension.registry.ItemRegistrator;
import ru.omsu.collapsedlogicextension.registry.TileEntityRegistrator;

@Mod(CLEMod.MOD_ID)
public class CLEMod {

    public static final String MOD_ID = "collapsedlogicextension";

    public static final ItemGroup CLE_BLOCKS_TAB = new CLEBlocksGroup(MOD_ID + ".blocks");
    public static final ItemGroup CLE_ITEMS_TAB = new CLEItemsGroup(MOD_ID + ".items");

    public CLEMod() {
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        BlockRegistrator.register();
        ItemRegistrator.register();

        TileEntityRegistrator.TILE_ENTITY_TYPES.register(modEventBus);

        ContainerRegistrator.register();
    }
}
