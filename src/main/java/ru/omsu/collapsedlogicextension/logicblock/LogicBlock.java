package ru.omsu.collapsedlogicextension.logicblock;

import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.network.NetworkHooks;

public class LogicBlock extends Block {

    private final Supplier<TileEntityType<?>> creator;
    private TileEntity tileEntity;

    public LogicBlock(final Supplier<TileEntityType<?>> creator) {
        super(
                Properties.create(Material.ROCK)
                        .harvestTool(ToolType.PICKAXE)
                        .hardnessAndResistance(3));
        this.creator = creator;
    }

    @Override
    public final boolean hasTileEntity(final BlockState state) {
        return true;
    }

    @Override
    public final TileEntity createTileEntity(final BlockState state, final IBlockReader world) {
        if (world instanceof ServerWorld) {
            return tileEntity = creator.get().create();
        } else if (world instanceof ClientWorld) {
            return tileEntity;
        }
        return null;
    }

    /** Вызывается при активации блока (нажатием ПКМ) */
    @Override
    public ActionResultType onBlockActivated(
            final BlockState state,
            final World worldIn,
            final BlockPos pos,
            final PlayerEntity player,
            final Hand handIn,
            final BlockRayTraceResult hit) {
        if (!worldIn.isRemote) {
            final LogicBlockTileEntity tile = (LogicBlockTileEntity) worldIn.getTileEntity(pos);
            NetworkHooks.openGui((ServerPlayerEntity) player, tile, pos);
        }
        return ActionResultType.SUCCESS;
    }

    /**
     * Вызывается при размещении блока в мире. Судя по исходникам вызывается после {@link
     * #onReplaced}, но это не точно и не всегда
     */
    @Override
    public void onBlockPlacedBy(
            final World worldIn,
            final BlockPos pos,
            final BlockState state,
            @Nullable final LivingEntity placer,
            final ItemStack stack) {
        if (stack.hasDisplayName()) {
            final LogicBlockTileEntity tile = (LogicBlockTileEntity) worldIn.getTileEntity(pos);
            tile.setCustomName(stack.getDisplayName());
        }
    }

    /**
     * Вызывается при замене блока в мире на другой блок или другое состояние текущего блока.
     * Замечание: отсутствие блока (блок воздуха) - тоже блок, т.е. при удалении блока этот метод
     * также вызывается. Должен ЗАМЕНЯТЬ все свои ресурсы БЕЗ УДАЛЕНИЯ. Судя по исходникам.
     * вызывается перед {@link #onBlockPlacedBy}, но это не точно и не всегда
     */
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
        }
    }
}
