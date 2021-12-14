package ru.omsu.collapsedlogicextension.init.forgeregistrators;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import ru.omsu.collapsedlogicextension.init.ModInit;
import ru.omsu.collapsedlogicextension.init.Registrator;
import ru.omsu.collapsedlogicextension.logicblock.LogicBlock;
import ru.omsu.collapsedlogicextension.logicblock.LogicBlockContainer;
import ru.omsu.collapsedlogicextension.logicblock.LogicBlockTileEntity;

public class ForgeTileEntityRegistrator implements Registrator {

    private static ForgeTileEntityRegistrator instance;

    /** Все зарегистрированные tile entities мода */
    private final Map<Class<? extends TileEntity>, RegistryObject<TileEntityType<?>>>
            registeredTileEntities = new HashMap<>();

    private ForgeTileEntityRegistrator() {}

    public static ForgeTileEntityRegistrator getInstance() {
        return instance == null ? instance = new ForgeTileEntityRegistrator() : instance;
    }

    /** @return зарегистрированный tile entity */
    public TileEntityType<?> getTileEntityType(final Class<? extends TileEntity> clazz) {
        return registeredTileEntities.get(clazz).get();
    }

    @Override
    public void registerAll() {
        // регистратор tile entities
        final DeferredRegister<TileEntityType<?>> TILE_ENTITIES =
                DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, ModInit.MOD_ID);

        // регистраторы Forge существуют только вместе
        final ForgeBlockRegistrator blockRegistrator = ForgeBlockRegistrator.getInstance();
        final ForgeContainerRegistrator containerRegistrator =
                ForgeContainerRegistrator.getInstance();

        registeredTileEntities.put(
                LogicBlockTileEntity.class,
                TILE_ENTITIES.register(
                        "logic_block",
                        () ->
                                TileEntityType.Builder.create(
                                                () ->
                                                        new LogicBlockTileEntity(
                                                                registeredTileEntities
                                                                        .get(
                                                                                LogicBlockTileEntity
                                                                                        .class)
                                                                        .get(),
                                                                containerRegistrator
                                                                        .getContainerType(
                                                                                LogicBlockContainer
                                                                                        .class)),
                                                blockRegistrator.getBlock(LogicBlock.class))
                                        .build(null)));

        TILE_ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
