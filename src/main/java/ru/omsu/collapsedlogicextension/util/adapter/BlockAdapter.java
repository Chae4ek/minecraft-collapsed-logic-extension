package ru.omsu.collapsedlogicextension.util.adapter;

import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ToolType;
import ru.omsu.collapsedlogicextension.init.ModObjectEnum.ModObject;
import ru.omsu.collapsedlogicextension.init.Registrator;
import ru.omsu.collapsedlogicextension.util.api.ModBlock;

import java.util.Random;

/** Перехватывает методы физического блока */
public class BlockAdapter<E extends ModBlock<E>> extends Block {

    private static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    public static final IntegerProperty POWER = BlockStateProperties.POWER_0_15;
    //,
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;

    private final E block;
    private final ModObject<?, E, ?, ?, ?> modObject;

    public BlockAdapter(final ModObject<?, E, ?, ?, ?> modObject) {
        // TODO: добавить настройки в параметры конструктора?
        super(Properties.create(Material.ROCK).harvestTool(ToolType.PICKAXE));
        setDefaultState(getStateContainer().getBaseState().with(FACING, Direction.NORTH).with(POWER, 0).with(POWERED, true));
        this.modObject = modObject;
        block = modObject.blockFactory.create(this);
    }

    @Override
    public final boolean hasTileEntity(final BlockState state) {
        return modObject.tileEntityFactory != null;
    }

    @Override
    public final TileEntity createTileEntity(final BlockState state, final IBlockReader world) {
        if (modObject.tileEntityFactory != null) {
            return Registrator.getTileEntityType(modObject.thisEnum).create();
        }
        return null;
    }

    @Override
    public final BlockState mirror(final BlockState state, final Mirror mirrorIn) {
        return state.rotate(mirrorIn.toRotation(state.get(FACING)));
    }

    @Override
    public BlockState rotate(final BlockState state, final Rotation rot) {
        return state.with(FACING, rot.rotate(state.get(FACING)));
    }

    @Override
    public BlockState getStateForPlacement(final BlockItemUseContext context) {
        return getDefaultState().with(FACING, context.getPlacementHorizontalFacing().getOpposite());
    }

    @Override
    protected void fillStateContainer(final Builder<Block, BlockState> builder) {
        super.fillStateContainer(builder);
        builder.add(FACING, POWER, POWERED);
    }

    @Override
    public boolean hasComparatorInputOverride(final BlockState state) {
        return block.isAffectRedstone();
    }

    @Override
    public int getComparatorInputOverride(
            final BlockState blockState, final World worldIn, final BlockPos pos) {
        return block.getRedstoneCharge();
    }

    @Override
    public ActionResultType onBlockActivated(
            final BlockState state,
            final World worldIn,
            final BlockPos pos,
            final PlayerEntity player,
            final Hand handIn,
            final BlockRayTraceResult hit) {
        block.onBlockActive(worldIn, pos, player);
        return ActionResultType.SUCCESS;
    }

    @Override
    public void onBlockPlacedBy(
            final World worldIn,
            final BlockPos pos,
            final BlockState state,
            @Nullable final LivingEntity placer,
            final ItemStack stack) {
        block.onBlockPlace(worldIn, pos, stack);
    }

    @Override
    public void onReplaced(
            final BlockState state,
            final World worldIn,
            final BlockPos pos,
            final BlockState newState,
            final boolean isMoving) {
        if (state.hasTileEntity()
                && (state.getBlock() != newState.getBlock() || !newState.hasTileEntity())) {
            worldIn.removeTileEntity(pos);
        } else {
            block.onBlockReplace(worldIn, pos, state, newState);
        }
    }

    @Override
    public boolean canConnectRedstone(
            final BlockState state,
            final IBlockReader world,
            final BlockPos pos,
            @Nullable final Direction side) {
        return block.canConnectRedstone(world, pos, state, side);
    }

    @Override
    public boolean canProvidePower(BlockState state) {
        return block.canProvidePower(state);
    }

    @Override
    public int getWeakPower(BlockState blockState, IBlockReader blockAccess, BlockPos pos, Direction side) {
        return block.getWeakPower(blockState, blockAccess, pos, side);
    }

    @Override
    public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
        block.tick(state, worldIn, pos);
    }
}
