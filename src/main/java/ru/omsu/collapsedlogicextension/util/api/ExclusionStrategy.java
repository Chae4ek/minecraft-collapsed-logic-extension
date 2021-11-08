package ru.omsu.collapsedlogicextension.util.api;

import com.google.gson.FieldAttributes;

public class ExclusionStrategy implements com.google.gson.ExclusionStrategy{

    /**
     * @param f the field object that is under test
     * @return true if the field should be ignored; otherwise false
     */
    @Override
    public boolean shouldSkipField(FieldAttributes f) {
        return f.getAnnotation(Exclude.class) != null;
    }

    /**
     * @param clazz the class object that is under test
     * @return true if the class should be ignored; otherwise false
     */
    @Override
    public boolean shouldSkipClass(Class<?> clazz) {
        return false;
    }

}
