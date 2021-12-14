package ru.omsu.collapsedlogicextension.logicblock;

import java.util.function.Supplier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.network.PacketBuffer;
import ru.omsu.collapsedlogicextension.logicblock.board.Board;

public class LogicBlockContainer extends Container {

    private final Supplier<Board> boardGetter;

    public LogicBlockContainer(
            final ContainerType<?> containerType,
            final int windowId,
            final Supplier<Board> boardGetter) {
        super(containerType, windowId);
        this.boardGetter = boardGetter;
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
                        ::getBoard);
    }

    public Supplier<Board> getBoardGetter() {
        return boardGetter;
    }

    @Override
    public boolean canInteractWith(final PlayerEntity playerIn) {
        return true;
    }
}
