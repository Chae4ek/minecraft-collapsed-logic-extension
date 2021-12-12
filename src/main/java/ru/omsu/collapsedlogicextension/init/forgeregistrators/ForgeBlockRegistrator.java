package ru.omsu.collapsedlogicextension.init.forgeregistrators;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import ru.omsu.collapsedlogicextension.init.ModInit;
import ru.omsu.collapsedlogicextension.init.Registrator;
import ru.omsu.collapsedlogicextension.logicblock.LogicBlock;
import ru.omsu.collapsedlogicextension.logicblock.LogicBlockTileEntity;

public class ForgeBlockRegistrator implements Registrator {

    private static ForgeBlockRegistrator instance;

    /** Все зарегистрированные блоки мода */
    private final Map<Class<? extends Block>, RegistryObject<Block>> registeredBlocks =
            new HashMap<>();

    private ForgeBlockRegistrator() {}

    public static ForgeBlockRegistrator getInstance() {
        return instance == null ? instance = new ForgeBlockRegistrator() : instance;
    }

    /** @return зарегистрированный блок */
    public Block getBlock(final Class<? extends Block> clazz) {
        return registeredBlocks.get(clazz).get();
    }

    @Override
    public void registerAll() {
        // регистратор блоков
        final DeferredRegister<Block> BLOCKS =
                DeferredRegister.create(ForgeRegistries.BLOCKS, ModInit.MOD_ID);

        // регистраторы Forge существуют только вместе
        final ForgeTileEntityRegistrator tileEntityRegistrator =
                ForgeTileEntityRegistrator.getInstance();

        final RegistryObject<Block> registeredlogicBlock =
                BLOCKS.register(
                        "logic_block",
                        () ->
                                new LogicBlock(
                                        () ->
                                                tileEntityRegistrator.getTileEntityType(
                                                        LogicBlockTileEntity.class)));
        registeredBlocks.put(LogicBlock.class, registeredlogicBlock);

        // вкладка в инвентаре креатива для предметов мода
        final ItemGroup MOD_GROUP =
                new ItemGroup(ModInit.MOD_ID) {
                    @OnlyIn(Dist.CLIENT)
                    @Override
                    public ItemStack createIcon() {
                        return new ItemStack(registeredlogicBlock.get());
                    }
                };

        // регистратор предметов блоков
        final DeferredRegister<Item> ITEMS =
                DeferredRegister.create(ForgeRegistries.ITEMS, ModInit.MOD_ID);

        ITEMS.register(
                "logic_block",
                () ->
                        new BlockItem(
                                registeredlogicBlock.get(),
                                new Item.Properties().group(MOD_GROUP)));

        final IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        BLOCKS.register(bus);
        ITEMS.register(bus);
    }
}
