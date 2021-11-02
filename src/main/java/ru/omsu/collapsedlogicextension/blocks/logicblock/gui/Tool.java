package ru.omsu.collapsedlogicextension.blocks.logicblock.gui;

import ru.omsu.collapsedlogicextension.blocks.logicblock.util.board.*;

import java.util.function.Supplier;

public enum Tool {

    ERASER(EmptyCell::new),
    OPERATOR_AND(OperatorAnd::new),
    OPERATOR_OR(OperatorOr::new),
    OPERATOR_XOR(OperatorXor::new),
    OPERATOR_NOT(OperatorNot::new),
    LOGIC_WIRE(Wire::new),
    ROTATION(Rotation::new);

    private Supplier<? extends State> constructor;

    Tool(Supplier<? extends State> constructor){
        this.constructor = constructor;
    }

    public Supplier<? extends State> getConstructor() {
        return constructor;
    }
}
