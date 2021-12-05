package ru.vsu.common.models;

import ru.vsu.common.models.enums.Direction;

import java.util.LinkedHashMap;
import java.util.Map;

public class Cell {
    private final Map<Direction, Cell> neighbors = new LinkedHashMap<>();

    public Cell() {
    }

    public Map<Direction, Cell> getNeighbors() {
        return neighbors;
    }

    @Override
    public String toString() {
        return ".";
    }
}
