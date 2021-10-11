package ru.omsu.collapsedlogicextension.util;

import net.minecraft.client.gui.ScreenManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import ru.omsu.collapsedlogicextension.CLEMod;
import ru.omsu.collapsedlogicextension.gui.LogicBlockScreen;
import ru.omsu.collapsedlogicextension.registry.ContainerRegistrator;

@Mod.EventBusSubscriber(modid = CLEMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEventBusSubscriber {

    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        ScreenManager.registerFactory(ContainerRegistrator.COLLAPSED_LOGIC_BLOCK_CONTAINER.get(), LogicBlockScreen::new);
    }
}

