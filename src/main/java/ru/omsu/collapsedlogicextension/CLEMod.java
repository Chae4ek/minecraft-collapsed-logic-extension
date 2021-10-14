package ru.omsu.collapsedlogicextension;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ru.omsu.collapsedlogicextension.init.BlockInit;
import ru.omsu.collapsedlogicextension.init.ModContainerTypes;
import ru.omsu.collapsedlogicextension.init.ModTileEntityTypes;

import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.IForgeRegistry;

@SuppressWarnings("deprecation")
@Mod("collapsedlogicextension")
@Mod.EventBusSubscriber(modid = CLEMod.MOD_ID, bus = Bus.MOD)
public class CLEMod {

	public static final Logger LOGGER = LogManager.getLogger();
	public static final String MOD_ID = "collapsedlogicextension";
	public static CLEMod instance;

	public CLEMod() {
		final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

		BlockInit.BLOCKS.register(modEventBus);
		ModTileEntityTypes.TILE_ENTITY_TYPES.register(modEventBus);
		ModContainerTypes.CONTAINER_TYPES.register(modEventBus);

		instance = this;
		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent
	public static void onRegisterItems(final RegistryEvent.Register<Item> event) {
		final IForgeRegistry<Item> registry = event.getRegistry();

		BlockInit.BLOCKS.getEntries().stream().map(RegistryObject::get)
				.filter(block -> !(block instanceof FlowingFluidBlock))
				.forEach(block -> {
					final Item.Properties properties = new Item.Properties().group(TutorialItemGroup.instance);
					final BlockItem blockItem = new BlockItem(block, properties);
					blockItem.setRegistryName(block.getRegistryName());
					registry.register(blockItem);
				});

		LOGGER.debug("Registered BlockItems!");
	}

	public static class TutorialItemGroup extends ItemGroup {
		public static final ItemGroup instance = new TutorialItemGroup(ItemGroup.GROUPS.length, "tutorialtab");

		private TutorialItemGroup(int index, String label) {
			super(index, label);
		}

		@Override
		public ItemStack createIcon() {
			return new ItemStack(BlockInit.LOGIC_BLOCK.get());
		}
	}
}
