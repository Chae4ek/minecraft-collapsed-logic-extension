package ru.omsu.collapsedlogicextension.blocks.logicblock.util.board;

public class OperatorNot extends Accumulator{

    public OperatorNot(){
        super();
        directions.put(output, true);
        directions.put(Direction.oppositeOf(output), false);
        isOutputActive = true;
    }

    @Override
    public void activate(Direction from) {
        directions.replace(from, true);
        directions.replace(Direction.oppositeOf(from), false);
        isOutputActive = false;
        System.out.println("Activated " + directions);
    }

    @Override
    public void deactivate(Direction from) {
        directions.replace(from, false);
        directions.replace(Direction.oppositeOf(from), true);
        isOutputActive = true;
        System.out.println("Deactivated " + directions);
    }

    @Override
    public int getXTex() {
        return 68;
    }

    @Override
    public String getType() {
        return "OPERATOR NOT";
    }

    @Override
    public boolean isConnectableFrom(Direction direction) {
        return directions.containsKey(direction);
    }
}
