package ru.omsu.collapsedlogicextension.items;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import ru.omsu.collapsedlogicextension.CLEMod;

/** Регистрирует все предметы мода */
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
     * Регистрирует предметы. Следует вызывать только 1 раз при инициализации мода и ПОСЛЕ
     * регистрации блоков
     */
    public static void register() {
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
