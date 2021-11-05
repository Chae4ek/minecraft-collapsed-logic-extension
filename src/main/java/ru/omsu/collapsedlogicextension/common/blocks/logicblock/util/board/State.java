package ru.omsu.collapsedlogicextension.common.blocks.logicblock.util.board;

import java.util.HashMap;
import java.util.Map;

public interface State {

    default void activate(final Direction from) {}

    default void deactivate(final Direction from) {}

    default void addDirection(final Direction direction) {}

    default void removeDirection(final Direction direction) {}

    default Map<Integer, Boolean> getDirections() {
        return new HashMap<>();
    }

    default boolean isConnectableFrom(final Direction direction) {
        return false;
    }

    default boolean isLogical() {
        return false;
    }

    default boolean isActive() {
        return false;
    }

    default boolean isActiveAt(final Direction direction) {
        return false;
    }

    default void rotate() {}

    int getXTex();

    String getType();
}
