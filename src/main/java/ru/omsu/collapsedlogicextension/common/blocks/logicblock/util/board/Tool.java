package ru.omsu.collapsedlogicextension.common.blocks.logicblock.util.board;

import java.util.function.Supplier;
import ru.omsu.collapsedlogicextension.common.blocks.logicblock.util.board.EmptyCell;
import ru.omsu.collapsedlogicextension.common.blocks.logicblock.util.board.OperatorAnd;
import ru.omsu.collapsedlogicextension.common.blocks.logicblock.util.board.OperatorNot;
import ru.omsu.collapsedlogicextension.common.blocks.logicblock.util.board.OperatorOr;
import ru.omsu.collapsedlogicextension.common.blocks.logicblock.util.board.OperatorXor;
import ru.omsu.collapsedlogicextension.common.blocks.logicblock.util.board.Rotation;
import ru.omsu.collapsedlogicextension.common.blocks.logicblock.util.board.State;
import ru.omsu.collapsedlogicextension.common.blocks.logicblock.util.board.Wire;

public enum Tool {
    ERASER(EmptyCell::new),
    OPERATOR_AND(OperatorAnd::new),
    OPERATOR_OR(OperatorOr::new),
    OPERATOR_XOR(OperatorXor::new),
    OPERATOR_NOT(OperatorNot::new),
    LOGIC_WIRE(Wire::new),
    ROTATION(Rotation::new);

    private final Supplier<? extends State> constructor;

    Tool(final Supplier<? extends State> constructor) {
        this.constructor = constructor;
    }

    public Supplier<? extends State> getConstructor() {
        return constructor;
    }
}
