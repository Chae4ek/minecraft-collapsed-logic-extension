package ru.omsu.collapsedlogicextension.common.blocks.logicblock.util;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.Constants;
import ru.omsu.collapsedlogicextension.common.blocks.logicblock.util.board.LogicBoard;
import ru.omsu.collapsedlogicextension.util.adapter.TileEntityAdapter;
import ru.omsu.collapsedlogicextension.util.api.ModTileEntity;

public class LogicBlockTileEntity extends ModTileEntity<LogicBlockTileEntity> {

    public final LogicBoard logicBoard;

    public LogicBlockTileEntity(final TileEntityAdapter<LogicBlockTileEntity> tileEntityAdapter) {
        super(tileEntityAdapter);
        logicBoard = new LogicBoard();
    }

    @Override
    public void read(final CompoundNBT compound) {
        if (compound.contains("LogicBoard", Constants.NBT.TAG_STRING)) {
            logicBoard.deserialize(compound.getString("LogicBoard"));
        }
    }

    @Override
    public CompoundNBT write(final CompoundNBT compound) {
        compound.putString("LogicBoard", logicBoard.serialize());
        return compound;
    }
}
