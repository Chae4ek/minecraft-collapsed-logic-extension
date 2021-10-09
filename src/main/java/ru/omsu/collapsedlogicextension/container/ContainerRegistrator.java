package ru.omsu.collapsedlogicextension.container;

import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;

import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import ru.omsu.collapsedlogicextension.CLEMod;

public class ContainerRegistrator {

    private static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister
            .create(ForgeRegistries.CONTAINERS, CLEMod.MOD_ID);

    public static final RegistryObject<ContainerType<LogicBlockContainer>> COLLAPSED_LOGIC_BLOCK_CONTAINER =
            CONTAINERS.register("collapsed_logic_block",
                    () -> IForgeContainerType.create(LogicBlockContainer::new));

    public static void register() {
        CONTAINERS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

}
