package ru.omsu.collapsedlogicextension.util.adapter;

import net.minecraft.item.Item;
import ru.omsu.collapsedlogicextension.init.ModObjectEnum.ModObject;
import ru.omsu.collapsedlogicextension.util.api.ModItem;

/** Перехватывает методы игрового предмета */
public class ItemAdapter<E extends ModItem<E>> extends Item {

    private final E item;

    public ItemAdapter(final ModObject<E, ?, ?, ?, ?> modObject) {
        // TODO: добавить настройки в параметры конструктора?
        super(new Properties());
        item = modObject.itemFactory.create(this);
    }
}
