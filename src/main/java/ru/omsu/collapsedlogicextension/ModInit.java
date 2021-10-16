package ru.omsu.collapsedlogicextension;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.block.Block;
import net.minecraft.client.gui.IHasContainer;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.gui.ScreenManager.IScreenFactory;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import ru.omsu.collapsedlogicextension.blocks.logicblock.LogicBlock;
import ru.omsu.collapsedlogicextension.blocks.logicblock.gui.LogicBlockScreen;
import ru.omsu.collapsedlogicextension.blocks.logicblock.util.LogicBlockContainer;
import ru.omsu.collapsedlogicextension.blocks.logicblock.util.LogicBlockTileEntity;

/** Основной класс для инициализации мода и регистрации объектов */
@Mod(ModInit.MOD_ID)
@EventBusSubscriber(modid = ModInit.MOD_ID, bus = Bus.MOD)
public class ModInit {

    public static final String MOD_ID = "collapsedlogicextension";

    /** Все зарегистрированные контейнеры мода */
    private static final Map<ModObjectEnum, RegistryObject<ContainerType<?>>> registeredContainers =
            new EnumMap<>(ModObjectEnum.class);
    /** Все зарегистрированные tile entities мода */
    private static final Map<ModObjectEnum, RegistryObject<TileEntityType<?>>>
            registeredTileEntities = new EnumMap<>(ModObjectEnum.class);
    /** Все зарегистрированные предметы (item) мода */
    private static final Map<ModObjectEnum, RegistryObject<Item>> registeredItems =
            new EnumMap<>(ModObjectEnum.class);
    /** Все зарегистрированные блоки мода */
    private static final Map<ModObjectEnum, RegistryObject<Block>> registeredBlocks =
            new EnumMap<>(ModObjectEnum.class);

    /** Вкладка в инвентаре креатива для предметов мода */
    public static final ItemGroup MOD_GROUP =
            new ItemGroup(MOD_ID) {
                @OnlyIn(Dist.CLIENT)
                @Override
                public ItemStack createIcon() {
                    return new ItemStack(getBlock(ModObjectEnum.LOGIC_BLOCK));
                }
            };

    /** Регистратор предметов */
    private static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, ModInit.MOD_ID);
    /** Регистратор блоков */
    private static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, ModInit.MOD_ID);
    /** Регистратор контейнеров блоков */
    private static final DeferredRegister<ContainerType<?>> CONTAINER_TYPES =
            DeferredRegister.create(ForgeRegistries.CONTAINERS, ModInit.MOD_ID);
    /** Регистратор tile entities */
    private static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, ModInit.MOD_ID);

    static {
        for (final ModObjectEnum modObject : ModObjectEnum.values()) {
            // Регистрация предмета (item) для объекта мода, если он есть
            if (modObject.itemFactory != null) {
                registeredItems.put(
                        modObject, ITEMS.register(modObject.registryName, modObject.itemFactory));
            }

            // Регистрация блока для объекта мода, если он есть
            if (modObject.blockFactory != null) {
                final RegistryObject<Block> registeredBlock =
                        BLOCKS.register(modObject.registryName, modObject.blockFactory);
                registeredBlocks.put(modObject, registeredBlock);

                // Регистрация tile entity блока для объекта мода, если он есть
                if (modObject.tileEntityFactory != null) {
                    registeredTileEntities.put(
                            modObject,
                            TILE_ENTITY_TYPES.register(
                                    modObject.registryName,
                                    () ->
                                            TileEntityType.Builder.create(
                                                            modObject.tileEntityFactory,
                                                            registeredBlock.get())
                                                    .build(null)));
                }
            }

            // Регистрация контейнера для объекта мода, если он есть
            if (modObject.containerFactory != null) {
                registeredContainers.put(
                        modObject,
                        CONTAINER_TYPES.register(
                                modObject.registryName,
                                () -> IForgeContainerType.create(modObject.containerFactory)));
            }
        }
    }

    public ModInit() {
        final IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        BLOCKS.register(bus);
        ITEMS.register(bus);
        TILE_ENTITY_TYPES.register(bus);
        CONTAINER_TYPES.register(bus);
    }

    public static Item getItem(final ModObjectEnum modObject) {
        return registeredItems.get(modObject).get();
    }

    public static Block getBlock(final ModObjectEnum modObject) {
        return registeredBlocks.get(modObject).get();
    }

    public static ContainerType<?> getContainerType(final ModObjectEnum modObject) {
        return registeredContainers.get(modObject).get();
    }

    public static TileEntityType<?> getTileEntityType(final ModObjectEnum modObject) {
        return registeredTileEntities.get(modObject).get();
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

    /** Описывает все объекты мода: блоки, контейнеры, tile entity и предметы (item) */
    public enum ModObjectEnum {
        LOGIC_BLOCK(
                "logic_block",
                null,
                LogicBlock::new,
                LogicBlockContainer::new,
                LogicBlockScreen::new,
                LogicBlockTileEntity::new);

        /** Регистрируемое имя должно совпадать со всеми файлами ресурсов */
        private final String registryName;
        /** Фабрика предмета (item), если есть, иначе null */
        private final Supplier<Item> itemFactory;
        /** Фабрика блока, если есть, иначе null */
        private final Supplier<Block> blockFactory;
        /** Фабрика контейнера, если есть, иначе null */
        private final IContainerFactory<?> containerFactory;
        /** Фабрика GUI для контейнера, если есть, иначе null */
        private final IScreenFactory<?, ?> screenFactory;
        /** Фабрика tile entity, если есть, иначе null */
        private final Supplier<TileEntity> tileEntityFactory;

        <M extends Container, U extends Screen & IHasContainer<M>> ModObjectEnum(
                final String registryName,
                final Supplier<Item> itemFactory,
                final Supplier<Block> blockFactory,
                final IContainerFactory<?> containerFactory,
                final IScreenFactory<M, U> screenFactory,
                final Supplier<TileEntity> tileEntityFactory) {
            this.registryName = registryName;
            this.itemFactory = itemFactory;
            this.blockFactory = blockFactory;
            this.containerFactory = containerFactory;
            this.screenFactory = screenFactory;
            this.tileEntityFactory = tileEntityFactory;
        }
    }

    /** Регистрация GUI только для клиента */
    @OnlyIn(Dist.CLIENT)
    @EventBusSubscriber(modid = ModInit.MOD_ID, bus = Bus.MOD, value = Dist.CLIENT)
    public static class ClientEventBusSubscriber {

        @OnlyIn(Dist.CLIENT)
        @SubscribeEvent
        public static void clientSetup(final FMLClientSetupEvent event) {
            for (final ModObjectEnum modObject : ModObjectEnum.values()) {
                if (modObject.screenFactory != null) {
                    ScreenManager.registerFactory(
                            (ContainerType) getContainerType(modObject), modObject.screenFactory);
                }
            }
        }
    }
}
