package ru.omsu.collapsedlogicextension.items;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import ru.omsu.collapsedlogicextension.CLEMod;
import ru.omsu.collapsedlogicextension.blocks.CLEBlockEnum;
import ru.omsu.collapsedlogicextension.exceptions.CLEErrorEnum;
import ru.omsu.collapsedlogicextension.exceptions.CLEException;

import java.util.ArrayList;
import java.util.List;

public class ItemRegistrator {

    public static final List<RegistryObject<Item>> registryItems;

    private static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, CLEMod.MOD_ID);

    static {
        registryItems = new ArrayList<>();

        for (final CLEItemEnum item : CLEItemEnum.values()) {
            registryItems.add(ITEMS.register(item.getRegistryName(), item.getConstructor()));
        }
    }

    /**
     * Регистрирует предметы. Следует вызывать только 1 раз при инициализации мода и ДО регистрации
     * предметов
     */
    public static void register() {
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

}
