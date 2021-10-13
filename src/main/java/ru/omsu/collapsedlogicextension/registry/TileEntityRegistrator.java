package ru.omsu.collapsedlogicextension.registry;

import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import ru.omsu.collapsedlogicextension.CLEMod;
import ru.omsu.collapsedlogicextension.blocks.CLEBlockEnum;
import ru.omsu.collapsedlogicextension.tileentity.LogicBlockTileEntity;

public class TileEntityRegistrator {

    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPES = DeferredRegister
            .create(ForgeRegistries.TILE_ENTITIES, CLEMod.MOD_ID);



    public static final RegistryObject<TileEntityType<LogicBlockTileEntity>> COLLAPSED_LOGIC_BLOCK =
            TILE_ENTITY_TYPES.register("collapsed_logic_block",
                    () -> TileEntityType.Builder.create(LogicBlockTileEntity::new,
                            CLEBlockEnum.COLLAPSED_LOGIC_BLOCK.getConstructor().get()).build(null));


    public static void register() {

        TILE_ENTITY_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());

    }
}
