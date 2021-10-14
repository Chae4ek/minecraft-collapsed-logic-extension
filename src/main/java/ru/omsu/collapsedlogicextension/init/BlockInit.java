package ru.omsu.collapsedlogicextension.init;

import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;
import ru.omsu.collapsedlogicextension.CLEMod;
import ru.omsu.collapsedlogicextension.objects.blocks.LogicBlock;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockInit {

	public static final DeferredRegister<Block> BLOCKS = new DeferredRegister<>(ForgeRegistries.BLOCKS,
			CLEMod.MOD_ID);

	public static final RegistryObject<Block> LOGIC_BLOCK = BLOCKS.register("logic_block",
			() -> new LogicBlock(Block.Properties.create(Material.ROCK).harvestTool(ToolType.PICKAXE)));


}
