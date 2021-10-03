package ru.omsu.collapsedlogicextension.tileentity;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import ru.omsu.collapsedlogicextension.CLEMod;

public class TileEntityRegistrator {

    private static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPES = DeferredRegister
            .create(ForgeRegistries.TILE_ENTITIES, CLEMod.MOD_ID);

    private static final List<RegistryObject<TileEntityType<? extends TileEntity>>> registryTileEntityTypes;

    static {
        registryTileEntityTypes = new ArrayList<>();

        for(final CLETileEntityEnum tileEntity : CLETileEntityEnum.values()){
            registryTileEntityTypes.add(TILE_ENTITY_TYPES.register(tileEntity.getRegistryName(), tileEntity.getConstructor()));
        }
    }

    public static void register() {
        TILE_ENTITY_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
