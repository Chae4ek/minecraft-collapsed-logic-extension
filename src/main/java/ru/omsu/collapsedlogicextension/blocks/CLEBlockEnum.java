package ru.omsu.collapsedlogicextension.blocks;

import java.util.function.Supplier;

import net.minecraft.block.RedstoneWireBlock;
import ru.omsu.collapsedlogicextension.blocks.collapsedlogic.*;

/** Хранит все блоки мода */
public enum CLEBlockEnum {
    COLLAPSED_LOGIC_BLOCK("collapsed_logic_block", CollapsedLogicBlock::new),
    //OPERATOR_AND("operator_and_block", OperatorAndBlock::new),
    //OPERATOR_OR("operator_or_block", OperatorOrBlock::new),
    //OPERATOR_XOR("operator_xor_block", OperatorXorBlock::new),
    //OPERATOR_NOT("operator_not_block", OperatorNotBlock::new),
    LOGIC_WIRE("logic_wire", LogicWireBlock::new);

    /** Имя должно совпадать со всеми файлами ресурсов */
    private final String registryName;
    /** При каждом вызове должен возвращать новый экземпляр блока */
    private final Supplier<CLEBlock> constructor;

    CLEBlockEnum(final String registryName, final Supplier<CLEBlock> constructor) {
        this.registryName = registryName;
        this.constructor = constructor;
    }

    /** @return Регистрируемое имя блока */
    public String getRegistryName() {
        return registryName;
    }

    /** @return Конструктор блока */
    public Supplier<CLEBlock> getConstructor() {
        return constructor;
    }
}
