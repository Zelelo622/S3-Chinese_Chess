package ru.vsu.common.services;

import ru.vsu.common.models.Cell;
import ru.vsu.common.models.Game;
import ru.vsu.common.models.Piece;
import ru.vsu.common.models.Step;
import ru.vsu.common.models.enums.ColorEnum;
import ru.vsu.common.models.enums.Direction;

import java.util.*;

public class PawnPieceService implements IPieceService {

    @Override
    public Step doMove(Game game) {
        return null;
    }

    @Override
    public List<Cell> getPossibleMoves(Game game, Piece piece) {
        Direction direction = getDirection(piece);
        List<Cell> possibleMoves = new ArrayList<>(findPawnStep(game, piece, direction));
        Set<Cell> beatMoves = new LinkedHashSet<>();
        return possibleMoves;
    }

    private List<Cell> findPawnStep(Game game, Piece piece, Direction direction) {
        List<Cell> availableCell = new ArrayList<>();
        Cell currentCell = game.getPieceToCellMap().get(piece);
        Cell nextCell = currentCell.getNeighbors().get(direction);
        Cell nextLeftCell = currentCell.getNeighbors().get(Direction.WEST);
        Cell nextRightCell = currentCell.getNeighbors().get(Direction.EAST);
        if (isMoveAvailable(game, piece, nextCell)) {
            availableCell.add(nextCell);
        }
        if (isMoveAvailable(game, piece, nextLeftCell)) {
            availableCell.add(nextLeftCell);
        }
        if (isMoveAvailable(game, piece, nextRightCell)) {
            availableCell.add(nextRightCell);
        }
        return availableCell;
    }

    private Direction getDirection(Piece piece) {
        if (piece.getPieceColor() == ColorEnum.BLACK) {
            return Direction.SOUTH;
        } else {
            return Direction.NORTH;
        }
    }

    private boolean isMoveAvailable(Game game, Piece piece, Cell testedCell) {
        if (testedCell != null) {
            return ((game.getCellToPieceMap().get(testedCell) == null) ||
                    (game.getCellToPieceMap().get(testedCell) != null) &&
                            (game.getCellToPieceMap().get(testedCell).getPieceColor() != piece.getPieceColor()));
        }
        return false;
    }
}
