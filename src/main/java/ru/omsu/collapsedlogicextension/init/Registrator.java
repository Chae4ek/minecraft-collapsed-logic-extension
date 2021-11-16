package ru.omsu.collapsedlogicextension.init;

import java.util.EnumMap;
import java.util.Map;
import net.minecraft.block.Block;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import ru.omsu.collapsedlogicextension.init.ModObjectEnum.ModObject;
import ru.omsu.collapsedlogicextension.util.proxy.BlockProxy;
import ru.omsu.collapsedlogicextension.util.proxy.ContainerProxy;
import ru.omsu.collapsedlogicextension.util.proxy.ContainerScreenProxy;
import ru.omsu.collapsedlogicextension.util.proxy.TileEntityProxy;

/** Регистрирует и хранит все объекты мода */
public class Registrator {

    /** Все зарегистрированные блоки мода */
    private static final Map<ModObjectEnum, RegistryObject<Block>> registeredBlocks =
            new EnumMap<>(ModObjectEnum.class);
    /** Все зарегистрированные tile entities мода */
    private static final Map<ModObjectEnum, RegistryObject<TileEntityType<?>>>
            registeredTileEntities = new EnumMap<>(ModObjectEnum.class);
    /** Все зарегистрированные контейнеры мода */
    private static final Map<ModObjectEnum, RegistryObject<ContainerType<ContainerProxy<?>>>>
            registeredContainers = new EnumMap<>(ModObjectEnum.class);

    // TODO: сделать нормальную мапу
    public static RegistryObject<SoundEvent> buttonClick;

    /** @return зарегистрированный блок */
    public static Block getBlock(final ModObjectEnum modObject) {
        return registeredBlocks.get(modObject).get();
    }

    /** @return зарегистрированный tile entity */
    public static TileEntityType<?> getTileEntityType(final ModObjectEnum modObject) {
        return registeredTileEntities.get(modObject).get();
    }

    /** @return зарегистрированный контейнер */
    public static ContainerType<ContainerProxy<?>> getContainerType(
            final ModObjectEnum modObject) {
        return registeredContainers.get(modObject).get();
    }

    /** Регистрирует все объекты мода */
    public static void registerAll() {
        // Регистратор предметов
        final DeferredRegister<Item> ITEMS =
                DeferredRegister.create(ForgeRegistries.ITEMS, ModInit.MOD_ID);
        // Регистратор блоков
        final DeferredRegister<Block> BLOCKS =
                DeferredRegister.create(ForgeRegistries.BLOCKS, ModInit.MOD_ID);
        // Регистратор tile entities
        final DeferredRegister<TileEntityType<?>> TILE_ENTITIES =
                DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, ModInit.MOD_ID);
        // Регистратор контейнеров
        final DeferredRegister<ContainerType<?>> CONTAINERS =
                DeferredRegister.create(ForgeRegistries.CONTAINERS, ModInit.MOD_ID);
        // Регистратор звуков
        final DeferredRegister<SoundEvent> SOUNDS =
                DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, ModInit.MOD_ID);

        // TODO: сделать отдельное перечисление звуков?
        final ResourceLocation button = new ResourceLocation(ModInit.MOD_ID, "button_click");
        buttonClick = SOUNDS.register("button_click", () -> new SoundEvent(button));

        for (final ModObjectEnum modObjectEnum : ModObjectEnum.values()) {
            final ModObject<?, ?, ?> modObject = modObjectEnum.modObject;
            final String registryName = modObject.registryName;

            // Регистрация блока для объекта мода, если он есть
            if (modObject.blockFactory != null) {
                final RegistryObject<Block> registeredBlock =
                        BLOCKS.register(registryName, () -> new BlockProxy<>(modObject));
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
                                                                    new TileEntityProxy<>(
                                                                            modObject),
                                                            registeredBlock.get())
                                                    .build(null)));
                }

                ITEMS.register(
                        registryName,
                        () ->
                                new BlockItem(
                                        registeredBlock.get(),
                                        new Item.Properties().group(ModInit.MOD_GROUP)));
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
                                                        new ContainerProxy<>(
                                                                modObject.thisEnum,
                                                                windowId,
                                                                inv,
                                                                data))));
            }
        }

        final IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        BLOCKS.register(bus);
        TILE_ENTITIES.register(bus);
        CONTAINERS.register(bus);
        SOUNDS.register(bus);
        ITEMS.register(bus);
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
                                            new ContainerScreenProxy<>(
                                                    modObjectEnum.modObject,
                                                    (ContainerProxy) container,
                                                    inventory,
                                                    title));
                }
            }
        }
    }
}
