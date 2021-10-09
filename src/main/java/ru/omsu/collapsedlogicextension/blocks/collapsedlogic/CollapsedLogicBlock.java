package ru.omsu.collapsedlogicextension.blocks.collapsedlogic;

import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.network.NetworkHooks;
import ru.omsu.collapsedlogicextension.blocks.CLEBlock;
import ru.omsu.collapsedlogicextension.tileentity.LogicBlockTileEntity;
import ru.omsu.collapsedlogicextension.tileentity.TileEntityRegistrator;

import javax.annotation.Nullable;

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
        if(!worldIn.isRemote){
            TileEntity te = worldIn.getTileEntity(pos);
            System.err.println(te.getType().getRegistryName().getPath());
            if(te instanceof LogicBlockTileEntity){
                NetworkHooks.openGui((ServerPlayerEntity) player, (LogicBlockTileEntity)te, pos);
                return ActionResultType.SUCCESS;
            }
        }
        return ActionResultType.PASS;
    }

    @Override
    public boolean hasTileEntity() {
        return true;
    }

    @Override
    public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        super.onReplaced(state, worldIn, pos, newState, isMoving);
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return TileEntityRegistrator.COLLAPSED_LOGIC_BLOCK_TILE_ENTITY.get().create();
    }
}
