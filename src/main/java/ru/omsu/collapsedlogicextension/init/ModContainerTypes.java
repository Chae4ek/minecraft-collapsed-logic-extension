package ru.omsu.collapsedlogicextension.init;

import ru.omsu.collapsedlogicextension.CLEMod;
import ru.omsu.collapsedlogicextension.container.LogicBlockContainer;

import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModContainerTypes {

	public static final DeferredRegister<ContainerType<?>> CONTAINER_TYPES = new DeferredRegister<>(
			ForgeRegistries.CONTAINERS, CLEMod.MOD_ID);

	public static final RegistryObject<ContainerType<LogicBlockContainer>> EXAMPLE_FURNACE = CONTAINER_TYPES
			.register("example_furnace", () -> IForgeContainerType.create(LogicBlockContainer::new));
}
