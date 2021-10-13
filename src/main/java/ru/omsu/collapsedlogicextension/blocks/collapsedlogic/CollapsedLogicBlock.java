package ru.omsu.collapsedlogicextension.blocks.collapsedlogic;

import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
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
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

import net.minecraftforge.fml.network.NetworkHooks;
import ru.omsu.collapsedlogicextension.blocks.CLEBlock;
import ru.omsu.collapsedlogicextension.registry.TileEntityRegistrator;
import ru.omsu.collapsedlogicextension.tileentity.LogicBlockTileEntity;
import ru.omsu.collapsedlogicextension.util.ExampleItemHandler;

/** Блок для рисования редстоун схем */
public class CollapsedLogicBlock extends CLEBlock {

    public CollapsedLogicBlock() {
        super(Properties.create(Material.ROCK).harvestTool(ToolType.PICKAXE).sound(SoundType.METAL));
    }

    @Override
    public void onBlockHarvested(
            final World worldIn,
            final BlockPos pos,
            final BlockState state,
            final PlayerEntity player) {
        // TODO: тут должно чето выпадать при разрушении блока
        super.onBlockHarvested(worldIn, pos, state, player);
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
        if (stack.hasDisplayName()) {
            TileEntity tile = worldIn.getTileEntity(pos);
            if (tile instanceof LogicBlockTileEntity) {
                ((LogicBlockTileEntity) tile).setCustomName(stack.getDisplayName());
            }
        }
    }
    @Override
    public boolean hasComparatorInputOverride(BlockState state) {
        return true;
    }

    @Override
    public int getComparatorInputOverride(BlockState blockState, World worldIn, BlockPos pos) {
        return Container.calcRedstone(worldIn.getTileEntity(pos));
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player,
                                             Hand handIn, BlockRayTraceResult hit) {
        if (!worldIn.isRemote) {
            TileEntity tile = worldIn.getTileEntity(pos);
            if (tile instanceof LogicBlockTileEntity) {
                NetworkHooks.openGui((ServerPlayerEntity) player, (INamedContainerProvider) tile, pos);
                return ActionResultType.SUCCESS;
            }
        }
        return ActionResultType.PASS;
    }
    @Override
    public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        TileEntity tile = worldIn.getTileEntity(pos);
        if (tile instanceof LogicBlockTileEntity && state.getBlock() != newState.getBlock()) {
            LogicBlockTileEntity logBlock = (LogicBlockTileEntity) tile;
            ((ExampleItemHandler) logBlock.getInventory()).toNonNullList().forEach(item -> {
                ItemEntity itemEntity = new ItemEntity(worldIn, pos.getX(), pos.getY(), pos.getZ(), item);
                worldIn.addEntity(itemEntity);
            });
        }

        if (state.hasTileEntity() && state.getBlock() != newState.getBlock()) {
            worldIn.removeTileEntity(pos);
        }
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return TileEntityRegistrator.COLLAPSED_LOGIC_BLOCK.get().create();
    }
}
