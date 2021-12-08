package ru.omsu.collapsedlogicextension.init.forgeregistrators;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import ru.omsu.collapsedlogicextension.init.ModInit;
import ru.omsu.collapsedlogicextension.init.Registrator;
import ru.omsu.collapsedlogicextension.logicblock.LogicBlockContainer;
import ru.omsu.collapsedlogicextension.logicblock.LogicBlockScreen;

public class ForgeContainerRegistrator implements Registrator {

    private static ForgeContainerRegistrator instance;

    /** Все зарегистрированные контейнеры мода */
    private final Map<Class<? extends Container>, RegistryObject<ContainerType<Container>>>
            registeredContainers = new HashMap<>();

    private ForgeContainerRegistrator() {}

    public static ForgeContainerRegistrator getInstance() {
        return instance == null ? instance = new ForgeContainerRegistrator() : instance;
    }

    /** @return зарегистрированный контейнер */
    public ContainerType<Container> getContainerType(final Class<? extends Container> clazz) {
        return registeredContainers.get(clazz).get();
    }

    @Override
    public void registerAll() {
        // регистратор контейнеров
        final DeferredRegister<ContainerType<?>> CONTAINERS =
                DeferredRegister.create(ForgeRegistries.CONTAINERS, ModInit.MOD_ID);

        registeredContainers.put(
                LogicBlockContainer.class,
                CONTAINERS.register(
                        "logic_block",
                        () ->
                                IForgeContainerType.create(
                                        (windowId, inv, data) ->
                                                new LogicBlockContainer(
                                                        registeredContainers
                                                                .get(LogicBlockContainer.class)
                                                                .get(),
                                                        windowId,
                                                        inv,
                                                        data))));

        CONTAINERS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    /** Регистрация GUI только для клиента */
    @OnlyIn(Dist.CLIENT)
    @EventBusSubscriber(modid = ModInit.MOD_ID, bus = Bus.MOD, value = Dist.CLIENT)
    public static class ClientEventBusSubscriber {

        @OnlyIn(Dist.CLIENT)
        @SubscribeEvent
        public static void clientSetup(final FMLClientSetupEvent event) {
            final ForgeContainerRegistrator containerRegistrator =
                    ForgeContainerRegistrator.getInstance();
            ScreenManager.registerFactory(
                    containerRegistrator.getContainerType(LogicBlockContainer.class),
                    (ScreenManager.IScreenFactory)
                            (container, inventory, title) ->
                                    new LogicBlockScreen(
                                            (LogicBlockContainer) container, inventory, title));
        }
    }
}
