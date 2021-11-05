package ru.omsu.collapsedlogicextension.util.api;

import ru.omsu.collapsedlogicextension.util.adapter.ItemAdapter;

/** Основной класс для всех предметов мода */
public abstract class ModItem<E extends ModItem<E>> {

    private final ItemAdapter<E> itemAdapter;

    public ModItem(final ItemAdapter<E> itemAdapter) {
        this.itemAdapter = itemAdapter;
    }

    public interface ModItemFactory<E extends ModItem<E>> {
        E create(ItemAdapter<E> adapter);
    }
}
