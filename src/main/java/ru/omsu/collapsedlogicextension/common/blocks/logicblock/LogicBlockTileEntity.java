package ru.omsu.collapsedlogicextension.common.blocks.logicblock;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.Constants;
import ru.omsu.collapsedlogicextension.common.blocks.logicblock.board.Board;
import ru.omsu.collapsedlogicextension.util.api.ModTileEntity;

public class LogicBlockTileEntity extends ModTileEntity<LogicBlockTileEntity> {

    private final Board board;

    public LogicBlockTileEntity() {
        board = new Board();
    }

    public Board getBoard() {
        return board;
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
