package ru.omsu.collapsedlogicextension.init;

import ru.omsu.collapsedlogicextension.init.forgeregistrators.ForgeTileEntityRegistrator;

public class TileEntityRegistratorCreator extends AbstractRegistratorCreator {

    @Override
    public Registrator getRegistrator() {
        return ForgeTileEntityRegistrator.getInstance();
    }
}
