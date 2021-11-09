package ru.omsu.collapsedlogicextension.init;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.common.Mod;

/** Основной класс для инициализации мода */
@Mod(ModInit.MOD_ID)
public class ModInit {

    public static final String MOD_ID = "collapsedlogicextension";

    /** Вкладка в инвентаре креатива для предметов мода */
    @MethodsReturnNonnullByDefault
    public static final ItemGroup MOD_GROUP =
            new ItemGroup(MOD_ID) {
                @OnlyIn(Dist.CLIENT)
                @Override
                public ItemStack createIcon() {
                    return new ItemStack(Registrator.getBlock(ModObjectEnum.LOGIC_BLOCK));
                }
            };

    public ModInit() {
        Registrator.registerAll();
    }
}
