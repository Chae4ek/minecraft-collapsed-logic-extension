package ru.omsu.collapsedlogicextension.common.blocks.logicblock;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.Constants;
import ru.omsu.collapsedlogicextension.common.blocks.logicblock.board.Board;
import ru.omsu.collapsedlogicextension.util.adapter.TileEntityAdapter;
import ru.omsu.collapsedlogicextension.util.api.ModTileEntity;

public class LogicBlockTileEntity extends ModTileEntity<LogicBlockTileEntity> {

    public final Board board;

    public LogicBlockTileEntity(final TileEntityAdapter<LogicBlockTileEntity> tileEntityAdapter) {
        super(tileEntityAdapter);
        board = new Board();
    }

    @Override
    public void read(final CompoundNBT compound) {
        if (compound.contains("Board", Constants.NBT.TAG_STRING)) {
            board.deserialize(compound.getString("Board"));
        }
    }

    @Override
    public CompoundNBT write(final CompoundNBT compound) {
        compound.putString("Board", board.serialize());
        return compound;
    }

    @Override
    public void update() {
        board.update();
    }
}
