package ru.omsu.collapsedlogicextension.common.blocks.logicblock;

import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.IntegerProperty;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import ru.omsu.collapsedlogicextension.common.blocks.logicblock.util.Direction3D;
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

    @Override
    public boolean canConnectRedstone(
            final IBlockReader world,
            final BlockPos pos,
            final BlockState state,
            @Nullable final Direction side) {
        return side != null
                && this.<LogicBlockTileEntity>getModTileEntityForThis(world, pos)
                        .board
                        .canConnectRedstone(Direction3D.convert(side));
    }

    @Override
    public int getWeakPower(BlockState blockState, IBlockReader blockAccess, BlockPos pos, Direction side) {
        return this.<LogicBlockTileEntity>getModTileEntityForThis(blockAccess, pos).board.getPowerOnSide(Direction3D.convert(side));
    }

    @Override
    public boolean isAffectRedstone() {
        return true;
    }

    @Override
    public int getRedstoneCharge() {
        return 15;
    }

    @Override
    public boolean canProvidePower(BlockState state) {
        return true;
    }
}
