package ru.omsu.collapsedlogicextension.tileentity;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.RayTraceResult;
import ru.omsu.collapsedlogicextension.blocks.CLEBlockEnum;
import ru.omsu.collapsedlogicextension.blocks.te.LogicBoard;

import java.util.function.Supplier;

public enum CLETileEntityEnum {
  COLLAPSED_LOGIC_BLOCK_TILE_ENTITY(
      "collapsed_logic_block",
      () ->
          TileEntityType.Builder.create(
                  LogicBoard::new, CLEBlockEnum.COLLAPSED_LOGIC_BLOCK.getConstructor().get()).build(null));

    /** Имя должно совпадать со всеми файлами ресурсов */
    private final String registryName;
    /** При каждом вызове должен возвращать новый экземпляр блока */
    private final Supplier<TileEntityType<? extends TileEntity>> constructor;

    CLETileEntityEnum(final String registryName, final Supplier<TileEntityType<? extends TileEntity>> constructor) {
        this.registryName = registryName;
        this.constructor = constructor;
    }

    /** @return Регистрируемое имя блока */
    public String getRegistryName() {
        return registryName;
    }

    /** @return Конструктор блока */
    public Supplier<TileEntityType<? extends TileEntity>> getConstructor() {
        return constructor;
    }
}
