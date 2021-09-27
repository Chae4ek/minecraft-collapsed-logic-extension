package ru.omsu.collapsedlogicextension.blocks.collapsedlogic;

import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import ru.omsu.collapsedlogicextension.blocks.CLEBlock;

/** Блок для рисования редстоун схем */
public class CollapsedLogicBlock extends CLEBlock {

    public CollapsedLogicBlock() {
        super(Properties.create(Material.ROCK).harvestTool(ToolType.PICKAXE));
    }

    @Override
    public void onBlockHarvested(
            final World worldIn,
            final BlockPos pos,
            final BlockState state,
            final PlayerEntity player) {
        // TODO: тут должно чето выпадать при разрушении блока
        System.err.println("Block Destroyed");
        super.onBlockHarvested(worldIn, pos, state, player);
    }

    @Override
    public ActionResultType onBlockActivated(
            final BlockState state,
            final World worldIn,
            final BlockPos pos,
            final PlayerEntity player,
            final Hand handIn,
            final BlockRayTraceResult hit) {
        // TODO: тут должно открываться GUI
        System.err.println("Block Activated");
        return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
    }
}
