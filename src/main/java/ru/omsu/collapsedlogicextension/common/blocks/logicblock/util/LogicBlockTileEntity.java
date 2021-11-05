package ru.omsu.collapsedlogicextension.common.blocks.logicblock.util;

import net.minecraft.nbt.CompoundNBT;
import ru.omsu.collapsedlogicextension.util.adapter.TileEntityAdapter;
import ru.omsu.collapsedlogicextension.util.api.ModTileEntity;

public class LogicBlockTileEntity extends ModTileEntity<LogicBlockTileEntity> {

    public LogicBlockTileEntity(final TileEntityAdapter<LogicBlockTileEntity> tileEntityAdapter) {
        super(tileEntityAdapter);
    }

    @Override
    public void read(final CompoundNBT compound) {
        // TODO: прочитать матрицу доски и передать в сущность доски
    }

    @Override
    public CompoundNBT write(final CompoundNBT compound) {
        // TODO: записать матрицу доски либо интов, либо стрингов
        return compound;
    }
}
