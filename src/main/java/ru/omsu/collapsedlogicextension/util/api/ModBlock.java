package ru.omsu.collapsedlogicextension.util.api;

import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.block.RedstoneBlock;
import net.minecraft.block.RepeaterBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.IntegerProperty;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkHooks;
import ru.omsu.collapsedlogicextension.common.blocks.logicblock.LogicBlockTileEntity;
import ru.omsu.collapsedlogicextension.common.blocks.logicblock.util.Direction3D;
import ru.omsu.collapsedlogicextension.util.adapter.BlockAdapter;
import ru.omsu.collapsedlogicextension.util.adapter.TileEntityAdapter;

import static ru.omsu.collapsedlogicextension.util.adapter.BlockAdapter.POWER;

/** Основной класс для всех блоков мода */
public abstract class ModBlock<E extends ModBlock<E>> {

    private final BlockAdapter<E> blockAdapter;

    public ModBlock(final BlockAdapter<E> blockAdapter) {
        this.blockAdapter = blockAdapter;
    }

    /** Если блок не имеет tile entity, игра крашнется */
    @Unsafe
    public void sendOpenGuiIfRemote(
            final World worldIn, final BlockPos pos, final PlayerEntity player) {
        if (!worldIn.isRemote) {
            final TileEntityAdapter<?> tile = (TileEntityAdapter<?>) worldIn.getTileEntity(pos);
            NetworkHooks.openGui((ServerPlayerEntity) player, tile, pos);
        }
    }

    /** Если блок не имеет tile entity, игра крашнется */
    @Unsafe
    public void setCustomTileEntityName(
            final World worldIn, final BlockPos pos, final ItemStack stack) {
        if (stack.hasDisplayName()) {
            final TileEntityAdapter<?> tile = (TileEntityAdapter<?>) worldIn.getTileEntity(pos);
            tile.setCustomName(stack.getDisplayName());
        }
    }

    /**
     * Отвечает за взаимодействие с редстоуном. Может влиять на компаратор или просто проводить
     * сигнал. Не забудьте также переопределить метод {@link #getRedstoneCharge}
     *
     * @return true, если блок влияет на заряд редстоун сигнала
     */
    public boolean isAffectRedstone() {
        return false;
    }

    /**
     * Возвращает заряд редстоун сигнала. По стандарту это значение лежит в отрезке [0;15]. Если
     * блок влияет на редстоун сигнал (хранит предметы для компаратора или проводит сигнал), то
     * нужно переопределить метод {@link #isAffectRedstone}
     *
     * @return заряд редстоун сигнала
     */
    public int getRedstoneCharge() {
        return 0;
    }

    /** Вызывается при активации блока (нажатием ПКМ) */
    public void onBlockActive(final World worldIn, final BlockPos pos, final PlayerEntity player) {}

    /**
     * Вызывается при размещении блока в мире. Судя по исходникам вызывается после {@link
     * #onBlockReplace}, но это не точно и не всегда
     */
    public void onBlockPlace(final World worldIn, final BlockPos pos, final ItemStack stack) {}

    /**
     * Вызывается при замене блока в мире на другой блок или другое состояние текущего блока.
     * Замечание: отсутствие блока (блок воздуха) - тоже блок, т.е. при удалении блока этот метод
     * также вызывается. Должен ЗАМЕНЯТЬ все свои ресурсы БЕЗ УДАЛЕНИЯ. Судя по исходникам.
     * вызывается перед {@link #onBlockPlace}, но это не точно и не всегда
     */
    public void onBlockReplace(
            final World worldIn,
            final BlockPos pos,
            final BlockState state,
            final BlockState newState) {}

    /** @return true, если блок может присоединиться к редстоуну */
    public boolean canConnectRedstone(
            final IBlockReader world,
            final BlockPos pos,
            final BlockState state,
            @Nullable final Direction side) {
        return side != null && state.canProvidePower();
    }

    /** @return tile entity этого объекта, если он есть, иначе крашнется игра */
    @Unsafe
    @SuppressWarnings("unchecked")
    public final <T extends ModTileEntity<T>> T getModTileEntityForThis(
            final IBlockReader world, final BlockPos pos) {
        return ((TileEntityAdapter<T>) world.getTileEntity(pos)).tileEntity;
    }

    public boolean canProvidePower(BlockState state){
        return true;
    }
    /**
     * @return физический (майнкрафтовский) tile entity этого объекта, если он есть, иначе крашнется
     *     игра
     */
    @Unsafe
    @SuppressWarnings("unchecked")
    public final <T extends ModTileEntity<T>> TileEntityAdapter<T> getTileEntityAdapterForThis(
            final World worldIn, final BlockPos pos) {
        return ((TileEntityAdapter<T>) worldIn.getTileEntity(pos));
    }

    public int getWeakPower(BlockState blockState, IBlockReader blockAccess, BlockPos pos, Direction side){
        return 0;
    }

    public void tick(BlockState state, ServerWorld worldIn, BlockPos pos) {
        for(Direction3D direction : Direction3D.values()){
            int power = this.<LogicBlockTileEntity>getModTileEntityForThis(worldIn, pos).board.getPowerOnSide(direction);
            worldIn.setBlockState(pos, state.with(POWER, power));
        }
    }

    public interface ModBlockFactory<E extends ModBlock<E>> {
        E create(BlockAdapter<E> adapter);
    }
}
