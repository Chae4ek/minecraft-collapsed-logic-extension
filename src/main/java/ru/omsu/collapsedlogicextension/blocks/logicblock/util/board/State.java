package ru.omsu.collapsedlogicextension.blocks.logicblock.util.board;

import java.util.HashMap;
import java.util.Map;

public interface State {

    default void activate(Direction from){}
    default void deactivate(Direction from){}

    default void addDirection(Direction direction){}
    default void removeDirection(Direction direction){}

    default Map<Integer, Boolean> getDirections(){return new HashMap<>();}

    default boolean isConnectableFrom(Direction direction){return false;}

    default boolean isLogical() { return false; }

    default boolean isActive(){return false;}
    default boolean isActiveAt(Direction direction){return false;}

    default void rotate(){}

    int getXTex();

    String getType();
}
