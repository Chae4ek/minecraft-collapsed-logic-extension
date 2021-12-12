package ru.omsu.collapsedlogicextension.init;

import ru.omsu.collapsedlogicextension.init.forgeregistrators.ForgeContainerRegistrator;

public class ContainerRegistratorCreator extends AbstractRegistratorCreator {

    @Override
    public Registrator getRegistrator() {
        return ForgeContainerRegistrator.getInstance();
    }
}
