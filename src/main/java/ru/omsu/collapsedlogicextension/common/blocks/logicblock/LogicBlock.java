package ru.omsu.collapsedlogicextension.common.blocks.logicblock;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import ru.omsu.collapsedlogicextension.util.adapter.BlockAdapter;
import ru.omsu.collapsedlogicextension.util.api.ModBlock;

public class LogicBlock extends ModBlock<LogicBlock> {

    public LogicBlock(final BlockAdapter<LogicBlock> blockAdapter) {
        super(blockAdapter);
    }

    @Override
    public void onBlockActive(final World worldIn, final BlockPos pos, final PlayerEntity player) {
        sendOpenGuiIfRemote(worldIn, pos, player);
    }

    @Override
    public void onBlockPlace(final World worldIn, final BlockPos pos, final ItemStack stack) {
        setCustomTileEntityName(worldIn, pos, stack);
    }

    @Override
    public void onBlockReplace(
            final World worldIn,
            final BlockPos pos,
            final BlockState state,
            final BlockState newState) {
        getTileEntityAdapterForThis(worldIn, pos).onBlockReplace(worldIn);
    }
}
