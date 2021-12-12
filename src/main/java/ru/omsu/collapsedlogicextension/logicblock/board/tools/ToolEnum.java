package ru.omsu.collapsedlogicextension.logicblock.board.tools;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import ru.omsu.collapsedlogicextension.logicblock.board.cellstates.EmptyCell;
import ru.omsu.collapsedlogicextension.logicblock.board.cellstates.Wire;
import ru.omsu.collapsedlogicextension.logicblock.util.CombinedTextureRegions;

public enum ToolEnum {
    ERASER(new Brush(EmptyCell::new, new CombinedTextureRegions(0, 0)), "eraser"),
    /* TODO
    OPERATOR_AND(new Brush(OperatorAnd::new, new CombinedTextureRegions(19, 0)), "operator_and"),
    OPERATOR_OR(new Brush(OperatorOr::new, new CombinedTextureRegions(38, 0)), "operator_or"),
    OPERATOR_XOR(new Brush(OperatorXor::new, new CombinedTextureRegions(57, 0)), "operator_xor"),
    OPERATOR_NOT(new Brush(OperatorNot::new, new CombinedTextureRegions(76, 0)), "operator_not"),

     */
    WIRE(new Brush(Wire::new, new CombinedTextureRegions(95, 0)), "wire"),
    ROTATOR(new Rotator(new CombinedTextureRegions(114, 0)), "rotator");

    public final Tool tool;
    public final ITextComponent name;

    ToolEnum(final Tool tool, final String translationKey) {
        this.tool = tool;
        name =
                new TranslationTextComponent(
                        "block.collapsedlogicextension.logic_block." + translationKey);
    }
}
