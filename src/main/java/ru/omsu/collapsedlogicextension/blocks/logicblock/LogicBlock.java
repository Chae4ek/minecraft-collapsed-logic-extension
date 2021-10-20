package ru.omsu.collapsedlogicextension.blocks.logicblock;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.network.NetworkHooks;
import ru.omsu.collapsedlogicextension.ModInit;
import ru.omsu.collapsedlogicextension.ModInit.ModObjectEnum;
import ru.omsu.collapsedlogicextension.blocks.logicblock.util.ExampleItemHandler;
import ru.omsu.collapsedlogicextension.blocks.logicblock.util.LogicBlockTileEntity;

import javax.annotation.Nullable;

public class LogicBlock extends Block {

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    private final int[][] angles = new int[4][2];

    public LogicBlock() {
        super(Properties.create(Material.ROCK).harvestTool(ToolType.PICKAXE));

        angles[0] = new int[]{1, 1};
        angles[1] = new int[]{0, 9};
        angles[2] = new int[]{13, 0};
        angles[3] = new int[]{0, -9};

        this.setDefaultState(this.getStateContainer().getBaseState().with(FACING, Direction.NORTH));
    }

    @Override
    public boolean hasTileEntity(final BlockState state) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(final BlockState state, final IBlockReader world) {
        return ModInit.getTileEntityType(ModObjectEnum.LOGIC_BLOCK).create();
    }

    @Override
    public BlockState mirror(final BlockState state, final Mirror mirrorIn) {
        return state.rotate(mirrorIn.toRotation(state.get(FACING)));
    }

    @Override
    public BlockState rotate(final BlockState state, final Rotation rot) {
        return state.with(FACING, rot.rotate(state.get(FACING)));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing().getOpposite());
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        super.fillStateContainer(builder);
        builder.add(FACING);
    }

    @Override
    public void onBlockPlacedBy(
            final World worldIn,
            final BlockPos pos,
            final BlockState state,
            final LivingEntity placer,
            final ItemStack stack) {
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
        if (stack.hasDisplayName()) {
            final TileEntity tile = worldIn.getTileEntity(pos);
            if (tile instanceof LogicBlockTileEntity) {
                ((LogicBlockTileEntity) tile).setCustomName(stack.getDisplayName());
            }
        }
    }
    @Override
    public boolean hasComparatorInputOverride(final BlockState state) {
        return true;
    }

    @Override
    public int getComparatorInputOverride(
            final BlockState blockState, final World worldIn, final BlockPos pos) {
        return Container.calcRedstone(worldIn.getTileEntity(pos));
    }

    @Override
    public ActionResultType onBlockActivated(
            final BlockState state,
            final World worldIn,
            final BlockPos pos,
            final PlayerEntity player,
            final Hand handIn,
            final BlockRayTraceResult hit) {
        if (!worldIn.isRemote) {
            final TileEntity tile = worldIn.getTileEntity(pos);
            if (tile instanceof LogicBlockTileEntity) {
                NetworkHooks.openGui(
                        (ServerPlayerEntity) player, (INamedContainerProvider) tile, pos);
                return ActionResultType.SUCCESS;
            }
        }
        return ActionResultType.SUCCESS;
    }

    @Override
    public void onReplaced(
            final BlockState state,
            final World worldIn,
            final BlockPos pos,
            final BlockState newState,
            final boolean isMoving) {
        final TileEntity tile = worldIn.getTileEntity(pos);
        if (tile instanceof LogicBlockTileEntity && state.getBlock() != newState.getBlock()) {
            final LogicBlockTileEntity tileEntity = (LogicBlockTileEntity) tile;
            ((ExampleItemHandler) tileEntity.getInventory())
                    .toNonNullList()
                    .forEach(
                            item -> {
                                final ItemEntity itemEntity =
                                        new ItemEntity(
                                                worldIn, pos.getX(), pos.getY(), pos.getZ(), item);
                                worldIn.addEntity(itemEntity);
                            });
        }

        if (state.hasTileEntity() && state.getBlock() != newState.getBlock()) {
            worldIn.removeTileEntity(pos);
        }
    }
}
