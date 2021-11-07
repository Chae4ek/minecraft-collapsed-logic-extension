package ru.omsu.collapsedlogicextension.common.blocks.logicblock.board.tools;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import ru.omsu.collapsedlogicextension.common.blocks.logicblock.board.cellstates.EmptyCell;
import ru.omsu.collapsedlogicextension.common.blocks.logicblock.board.cellstates.OperatorAnd;
import ru.omsu.collapsedlogicextension.common.blocks.logicblock.board.cellstates.OperatorNot;
import ru.omsu.collapsedlogicextension.common.blocks.logicblock.board.cellstates.OperatorOr;
import ru.omsu.collapsedlogicextension.common.blocks.logicblock.board.cellstates.OperatorXor;
import ru.omsu.collapsedlogicextension.common.blocks.logicblock.board.cellstates.Wire;
import ru.omsu.collapsedlogicextension.common.blocks.logicblock.util.TextureRegion;

public enum ToolEnum {
    ERASER(new Brush(EmptyCell::new, new TextureRegion(0, 0)), "eraser"),
    OPERATOR_AND(new Brush(OperatorAnd::new, new TextureRegion(19, 0)), "operator_and"),
    OPERATOR_OR(new Brush(OperatorOr::new, new TextureRegion(38, 0)), "operator_or"),
    OPERATOR_XOR(new Brush(OperatorXor::new, new TextureRegion(57, 0)), "operator_xor"),
    OPERATOR_NOT(new Brush(OperatorNot::new, new TextureRegion(76, 0)), "operator_not"),
    WIRE(new Brush(Wire::new, new TextureRegion(95, 0)), "wire"),
    ROTATOR(new Rotator(new TextureRegion(114, 0)), "rotator");

    public final Tool tool;
    public final ITextComponent name;

    ToolEnum(final Tool tool, final String translationKey) {
        this.tool = tool;
        name =
                new TranslationTextComponent(
                        "block.collapsedlogicextension.logic_block." + translationKey);
    }
}
