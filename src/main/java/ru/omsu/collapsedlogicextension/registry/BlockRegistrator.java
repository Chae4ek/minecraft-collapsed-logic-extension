package ru.omsu.collapsedlogicextension.registry;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import ru.omsu.collapsedlogicextension.CLEMod;
import ru.omsu.collapsedlogicextension.blocks.CLEBlockEnum;
import ru.omsu.collapsedlogicextension.exceptions.CLEErrorEnum;
import ru.omsu.collapsedlogicextension.exceptions.CLEException;

/**
 * Регистрирует все блоки мода и их аналоги предмета (item). При этом ничего не мешает создать для
 * блока отдельный предмет, т.к. он будет переопределен
 */
@EventBusSubscriber(modid = CLEMod.MOD_ID, bus = Bus.MOD)
public class BlockRegistrator {

    public static final List<RegistryObject<Block>> registryBlocks;

    private static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, CLEMod.MOD_ID);

    static {
        registryBlocks = new ArrayList<>();

        for (final CLEBlockEnum block : CLEBlockEnum.values()) {
            registryBlocks.add(BLOCKS.register(block.getRegistryName(), block.getConstructor()));
        }
    }

    /**
     * Регистрирует блоки. Следует вызывать только 1 раз при инициализации мода и ДО регистрации
     * предметов
     */
    public static void register() {
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    /**
     * Регистрирует аналоги предметов для блоков с настройками по-умолчанию из {@link
     * net.minecraft.item.Item.Properties}
     */
    @SubscribeEvent
    public static void onRegisterItems(final RegistryEvent.Register<Item> event) {
        final IForgeRegistry<Item> registry = event.getRegistry();
        for (final RegistryObject<Block> regBlock : BLOCKS.getEntries()) {
            final Block block = regBlock.get();
            final BlockItem blockItem =
                    new BlockItem(block, new Item.Properties().group(CLEMod.CLE_BLOCKS_TAB));
            final ResourceLocation resLocationBlock = block.getRegistryName();

            if (resLocationBlock == null) {
                throw new CLEException(CLEErrorEnum.REGISTERING_ITEM_BEFORE_ITS_BLOCK);
            }

            blockItem.setRegistryName(resLocationBlock);
            registry.register(blockItem);
        }
    }
}
