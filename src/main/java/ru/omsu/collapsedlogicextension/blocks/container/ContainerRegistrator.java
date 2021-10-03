package ru.omsu.collapsedlogicextension.blocks.container;

import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.fml.RegistryObject;

import java.util.ArrayList;
import java.util.List;

import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import ru.omsu.collapsedlogicextension.CLEMod;

public class ContainerRegistrator {

    private static List<RegistryObject<ContainerType<?>>> registryContainers;

    private static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister
            .create(ForgeRegistries.CONTAINERS, CLEMod.MOD_ID);

    static {
        registryContainers = new ArrayList<>();

        for(CLEContainerType containerType : CLEContainerType.values()){
            registryContainers.add(CONTAINERS.register(containerType.getRegistryName(), containerType.getConstructor()));
        }
    }

    public static void register() {
        CONTAINERS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

}
