package ru.omsu.collapsedlogicextension.blocks.logicblock.util.board;

import net.minecraft.client.gui.widget.button.ImageButton;

import java.util.HashMap;
import java.util.Map;

public class Wire extends Cell {

    boolean isActivated;

    private Map<Direction, Boolean> directions;

    public Wire(int x, int y){
        super(x, y);
        directions = new HashMap<>(4);
    }

    @Override
    public void activate(Direction from) {
        for(Map.Entry<Direction, Boolean> entry : directions.entrySet()){
            directions.replace(entry.getKey(), true);
        }
    }

    @Override
    public void deactivate(Direction from, Direction to) {
        directions.replace(from, false);
    }

    public void addDirection(Direction direction){
        directions.put(direction, false);
    }

    //TODO: прописать
    public void removeDirection(Direction direction){
        directions.remove(direction);
    }

    public void removeAllDirections() {directions.clear();}

    public Map<Direction, Boolean> getDirections() {
        return directions;
    }
}
