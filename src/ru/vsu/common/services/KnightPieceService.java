package ru.vsu.common.services;

import ru.vsu.common.models.Cell;
import ru.vsu.common.models.Game;
import ru.vsu.common.models.Piece;
import ru.vsu.common.models.Step;
import ru.vsu.common.models.enums.Direction;

import java.util.*;

public class KnightPieceService implements IPieceService {

    @Override
    public List<Cell> getPossibleMoves(Game game, Piece piece) {
        List<Cell> possibleMoves = new ArrayList<>();
        //Set<Cell> possibleMoves = new LinkedHashSet<>();
        Set<Cell> beatMoves = new LinkedHashSet<>();

        List<Direction> directionEnumList =
                Arrays.asList(Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST);

        possibleMoves.addAll(findKnightMoves(game, piece, directionEnumList));
        return possibleMoves;
    }

    private List<Cell> findKnightMoves(
            Game game,
            Piece piece,
            List<Direction> directionsList
    ) {
        List<Cell> possibleMoves = new ArrayList<>();
//        List<DirectionEnum> horizontalDirs = directionsList.subList(2, 3);
//        List<DirectionEnum> verticalDirs = directionsList.subList(0, 1);
        Direction dir;
        Cell receivedCell = game.getPieceToCellMap().get(piece);
        Cell currentCell;
        Cell nextCell;
        Cell tempCell;

        for (int i = 0; i < directionsList.size(); i++) {
            dir = directionsList.get(i);
            currentCell = receivedCell;
            nextCell = currentCell.getNeighbors().get(dir);
            if(nextCell!= null) {
                //tempCell = nextCell;
                currentCell = nextCell;
                nextCell = currentCell.getNeighbors().get(dir);
                if(nextCell != null) {
                    currentCell = nextCell;


                    for (int j = 0, k = 1; j < directionsList.size(); j += 2, k += 2) {
                        if(i % 2 == 0) {
                            dir = directionsList.get(k);
                        } else {
                            dir = directionsList.get(j);
                        }
                        tempCell = currentCell.getNeighbors().get(dir);
                        if(PieceServiceUtil.isMoveAvailable(game, piece, tempCell)) {
                            possibleMoves.add(tempCell);
                        }
                    }
                }
            }
        }
        return possibleMoves;
    }

    @Override
    public Step doMove(Game game) {
        return null;
    }

}
