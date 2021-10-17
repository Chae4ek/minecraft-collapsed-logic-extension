package ru.omsu.collapsedlogicextension.blocks.operators;

import net.minecraft.block.BlockState;
import net.minecraft.block.RedstoneDiodeBlock;

public class AbstractOperator extends RedstoneDiodeBlock {

    public AbstractOperator(Properties properties) {
        super(properties);
    }

    @Override
    protected int getDelay(BlockState state) {
        return 0;
    }


}
