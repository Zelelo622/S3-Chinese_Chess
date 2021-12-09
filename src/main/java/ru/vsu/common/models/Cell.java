package ru.vsu.common.models;

import ru.vsu.common.models.enums.Direction;

import java.util.LinkedHashMap;
import java.util.Map;

public class Cell {

    private final Map<Direction, Cell> neighbors = new LinkedHashMap<>();
    private String consoleCoordinates;

    public Cell(String consoleCoordinates) {
        this.consoleCoordinates = consoleCoordinates;
    }

    public String getConsoleCoordinates() {
        return consoleCoordinates;
    }

    public Map<Direction, Cell> getNeighbors() {
        return neighbors;
    }
}
