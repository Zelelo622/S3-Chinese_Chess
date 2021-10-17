package ru.vsu.common.models;

import ru.vsu.common.models.enums.ColorEnum;
import ru.vsu.common.models.enums.Direction;

import java.util.LinkedHashMap;
import java.util.Map;

public class Cell {

    private final ColorEnum color;
    private final Map<Direction, Cell> neighbors = new LinkedHashMap<>();

    public Cell(ColorEnum color) {
        this.color = color;
    }

    public ColorEnum getColor() {
        return color;
    }

    public Map<Direction, Cell> getNeighbors() {
        return neighbors;
    }

    @Override
    public String toString() {
        return "Cell{" +
                "color=" + color +
                '}';
    }
}
