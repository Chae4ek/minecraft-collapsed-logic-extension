package ru.omsu.collapsedlogicextension.blocks.collapsedlogic;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.shapes.VoxelShape;
import ru.omsu.collapsedlogicextension.blocks.CLEBlock;

public class LogicWireBlock extends CLEBlock {

    /**Зададим плоскую форму провода*/
    protected static final VoxelShape SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D);

    public LogicWireBlock() {
        /**Логический провод не может сдвигаться поршнем, с него ничего не выпадает, звук как у снега
         * нулевая прочность*/
        super(
            Properties.create(Material.MISCELLANEOUS)
                .doesNotBlockMovement()
                .noDrops()
                .sound(SoundType.SNOW).hardnessAndResistance(0));
    }

    public static VoxelShape getShape() {
        return SHAPE;
    }
}

