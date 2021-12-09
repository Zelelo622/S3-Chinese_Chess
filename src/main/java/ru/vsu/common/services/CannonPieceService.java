package ru.vsu.common.services;


import ru.vsu.common.models.*;
import ru.vsu.common.models.enums.Direction;

import java.util.*;

public class CannonPieceService implements IPieceService {

    @Override
    public List<Cell> getPossibleMoves(Game game, Piece piece) {
        List<Direction> directions = Arrays.asList(Direction.NORTH, Direction.EAST, Direction.WEST, Direction.SOUTH);
        return new ArrayList<>(findCannonStep(game, piece, directions));
    }

    private List<Cell> findCannonStep(Game game, Piece piece, List<Direction> directions) {
        List<Cell> possibleMoves = new ArrayList<>();
        Cell pieceCell = game.getPieceToCellMap().get(piece);
        Cell currCell;
        Cell nextCell;
        Direction direction;
        for (Direction value : directions) {
            direction = value;
            nextCell = pieceCell.getNeighbors().get(direction);
            while (isMoveAvailable(game, piece, nextCell)) {
                currCell = nextCell;
                nextCell = currCell.getNeighbors().get(direction);
                possibleMoves.add(currCell);
                if (isMoveAvailable(game, piece, currCell) && stopsAfterKill(game, piece, currCell)) {
                    possibleMoves.remove(currCell);
                    possibleMoves.add(nextCell);
                    break;
                }
            }
        }
        return possibleMoves;
    }

    private boolean isMoveAvailable(Game game, Piece piece, Cell testedCell) {
        if (testedCell != null) {
            return ((game.getCellToPieceMap().get(testedCell) == null) ||
                    (game.getCellToPieceMap().get(testedCell) != null) &&
                            (game.getCellToPieceMap().get(testedCell).getPieceColor() != piece.getPieceColor()));
        }
        return false;
    }

    private boolean stopsAfterKill(Game game, Piece piece, Cell testedCell) {
        return ((game.getCellToPieceMap().get(testedCell) != null) &&
                (game.getCellToPieceMap().get(testedCell).getPieceColor() != piece.getPieceColor()));
    }

    @Override
    public Step doMove(Game game, Piece piece, Cell finCell) {
        return null;
    }
}
