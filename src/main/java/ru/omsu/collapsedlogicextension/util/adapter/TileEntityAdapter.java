package ru.omsu.collapsedlogicextension.util.adapter;

import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import ru.omsu.collapsedlogicextension.init.ModInit;
import ru.omsu.collapsedlogicextension.init.ModObjectEnum.ModObject;
import ru.omsu.collapsedlogicextension.init.Registrator;
import ru.omsu.collapsedlogicextension.util.api.ModTileEntity;
import ru.omsu.collapsedlogicextension.util.api.Unsafe;

// На самом деле этот класс обязует иметь у tile entity контейнер (причем с 1 слотом), поэтому
// некоторые методы могут быть небезопасными. Это должно быть исправлено в будущем, наследовав от
// этого класса специальный класс tile entity именно с контейнером

/** Перехватывает методы физического tile entity */
public class TileEntityAdapter<E extends ModTileEntity<E>> extends TileEntity
        implements INamedContainerProvider {

    public final E tileEntity;

    final ItemStackHandler slots;
    private final ModObject<?, ?, E, ?, ?> modObject;
    private ITextComponent customName;

    public TileEntityAdapter(final ModObject<?, ?, E, ?, ?> modObject) {
        super(Registrator.getTileEntityType(modObject.thisEnum));
        this.modObject = modObject;
        slots = new ItemStackHandler(1);
        tileEntity = modObject.tileEntityFactory.create(this);
    }

    /** Если у этого tile entity нет контейнера, игра крашнется */
    @Unsafe
    public void onBlockReplace(final World worldIn) {
        worldIn.addEntity(
                new ItemEntity(
                        worldIn, pos.getX(), pos.getY(), pos.getZ(), slots.getStackInSlot(0)));
    }

    @Override
    public Container createMenu(
            final int windowID, final PlayerInventory playerInv, final PlayerEntity playerIn) {
        return new ContainerAdapter<>(modObject, windowID, this);
    }

    @Override
    public ITextComponent getDisplayName() {
        return customName != null
                ? customName
                : new TranslationTextComponent("container." + ModInit.MOD_ID + ".logic_block");
    }

    public void setCustomName(final ITextComponent name) {
        customName = name;
    }

    @Override
    public void read(final CompoundNBT compound) {
        super.read(compound);
        if (compound.contains("CustomName", Constants.NBT.TAG_STRING)) {
            customName = ITextComponent.Serializer.fromJson(compound.getString("CustomName"));
        }
        slots.deserializeNBT(compound);
        tileEntity.read(compound);
    }

    @Override
    public CompoundNBT write(final CompoundNBT compound) {
        super.write(compound);
        if (customName != null)
            compound.putString("CustomName", ITextComponent.Serializer.toJson(customName));
        compound.merge(slots.serializeNBT());
        return tileEntity.write(compound);
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
        final CompoundNBT tag = new CompoundNBT();
        write(tag);
        return tag;
    }

    @Override
    public void handleUpdateTag(final CompoundNBT tag) {
        read(tag);
    }

    @Override
    public <T> LazyOptional<T> getCapability(final Capability<T> cap, final Direction side) {
        return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.orEmpty(
                cap, LazyOptional.of(() -> slots));
    }
}
