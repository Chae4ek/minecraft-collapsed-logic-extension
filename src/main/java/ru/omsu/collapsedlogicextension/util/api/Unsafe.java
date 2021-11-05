package ru.omsu.collapsedlogicextension.util.api;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * Метод, отмеченный этой аннотацией является небезопасным, т.е. может крашнуть игру, если
 * использован неправильно
 */
@Documented
@Target(ElementType.METHOD)
public @interface Unsafe {}
