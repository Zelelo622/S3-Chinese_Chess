package ru.vsu.common.services;

import ru.vsu.common.models.Cell;
import ru.vsu.common.models.Game;
import ru.vsu.common.models.Piece;
import ru.vsu.common.models.Step;
import ru.vsu.common.models.enums.Direction;

import java.util.*;

public class BishopPieceService implements IPieceService {

    @Override
    public List<Cell> getPossibleMoves(Game game, Piece piece) {
        List<Cell> possibleMoves = new ArrayList<>();
        Set<Cell> beatMoves = new LinkedHashSet<>();
        List<Direction> directions = Arrays.asList(Direction.NORTH_EAST, Direction.NORTH_WEST,
                Direction.SOUTH_EAST, Direction.SOUTH_WEST);
        possibleMoves.addAll(findBishopSteps(game, piece, directions));
        return possibleMoves;
    }

    private List<Cell> findBishopSteps(Game game, Piece piece, List<Direction> directions) {
        List<Cell> possibleMoves = new ArrayList<>();
        Cell pieceCell = game.getPieceToCellMap().get(piece);
        Cell currCell;
        Cell nextCell;
        Direction direction;
        for (Direction value : directions) {
            direction = value;
            nextCell = pieceCell.getNeighbors().get(direction);
            for (int j = 0; j < 2; j++) {
                if (isMoveAvailable(game, nextCell)) {
                    currCell = nextCell;
                    nextCell = currCell.getNeighbors().get(direction);
                    if (j == 1) {
                        possibleMoves.add(currCell);
                    }
                } else {
                    break;
                }
            }
        }
        return possibleMoves;
    }

    private boolean isMoveAvailable(Game game, Cell testedCell) {
        if(testedCell != null) {
            return game.getCellToPieceMap().get(testedCell) == null;
        }
        return false;
    }

    @Override
    public Step doMove(Game game) {
        return null;
    }
}
