package ru.vsu.common.services;

import ru.vsu.common.models.Cell;
import ru.vsu.common.models.enums.ColorEnum;
import ru.vsu.common.models.enums.Direction;

import java.util.ArrayList;
import java.util.List;

public class GameService {

    public static final Integer BOARD_COL = 9;
    public static final Integer BOARD_ROW = 10;

    public List<List<Cell>> initBoard() {
        List<List<Cell>> graph = new ArrayList<>();
        List<Cell> prevRow;
        prevRow = null;
        for (int row = 0; row < BOARD_ROW; row++) {
            Cell prevCell = null;
            List<Cell> currRow = new ArrayList<>();
            for (int col = 0; col < BOARD_COL; col++) {
                Cell currCell = new Cell(ColorEnum.BLACK);
                if (prevCell != null) {
                    currCell.getNeighbors().put(Direction.WEST, prevCell);
                    prevCell.getNeighbors().put(Direction.EAST, currCell);
                }
                currRow.add(currCell);
                prevCell = currCell;
                if (prevRow != null) {
                    for (int dir = 0; dir < currRow.size(); dir++) {
                        Cell currentRowCell = currRow.get(dir);
                        Cell prevRowCell = prevRow.get(dir);
                        currentRowCell.getNeighbors().put(Direction.SOUTH, prevRowCell);
                        prevRowCell.getNeighbors().put(Direction.NORTH, currentRowCell);
                    }
                }
                prevRow = currRow;
            }
            graph.add(prevRow);
        }
        return graph;
    }
}
