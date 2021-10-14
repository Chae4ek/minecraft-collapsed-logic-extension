package ru.omsu.collapsedlogicextension.init;

import ru.omsu.collapsedlogicextension.CLEMod;
import ru.omsu.collapsedlogicextension.tileentity.LogicBlockTileEntity;

import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModTileEntityTypes {

	public static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPES = new DeferredRegister<>(
			ForgeRegistries.TILE_ENTITIES, CLEMod.MOD_ID);

	public static final RegistryObject<TileEntityType<LogicBlockTileEntity>> LOGIC_BLOCK = TILE_ENTITY_TYPES
			.register("logic_block", () -> TileEntityType.Builder
					.create(LogicBlockTileEntity::new, BlockInit.LOGIC_BLOCK.get()).build(null));
}
