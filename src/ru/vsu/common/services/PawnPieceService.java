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
        Set<Cell> possibleMoves = new LinkedHashSet<>();
        Set<Cell> beatMoves = new LinkedHashSet<>();
        ColorEnum pieceColor = piece.getPieceColor();
        Map<Piece, Cell> pieceCellMap = game.getPieceToCellMap();
        Direction direction = getDirection(piece);
        possibleMoves.add(findPawnStep(game, piece, direction));
        return null;
    }

    private Cell findPawnStep(Game game, Piece piece, Direction direction) {
        Cell currentCell = game.getPieceToCellMap().get(piece);
        Cell nextCell = currentCell.getNeighbors().get(direction);

        if(isMoveAvailable(game, nextCell)) {
            return nextCell;
        }
        return null;
    }

    private Direction getDirection(Piece piece) {
        if(piece.getPieceColor() == ColorEnum.BLACK) {
            return Direction.SOUTH;
        } else {
            return Direction.NORTH;
        }
    }

    private boolean isAttackAvailable(Game game, Piece piece, Cell testedCell) {
        if (testedCell != null) {
            return (game.getCellToPieceMap().get(testedCell) != null) &&
                    (game.getCellToPieceMap().get(testedCell).getPieceColor() != piece.getPieceColor());
        }
        return false;
    }

    private boolean isMoveAvailable(Game game, Cell testedCell) {
        if(testedCell != null) {
            return game.getCellToPieceMap().get(testedCell) == null;
        }
        return false;
    }
}
