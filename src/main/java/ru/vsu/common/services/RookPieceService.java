package ru.vsu.common.services;

import ru.vsu.common.models.*;
import ru.vsu.common.models.enums.Direction;

import java.util.*;

public class RookPieceService implements IPieceService {

    @Override
    public List<Cell> getPossibleMoves(Game game, Piece piece) {
        List<Direction> directions = Arrays.asList(Direction.NORTH, Direction.EAST, Direction.WEST, Direction.SOUTH);
        return new ArrayList<>(findRookStep(game, piece, directions));
    }

    private List<Cell> findRookStep(Game game, Piece piece, List<Direction> directions) {
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
                if (isMoveAvailable(game, piece, currCell) && stopsAfterKill(game, piece, currCell)) {
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
        Step stepRook = new Step();
        Cell currCell = game.getPieceToCellMap().get(piece);
        stepRook.setPlayer(game.getPieceToPlayerMap().get(piece));
        stepRook.setStartCell(currCell);
        stepRook.setEndCell(finCell);
        stepRook.setPiece(piece);
        if (isFinCellNotEmpty(game, finCell)) {
            stepRook.setKilledPiece(game.getCellToPieceMap().get(finCell));
        }
        game.getSteps().add(stepRook);
        changeOnBoardPlacement(game, piece, finCell, currCell);
        return stepRook;
    }

    private boolean isFinCellNotEmpty(Game game, Cell finCell) {
        return game.getCellToPieceMap().get(finCell) != null;
    }

    private void changeOnBoardPlacement(Game game, Piece piece, Cell finCell, Cell currCell) {
        Player rival;
        Piece targetPiece;
        game.getPieceToCellMap().replace(piece, finCell);
        game.getCellToPieceMap().put(finCell, piece);
        game.getCellToPieceMap().remove(currCell, piece);
        if (isFinCellNotEmpty(game, finCell)) {
            targetPiece = game.getCellToPieceMap().get(finCell);
            rival = game.getPieceToPlayerMap().get(targetPiece);
            game.getPlayerToPieceMap().get(rival).remove(targetPiece);
        }
    }
}
