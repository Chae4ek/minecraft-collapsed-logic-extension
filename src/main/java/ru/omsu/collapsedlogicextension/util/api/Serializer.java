package ru.omsu.collapsedlogicextension.util.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/** Содержит методы для общего использования без необходимости создавать Gson */
public class Serializer {

    private static final Gson gson = new GsonBuilder().setExclusionStrategies(new ExclusionStrategy()).create();

    public static <T> String serialize(final T object) {
        return gson.toJson(object);
    }

    public static <T> T deserialize(final String json, final Class<T> classOfT) {
        return gson.fromJson(json, classOfT);
    }

    /** Интерфейс для помощи в (де)сериализации NBT тегов */
    public interface Serializable {
        /**
         * Класс должен возвращать данные, которые потом сможет десериализовать через метод {@link
         * #deserialize}
         */
        String serialize();

        /**
         * Класс должен загружать данные, которые потом сможет сериализовать через метод {@link
         * #serialize}
         */
        void deserialize(String data);
    }
}
