package ru.omsu.collapsedlogicextension.init;

import ru.omsu.collapsedlogicextension.init.forgeregistrators.ForgeBlockRegistrator;

public class BlockRegistratorCreator extends AbstractRegistratorCreator {

    @Override
    public Registrator getRegistrator() {
        return ForgeBlockRegistrator.getInstance();
    }
}
