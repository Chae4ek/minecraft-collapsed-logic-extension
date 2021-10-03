package ru.omsu.collapsedlogicextension.blocks.te;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.items.ItemStackHandler;
import ru.omsu.collapsedlogicextension.tileentity.CLETileEntity;
import ru.omsu.collapsedlogicextension.tileentity.CLETileEntityEnum;

public class LogicBoard extends CLETileEntity {

    private Cell[][] cells;

    ItemStackHandler inventory = new ItemStackHandler(9);

    protected LogicBoard(TileEntityType<?> typeIn) {
        super(typeIn);
        this.cells = new Cell[9][5];
    }
    public LogicBoard() {
        this(CLETileEntityEnum.COLLAPSED_LOGIC_BLOCK_TILE_ENTITY.getConstructor().get());
    }

    @Override
    public void read(CompoundNBT compound) {
        super.read(compound);
        inventory.deserializeNBT(compound.getCompound("inventory"));
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        return super.write(compound);
    }

    public boolean set(int x, int y, Cell cell){
        cells[x][y] = cell;
        return true;
    }
    public Cell get(int x, int y){
        return cells[x][y];
    }
    public void clear(){this.cells = null;}

    public boolean remove(int x, int y){
        cells[x][y] = null;
        return true;
    }
}
