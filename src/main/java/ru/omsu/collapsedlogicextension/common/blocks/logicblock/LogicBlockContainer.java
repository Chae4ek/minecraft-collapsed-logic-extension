package ru.omsu.collapsedlogicextension.common.blocks.logicblock;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.IWorldPosCallable;
import ru.omsu.collapsedlogicextension.init.ModObjectEnum;
import ru.omsu.collapsedlogicextension.util.adapter.ContainerAdapter;
import ru.omsu.collapsedlogicextension.util.adapter.TileEntityAdapter;
import ru.omsu.collapsedlogicextension.util.api.ModContainer;

/** Хранит группы слотов под предметы */
public class LogicBlockContainer extends ModContainer<LogicBlockContainer> {

    private final IWorldPosCallable canInteractWithCallable;

    public LogicBlockContainer(
            final ContainerAdapter<LogicBlockContainer> containerAdapter,
            final TileEntityAdapter<?> tileEntityAdapter) {
        super(containerAdapter, tileEntityAdapter);

        canInteractWithCallable =
                IWorldPosCallable.of(tileEntityAdapter.getWorld(), tileEntityAdapter.getPos());

        addNextSlot(getItemStackHandler(), 15, 168);
    }

    @Override
    public boolean canInteractWith(final PlayerEntity playerIn) {
        return isWithinUsableDistance(canInteractWithCallable, playerIn, ModObjectEnum.LOGIC_BLOCK);
    }
}
