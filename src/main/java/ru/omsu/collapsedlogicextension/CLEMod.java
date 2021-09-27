package ru.omsu.collapsedlogicextension;

import net.minecraftforge.fml.common.Mod;
import ru.omsu.collapsedlogicextension.blocks.BlockRegistrator;

@Mod(CLEMod.MOD_ID)
public class CLEMod {

    public static final String MOD_ID = "collapsedlogicextension";

    public CLEMod() {
        BlockRegistrator.register();
    }
}
