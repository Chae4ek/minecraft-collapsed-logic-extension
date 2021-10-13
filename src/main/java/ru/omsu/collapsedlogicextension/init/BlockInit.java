package ru.omsu.collapsedlogicextension.init;

import ru.omsu.collapsedlogicextension.CLEMod;
import ru.omsu.collapsedlogicextension.objects.blocks.LogicBlock;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockInit {

	public static final DeferredRegister<Block> BLOCKS = new DeferredRegister<>(ForgeRegistries.BLOCKS,
			CLEMod.MOD_ID);

	public static final RegistryObject<Block> EXAMPLE_BLOCK = BLOCKS.register("example_block", () -> new Block(
			Block.Properties.create(Material.IRON).hardnessAndResistance(0.5f, 15.0f).sound(SoundType.SAND)));


	public static final RegistryObject<Block> EXAMPLE_FURNACE = BLOCKS.register("example_furnace",
			() -> new LogicBlock(Block.Properties.from(Blocks.FURNACE)));


}
