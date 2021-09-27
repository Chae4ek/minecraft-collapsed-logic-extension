package ru.omsu.collapsedlogicextension.exceptions;

/** Используется для непредвиденных ошибок мода, которых быть не должно */
public class CLEException extends RuntimeException {

    public CLEException(final CLEErrorEnum error) {
        super(error.getMsg());
    }
}
