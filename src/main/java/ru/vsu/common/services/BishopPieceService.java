package ru.vsu.common.services;

import ru.vsu.common.models.*;
import ru.vsu.common.models.enums.Direction;

import java.util.*;

public class BishopPieceService implements IPieceService {

    @Override
    public List<Cell> getPossibleMoves(Game game, Piece piece) {
        List<Direction> directions = Arrays.asList(Direction.NORTH_EAST, Direction.NORTH_WEST,
                Direction.SOUTH_EAST, Direction.SOUTH_WEST);
        return new ArrayList<>(findBishopSteps(game, piece, directions));
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
                if (isMoveAvailable(game, piece, nextCell)) {
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

    private boolean isMoveAvailable(Game game, Piece piece, Cell testedCell) {
        if(testedCell != null) {
            for (int i = 0; i < game.getRiverCells().size(); i++) {
                if (testedCell == game.getRiverCells().get(i)) {
                    return false;
                }
            }
            return ((game.getCellToPieceMap().get(testedCell) == null) ||
                    (game.getCellToPieceMap().get(testedCell) != null) &&
                            (game.getCellToPieceMap().get(testedCell).getPieceColor() != piece.getPieceColor()));
        }
        return false;
    }

    @Override
    public Step doMove(Game game, Piece piece, Cell finCell) {
        return null;
    }
}
