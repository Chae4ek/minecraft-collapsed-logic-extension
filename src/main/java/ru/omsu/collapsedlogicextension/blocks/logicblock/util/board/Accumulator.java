package ru.omsu.collapsedlogicextension.blocks.logicblock.util.board;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class Accumulator implements State {

    protected Map<Direction, Boolean> directions;
    protected Direction output;
    protected boolean isOutputActive;

    public Accumulator() {
        this.output = Direction.NORTH;
        this.directions = new HashMap<>();
        this.isOutputActive = false;
    }

    @Override
    public void rotate(){
        output = Direction.rotate(output);

        Map<Boolean, Direction> reversedMap = reverse(directions);

        for(Map.Entry<Boolean, Direction> entry : reversedMap.entrySet()){
            entry.setValue(Direction.rotate(entry.getValue()));
        }

        this.directions = reverse(reversedMap);
        System.out.println(directions);
    }

    @Override
    public void activate(Direction from) {
        if(from!=output) {
            directions.replace(from, true);
        }
    }

    @Override
    public void deactivate(Direction from) {
        if(from!=output) {
            directions.replace(from, false);
        }
    }

    public boolean allInputsActive(){
        for(Map.Entry<Direction, Boolean> entry : directions.entrySet()){
            if(entry.getKey()!=output && !entry.getValue()){
                return false;
            }
        }
        return true;
    }

    public boolean allInputsNotActive(){
        for(Map.Entry<Direction, Boolean> entry : directions.entrySet()){
            if(entry.getKey()!=output && entry.getValue()){
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean isConnectableFrom(Direction direction) {
        return direction != Direction.oppositeOf(output);
    }

    @Override
    public boolean isActive() {
        return directions.containsValue(true);
    }

    @Override
    public boolean isActiveAt(Direction direction) {
        return directions.get(direction);
    }

    @Override
    public boolean isLogical() {
        return true;
    }

    private <T, V> Map<V, T> reverse(Map<T, V> map){
        return map.entrySet().stream().collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));
    }
}
