package ru.omsu.collapsedlogicextension.init;

import net.minecraftforge.fml.common.Mod;

/** Основной класс для инициализации мода */
@Mod(ModInit.MOD_ID)
public class ModInit {

    public static final String MOD_ID = "collapsedlogicextension";

    public ModInit() {
        final Registrator blockRegistrator = new BlockRegistratorCreator().getRegistrator();
        final Registrator tileEntityRegistrator =
                new TileEntityRegistratorCreator().getRegistrator();
        final Registrator containerRegistrator = new ContainerRegistratorCreator().getRegistrator();
        blockRegistrator.registerAll();
        tileEntityRegistrator.registerAll();
        containerRegistrator.registerAll();
    }
}
