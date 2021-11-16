package ru.omsu.collapsedlogicextension.util.api;

import net.minecraft.client.gui.widget.Widget;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import ru.omsu.collapsedlogicextension.util.proxy.ContainerScreenProxy;

/** Основной класс для всех GUI контейнеров мода */
@OnlyIn(Dist.CLIENT)
public abstract class ModContainerScreen<E extends ModContainerScreen<E>> {

    private final ContainerScreenProxy<E> containerScreenProxy;

    private final int width;
    private final int height;

    public ModContainerScreen(
            final ContainerScreenProxy<E> containerScreenProxy,
            final int width,
            final int height) {
        this.containerScreenProxy = containerScreenProxy;
        this.width = width;
        this.height = height;
    }

    /** @return tile entity этого объекта, если он есть, иначе крашнется игра */
    @Unsafe
    public <T extends ModTileEntity<T>> T getModTileEntity() {
        return containerScreenProxy.getModTileEntity();
    }

    /**
     * Вызывается при инициализации GUI. ДОЛЖЕН создавать элементы именно здесь, а не в конструкторе
     */
    public abstract void init();

    /** @return ширина GUI */
    public final int getWidth() {
        return width;
    }

    /** @return высота GUI */
    public final int getHeight() {
        return height;
    }

    /** @return текстура заднего фона GUI */
    public abstract ResourceLocation getGuiBackgroundTexture();

    /** Вызывается при отрисовке GUI */
    public void onGuiDraw() {}

    /** Рисует текст на GUI с координатами (x,y) и цветом color */
    public final void drawString(final String text, final float x, final float y, final int color) {
        containerScreenProxy.drawString(text, x, y, color);
    }

    /** Добавляет новую кнопку на GUI */
    public final void addButton(final Widget widget) {
        containerScreenProxy.addNewButton(widget);
    }

    public interface ModContainerScreenFactory<E extends ModContainerScreen<E>> {
        E create(ContainerScreenProxy<E> adapter);
    }
}
