package ru.omsu.collapsedlogicextension.logicblock;

import com.google.gson.Gson;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.util.Constants;
import ru.omsu.collapsedlogicextension.init.ModInit;
import ru.omsu.collapsedlogicextension.logicblock.board.Board;

public class LogicBlockTileEntity extends TileEntity
        implements INamedContainerProvider, ITickableTileEntity {

    private final ContainerType<?> containerType;
    private final Gson gson = new Gson();
    private Board board;
    private ITextComponent customName;

    public LogicBlockTileEntity(
            final TileEntityType<?> tileEntityType, final ContainerType<?> containerType) {
        super(tileEntityType);
        this.containerType = containerType;
        board = new Board();
    }

    public Board getBoard() {
        return board;
    }

    /** Читает данные из NBT и записывает их в этот tile entity */
    @Override
    public void read(final CompoundNBT compound) {
        if (compound.contains("CustomName", Constants.NBT.TAG_STRING)) {
            customName = ITextComponent.Serializer.fromJson(compound.getString("CustomName"));
        }
        if (compound.contains("Board", Constants.NBT.TAG_STRING)) {
            board = gson.fromJson(compound.getString("Board"), Board.class);
        }
    }

    /** Записывает данные в NBT из этого tile entity */
    @Override
    public CompoundNBT write(final CompoundNBT compound) {
        if (customName != null)
            compound.putString("CustomName", ITextComponent.Serializer.toJson(customName));
        compound.putString("Board", gson.toJson(board));
        return compound;
    }

    @Override
    public Container createMenu(
            final int windowID, final PlayerInventory playerInv, final PlayerEntity playerIn) {
        return new LogicBlockContainer(containerType, windowID, this::getBoard);
    }

    @Override
    public ITextComponent getDisplayName() {
        return customName != null
                ? customName
                : (customName =
                        new TranslationTextComponent(
                                "container." + ModInit.MOD_ID + ".logic_block"));
    }

    public void setCustomName(final ITextComponent name) {
        customName = name;
    }

    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        final CompoundNBT tag = new CompoundNBT();
        write(tag);
        return new SUpdateTileEntityPacket(pos, 0, tag);
    }

    @Override
    public void onDataPacket(final NetworkManager net, final SUpdateTileEntityPacket pkt) {
        read(pkt.getNbtCompound());
    }

    @Override
    public CompoundNBT getUpdateTag() {
        return write(new CompoundNBT());
    }

    /** Вызывается с каждым игровым тиком (примерно 20 раз в секунду) */
    @Override
    public void tick() {
        board.update();
    }
}
