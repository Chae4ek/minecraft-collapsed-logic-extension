package ru.omsu.collapsedlogicextension.init;

import ru.omsu.collapsedlogicextension.common.blocks.logicblock.LogicBlock;
import ru.omsu.collapsedlogicextension.common.blocks.logicblock.gui.LogicBlockScreen;
import ru.omsu.collapsedlogicextension.common.blocks.logicblock.util.LogicBlockContainer;
import ru.omsu.collapsedlogicextension.common.blocks.logicblock.util.LogicBlockTileEntity;
import ru.omsu.collapsedlogicextension.util.api.ModBlock;
import ru.omsu.collapsedlogicextension.util.api.ModBlock.ModBlockFactory;
import ru.omsu.collapsedlogicextension.util.api.ModContainer;
import ru.omsu.collapsedlogicextension.util.api.ModContainer.ModContainerFactory;
import ru.omsu.collapsedlogicextension.util.api.ModContainerScreen;
import ru.omsu.collapsedlogicextension.util.api.ModContainerScreen.ModContainerScreenFactory;
import ru.omsu.collapsedlogicextension.util.api.ModItem;
import ru.omsu.collapsedlogicextension.util.api.ModItem.ModItemFactory;
import ru.omsu.collapsedlogicextension.util.api.ModTileEntity;
import ru.omsu.collapsedlogicextension.util.api.ModTileEntity.ModTileEntityFactory;

/** Описывает все объекты мода */
public enum ModObjectEnum {
    LOGIC_BLOCK(
            "logic_block",
            null,
            LogicBlock::new,
            LogicBlockTileEntity::new,
            LogicBlockContainer::new,
            LogicBlockScreen::new);

    public final ModObject<?, ?, ?, ?, ?> modObject;

    <
                    I extends ModItem<I>,
                    B extends ModBlock<B>,
                    TE extends ModTileEntity<TE>,
                    C extends ModContainer<C>,
                    CS extends ModContainerScreen<CS>>
            ModObjectEnum(
                    final String registryName,
                    final ModItemFactory<I> itemFactory,
                    final ModBlockFactory<B> blockFactory,
                    final ModTileEntityFactory<TE> tileEntityFactory,
                    final ModContainerFactory<C> containerFactory,
                    final ModContainerScreenFactory<CS> containerScreenFactory) {
        modObject =
                new ModObject<>(
                        this,
                        registryName,
                        itemFactory,
                        blockFactory,
                        tileEntityFactory,
                        containerFactory,
                        containerScreenFactory);
    }

    /** Объект мода */
    public static class ModObject<
            I extends ModItem<I>,
            B extends ModBlock<B>,
            TE extends ModTileEntity<TE>,
            C extends ModContainer<C>,
            CS extends ModContainerScreen<CS>> {

        /** Ссылка на enum этого объекта (используется для оптимизации регистратора) */
        public final ModObjectEnum thisEnum;

        /** Регистрируемое имя должно совпадать со всеми файлами ресурсов */
        public final String registryName;
        /** Фабрика item, если есть, иначе null */
        public final ModItemFactory<I> itemFactory;
        /** Фабрика блока, если есть, иначе null */
        public final ModBlockFactory<B> blockFactory;
        /** Фабрика tile entity, если есть, иначе null */
        public final ModTileEntityFactory<TE> tileEntityFactory;
        /** Фабрика контейнера, если есть, иначе null */
        public final ModContainerFactory<C> containerFactory;
        /** Фабрика GUI для контейнера, если есть, иначе null */
        public final ModContainerScreenFactory<CS> containerScreenFactory;

        private ModObject(
                final ModObjectEnum thisEnum,
                final String registryName,
                final ModItemFactory<I> itemFactory,
                final ModBlockFactory<B> blockFactory,
                final ModTileEntityFactory<TE> tileEntityFactory,
                final ModContainerFactory<C> containerFactory,
                final ModContainerScreenFactory<CS> containerScreenFactory) {
            this.thisEnum = thisEnum;
            this.registryName = registryName;
            this.itemFactory = itemFactory;
            this.blockFactory = blockFactory;
            this.tileEntityFactory = tileEntityFactory;
            this.containerFactory = containerFactory;
            this.containerScreenFactory = containerScreenFactory;
        }
    }
}
