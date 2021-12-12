package ru.omsu.collapsedlogicextension.logicblock;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.network.PacketBuffer;
import ru.omsu.collapsedlogicextension.logicblock.board.Board;

public class LogicBlockContainer extends Container {

    private final Board board;

    public LogicBlockContainer(
            final ContainerType<?> containerType, final int windowId, final Board board) {
        super(containerType, windowId);
        this.board = board;
    }

    public LogicBlockContainer(
            final ContainerType<?> containerType,
            final int windowId,
            final PlayerInventory inventory,
            final PacketBuffer data) {
        this(
                containerType,
                windowId,
                ((LogicBlockTileEntity) inventory.player.world.getTileEntity(data.readBlockPos()))
                        .getBoard());
    }

    public Board getBoard() {
        return board;
    }

    @Override
    public boolean canInteractWith(final PlayerEntity playerIn) {
        return true;
    }
}
