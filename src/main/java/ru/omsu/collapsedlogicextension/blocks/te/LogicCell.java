package ru.omsu.collapsedlogicextension.blocks.te;

import java.awt.*;

public interface LogicCell extends Cell{
    void activate(LogicCell from, Direction to);
    void deactivate(LogicCell from, Direction to);
}
