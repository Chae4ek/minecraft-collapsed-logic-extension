package ru.omsu.collapsedlogicextension.init;

import java.util.EnumMap;
import java.util.Map;
import net.minecraft.block.Block;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import ru.omsu.collapsedlogicextension.init.ModObjectEnum.ModObject;
import ru.omsu.collapsedlogicextension.util.adapter.BlockAdapter;
import ru.omsu.collapsedlogicextension.util.adapter.ContainerAdapter;
import ru.omsu.collapsedlogicextension.util.adapter.ContainerScreenAdapter;
import ru.omsu.collapsedlogicextension.util.adapter.ItemAdapter;
import ru.omsu.collapsedlogicextension.util.adapter.TileEntityAdapter;

/** Регистрирует и хранит все объекты мода */
@EventBusSubscriber(modid = ModInit.MOD_ID, bus = Bus.MOD)
public class Registrator {

    /** Все зарегистрированные предметы (items) мода */
    private static final Map<ModObjectEnum, RegistryObject<Item>> registeredItems =
            new EnumMap<>(ModObjectEnum.class);
    /** Все зарегистрированные блоки мода */
    private static final Map<ModObjectEnum, RegistryObject<Block>> registeredBlocks =
            new EnumMap<>(ModObjectEnum.class);
    /** Все зарегистрированные tile entities мода */
    private static final Map<ModObjectEnum, RegistryObject<TileEntityType<?>>>
            registeredTileEntities = new EnumMap<>(ModObjectEnum.class);
    /** Все зарегистрированные контейнеры мода */
    private static final Map<ModObjectEnum, RegistryObject<ContainerType<ContainerAdapter<?>>>>
            registeredContainers = new EnumMap<>(ModObjectEnum.class);

    /** Регистратор предметов */
    private static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, ModInit.MOD_ID);
    /** Регистратор блоков */
    private static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, ModInit.MOD_ID);
    /** Регистратор tile entities */
    private static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES =
            DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, ModInit.MOD_ID);
    /** Регистратор контейнеров */
    private static final DeferredRegister<ContainerType<?>> CONTAINERS =
            DeferredRegister.create(ForgeRegistries.CONTAINERS, ModInit.MOD_ID);

    /** @return зарегистрированный предмет */
    public static Item getItem(final ModObjectEnum modObject) {
        return registeredItems.get(modObject).get();
    }

    /** @return зарегистрированный блок */
    public static Block getBlock(final ModObjectEnum modObject) {
        return registeredBlocks.get(modObject).get();
    }

    /** @return зарегистрированный tile entity */
    public static TileEntityType<?> getTileEntityType(final ModObjectEnum modObject) {
        return registeredTileEntities.get(modObject).get();
    }

    /** @return зарегистрированный контейнер */
    public static ContainerType<ContainerAdapter<?>> getContainerType(
            final ModObjectEnum modObject) {
        return registeredContainers.get(modObject).get();
    }

    /** Регистрирует все объекты мода */
    public static void registerAll() {
        for (final ModObjectEnum modObjectEnum : ModObjectEnum.values()) {
            final ModObject<?, ?, ?, ?, ?> modObject = modObjectEnum.modObject;
            final String registryName = modObject.registryName;

            // Регистрация предмета (item) для объекта мода, если он есть
            if (modObject.itemFactory != null) {
                registeredItems.put(
                        modObjectEnum,
                        ITEMS.register(registryName, () -> new ItemAdapter<>(modObject)));
            }

            // Регистрация блока для объекта мода, если он есть
            if (modObject.blockFactory != null) {
                final RegistryObject<Block> registeredBlock =
                        BLOCKS.register(registryName, () -> new BlockAdapter<>(modObject));
                registeredBlocks.put(modObjectEnum, registeredBlock);

                // Регистрация tile entity блока для объекта мода, если он есть
                if (modObject.tileEntityFactory != null) {
                    registeredTileEntities.put(
                            modObjectEnum,
                            TILE_ENTITIES.register(
                                    registryName,
                                    () ->
                                            TileEntityType.Builder.create(
                                                            () ->
                                                                    new TileEntityAdapter<>(
                                                                            modObject),
                                                            registeredBlock.get())
                                                    .build(null)));
                }
            }

            // Регистрация контейнера для объекта мода, если он есть
            if (modObject.containerFactory != null) {
                registeredContainers.put(
                        modObjectEnum,
                        CONTAINERS.register(
                                registryName,
                                () ->
                                        IForgeContainerType.create(
                                                (windowId, inv, data) ->
                                                        new ContainerAdapter<>(
                                                                modObject, windowId, inv, data))));
            }
        }

        final IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        ITEMS.register(bus);
        BLOCKS.register(bus);
        TILE_ENTITIES.register(bus);
        CONTAINERS.register(bus);
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
                    new BlockItem(block, new Item.Properties().group(ModInit.MOD_GROUP));
            blockItem.setRegistryName(block.getRegistryName());
            registry.register(blockItem);
        }
    }

    /** Регистрация GUI только для клиента */
    @OnlyIn(Dist.CLIENT)
    @EventBusSubscriber(modid = ModInit.MOD_ID, bus = Bus.MOD, value = Dist.CLIENT)
    public static class ClientEventBusSubscriber {

        // Без каста к raw типу, компилятор java ведет себя странно
        @SuppressWarnings("unchecked")
        @OnlyIn(Dist.CLIENT)
        @SubscribeEvent
        public static void clientSetup(final FMLClientSetupEvent event) {
            for (final ModObjectEnum modObjectEnum : ModObjectEnum.values()) {
                if (modObjectEnum.modObject.containerScreenFactory != null) {
                    ScreenManager.registerFactory(
                            getContainerType(modObjectEnum),
                            (ScreenManager.IScreenFactory)
                                    (container, inventory, title) ->
                                            new ContainerScreenAdapter<>(
                                                    modObjectEnum.modObject,
                                                    (ContainerAdapter) container,
                                                    inventory,
                                                    title));
                }
            }
        }
    }
}
