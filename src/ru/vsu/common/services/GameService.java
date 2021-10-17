package ru.vsu.common.services;

import ru.vsu.common.models.Cell;
import ru.vsu.common.models.enums.ColorEnum;
import ru.vsu.common.models.enums.Direction;

import java.util.ArrayList;
import java.util.List;

public class GameService {

    public static final Integer BOARD_COL = 9;
    public static final Integer BOARD_ROW = 10;

//    public void initBoard() {
//        List<List<Cell>> graph = new ArrayList<>();
//        List<ColorEnum> cellColors = Arrays.asList(ColorEnum.BLACK, ColorEnum.WHITE);
//
//        List<Cell> prevRow = new ArrayList<>();
//        prevRow = null;
//        for (int i = 0; i < BOARD_SIZE; i++) {
//            Cell prevCell = null;
//            List<Cell> currRow = new ArrayList<>();
//            for (int j = 0; j < BOARD_SIZE; j++) {
//                Cell currCell = new Cell(cellColors.get(0));
//                if (prevCell != null) {
//                    currCell.getNeighbors().put(Direction.WEST, prevCell);
//                    prevCell.getNeighbors().put(Direction.EAST, currCell);
//                }
//                currRow.add(currCell);
//                prevCell = currCell;
//                if (prevRow != null) {
//                    for (int k = 0; k < currRow.size(); k++) {
//                        Cell currentRowCell = currRow.get(k);
//                        Cell prevRowCell = prevRow.get(k);
//                        currentRowCell.getNeighbors().put(Direction.SOUTH, prevRowCell);
//                        prevRowCell.getNeighbors().put(Direction.NORTH, currentRowCell);
//                    }
//                }
//                prevRow = currRow;
//            }
//            graph.add(prevRow);
//        }
//    }

    public List<List<Cell>> initBoard() {
        List<List<Cell>> graph = new ArrayList<>();
        List<Cell> prevRow = new ArrayList<>();
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
