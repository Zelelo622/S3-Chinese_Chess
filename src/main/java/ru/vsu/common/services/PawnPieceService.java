package ru.vsu.common.services;

import ru.vsu.common.models.*;
import ru.vsu.common.models.enums.ColorEnum;
import ru.vsu.common.models.enums.Direction;

import java.util.*;

public class PawnPieceService implements IPieceService {

    @Override
    public List<Cell> getPossibleMoves(Game game, Piece piece) {
        Direction direction = getDirection(piece);
        return new ArrayList<>(findPawnStep(game, piece, direction));
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

    @Override
    public Step doMove(Game game, Piece piece, Cell targetCell) {
        Step pawnStep = new Step();
        Cell currPosition = game.getPieceToCellMap().get(piece);

        pawnStep.setPlayer(game.getPieceToPlayerMap().get(piece));
        pawnStep.setStartCell(currPosition);
        pawnStep.setEndCell(targetCell);
        pawnStep.setPiece(piece);
        if(isTargetCellNotEmpty(game, targetCell)) {
            pawnStep.setKilledPiece(game.getCellToPieceMap().get(targetCell));
        }
        game.getSteps().add(pawnStep);
        changeOnBoardPlacement(game, piece, targetCell, currPosition);
        return pawnStep;
    }

    private void changeOnBoardPlacement(Game game, Piece piece, Cell targetCell, Cell currPosition) {
        Player rival;
        Piece targetPiece;
        if(isTargetCellNotEmpty(game, targetCell)) {
            targetPiece = game.getCellToPieceMap().get(targetCell);
            rival = game.getPieceToPlayerMap().get(targetPiece);
            game.getPlayerToPieceMap().get(rival).remove(targetPiece);
        }
        game.getPieceToCellMap().put(piece, targetCell);
        game.getCellToPieceMap().put(targetCell, piece);
        game.getCellToPieceMap().remove(currPosition, piece);
    }

    public boolean isTargetCellNotEmpty(Game game, Cell targetCell) {
        return game.getCellToPieceMap().get(targetCell) != null;
    }
}
