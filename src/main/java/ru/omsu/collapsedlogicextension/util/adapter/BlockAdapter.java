package ru.omsu.collapsedlogicextension.util.adapter;

import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.client.world.ClientWorld;
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
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ToolType;
import ru.omsu.collapsedlogicextension.init.ModObjectEnum;
import ru.omsu.collapsedlogicextension.init.Registrator;
import ru.omsu.collapsedlogicextension.util.api.ModBlock;


/** Перехватывает методы физического блока */
public class BlockAdapter<E extends ModBlock<E>> extends Block {

    private static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    public static final IntegerProperty POWER = BlockStateProperties.POWER_0_15;

    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;

    private final E block;
    private final ModObjectEnum.ModObject<E, ?, ?, ?> modObject;

    private TileEntity te;

    public BlockAdapter(final ModObjectEnum.ModObject<E, ?, ?, ?> modObject) {
        // TODO: добавить настройки в параметры конструктора?
        super(Properties.create(Material.ROCK).harvestTool(ToolType.PICKAXE).hardnessAndResistance(3));
        setDefaultState(getStateContainer().getBaseState().with(FACING, Direction.NORTH).with(POWER, 0).with(POWERED, true));
        this.modObject = modObject;
        block = modObject.blockFactory.create(this);
    }

    @Override
    public final boolean hasTileEntity(final BlockState state) {
        return modObject.tileEntityFactory != null;
    }

    //TODO: мне кажется это костыль))) можн адаптер написать для IBlockReader
    @Override
    public final TileEntity createTileEntity(final BlockState state, final IBlockReader world) {
        if (modObject.tileEntityFactory != null && world instanceof ServerWorld) {
            te = Registrator.getTileEntityType(modObject.thisEnum).create();
            return te;
        }
        else if(world instanceof ClientWorld){
            return te;
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
    public void updateNeighbors(final BlockState stateIn, final IWorld worldIn, final BlockPos pos, final int flags) {
        System.out.println("updateNeighbors");
    }

    @Override
    public void onNeighborChange(final BlockState state, final IWorldReader world, final BlockPos pos, final BlockPos neighbor) {
        block.onNeighborChange(state, world, pos, neighbor);
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
    public boolean canProvidePower(final BlockState state) {
        return block.canProvidePower(state);
    }

    @Override
    public int getWeakPower(
        final BlockState blockState, final IBlockReader blockAccess, final BlockPos pos, final Direction side) {
        return block.getWeakPower(blockState, blockAccess, pos, side);
    }
}
