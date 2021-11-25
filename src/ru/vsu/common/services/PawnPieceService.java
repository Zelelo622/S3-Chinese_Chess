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

        if(isFirstPawnMove(game, piece, direction)) {
            possibleMoves.addAll(firstPawnStep(game, piece, direction));
        } else {
            possibleMoves.add(findPawnStep(game, piece, direction));
        }
        return null;
    }

    private List<Cell> firstPawnStep(Game game, Piece piece, Direction direction) {
        List<Cell> firstSteps = new ArrayList<>();
        Map<Piece, Cell> pieceCellMap = game.getPieceToCellMap();
        Cell currCell = pieceCellMap.get(piece);
        Cell nextCell;
        Cell nextLeftCell;
        Cell nextRightCell;

        for (int i = 0; i < 3; i++) {
            nextCell = currCell.getNeighbors().get(direction);
            nextLeftCell = nextCell.getNeighbors().get(Direction.WEST);
            nextRightCell = nextCell.getNeighbors().get(Direction.EAST);
            firstSteps.add(nextCell);

            nextCell = currCell.getNeighbors().get(direction);
            if(isAttackAvailable(game, piece, nextLeftCell)) {
                firstSteps.add(nextLeftCell);
            }

            if(isAttackAvailable(game, piece, nextRightCell)) {
                firstSteps.add(nextRightCell);
            }
            currCell = nextCell;
        }
        return firstSteps;
    }

    private Cell findPawnStep(Game game, Piece piece, Direction direction) {
        Cell currentCell = game.getPieceToCellMap().get(piece);
        Cell nextCell = currentCell.getNeighbors().get(direction);

        if(isMoveAvailable(game, nextCell)) {
            return nextCell;
        }
        return null;
    }

    private boolean isFirstPawnMove(Game game, Piece piece, Direction direction) {
        Cell currCell = game.getPieceToCellMap().get(piece);

        Cell prevCell = currCell.getNeighbors().get(direction);
        return prevCell.getNeighbors().get(direction) == null;
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
