package ru.vsu.common.services;


import ru.vsu.common.models.*;
import ru.vsu.common.models.enums.Direction;

import java.util.*;

public class CannonPieceService implements IPieceService {

    @Override
    public List<Cell> getPossibleMoves(Game game, Piece piece) {
        List<Direction> directions = Arrays.asList(Direction.NORTH, Direction.EAST, Direction.WEST, Direction.SOUTH);
        List<Cell> pos = new ArrayList<>(findCannonStep(game, piece, directions));
        return pos;
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
                possibleMoves.add(nextCell);
                currCell = nextCell;
                nextCell = currCell.getNeighbors().get(direction);
                if (isMoveAvailable(game, piece, currCell) && stopsAfterKill(game, piece, currCell) && nextCell != null) {
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
    public Step doMove(Game game, Piece piece, Cell targetCell) {
        Step cannonStep = new Step();
        Cell currPosition = game.getPieceToCellMap().get(piece);

        cannonStep.setPlayer(game.getPieceToPlayerMap().get(piece));
        cannonStep.setStartCell(currPosition);
        cannonStep.setEndCell(targetCell);
        cannonStep.setPiece(piece);
        if(isTargetCellNotEmpty(game, targetCell)) {
            cannonStep.setKilledPiece(game.getCellToPieceMap().get(targetCell));
        }
        game.getSteps().add(cannonStep);
        changeOnBoardPlacement(game, piece, targetCell, currPosition);
        return cannonStep;
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
