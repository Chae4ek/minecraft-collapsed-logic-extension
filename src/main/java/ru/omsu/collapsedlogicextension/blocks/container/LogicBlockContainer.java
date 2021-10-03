package ru.omsu.collapsedlogicextension.blocks.container;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import ru.omsu.collapsedlogicextension.blocks.CLEBlockEnum;
import ru.omsu.collapsedlogicextension.blocks.te.LogicBoard;

import java.util.Objects;

public class LogicBlockContainer extends Container {

    private LogicBoard tileBoard;

    private IWorldPosCallable canInteractWithCallable;

    public LogicBlockContainer(final int id, final PlayerInventory inv, final LogicBoard tileBoard){
        super(CLEContainerType.COLLAPSED_LOGIC_BLOCK.getConstructor().get(), id);

        this.tileBoard = tileBoard;

        this.canInteractWithCallable = IWorldPosCallable.of(tileBoard.getWorld(), tileBoard.getPos());

        //пояс игрока
        for(int i = 0; i < 9; i++){
            this.addSlot(new Slot(inv, i, 48+18*i, 168));
        }

        //выходной слот
        this.addSlot(new SlotItemHandler((IItemHandler) this.getInventory(), 10, 15, 168));
    }

    public LogicBlockContainer(final int id, final PlayerInventory inv, final PacketBuffer buffer) {
        this(id, inv, getTileEntity(inv, buffer));
    }

    private static LogicBoard getTileEntity(final PlayerInventory inv, final PacketBuffer buffer){
        Objects.requireNonNull(inv);
        Objects.requireNonNull(buffer);
        final TileEntity tileAtPos = inv.player.world.getTileEntity(buffer.readBlockPos());
        if(tileAtPos instanceof LogicBoard){
            return (LogicBoard) tileAtPos;
        }
        throw new IllegalStateException("Tile entity invalid: " + tileAtPos);
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return Container.isWithinUsableDistance(canInteractWithCallable, playerIn, CLEBlockEnum.COLLAPSED_LOGIC_BLOCK.getConstructor().get());
    }

}
