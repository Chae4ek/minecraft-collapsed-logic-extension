package ru.omsu.collapsedlogicextension.exceptions;

/** Сообщения об ошибках мода */
public enum CLEErrorEnum {
    REGISTERING_ITEM_BEFORE_ITS_BLOCK(
            "Блок еще не был зарегистрирован, но его предмет уже регистрируется");

    private final String msg;

    CLEErrorEnum(final String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
