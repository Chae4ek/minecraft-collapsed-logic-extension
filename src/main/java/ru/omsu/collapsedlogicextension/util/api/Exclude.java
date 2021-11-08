package ru.omsu.collapsedlogicextension.util.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**Поле, отмеченнное этой аннотацией, будет игнорироваться при сериализации объекта*/

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Exclude {
}
