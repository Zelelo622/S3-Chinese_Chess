package ru.vsu.common.services;


import ru.vsu.common.models.*;
import ru.vsu.common.models.enums.Direction;

import java.util.*;

public class CannonPieceService implements IPieceService {

    @Override
    public List<Cell> getPossibleMoves(Game game, Piece piece) {
        List<Cell> possibleMoves = new ArrayList<>();
        Set<Cell> beatMoves = new LinkedHashSet<>();
        List<Direction> directions = Arrays.asList(Direction.NORTH, Direction.EAST, Direction.WEST, Direction.SOUTH);
        possibleMoves.addAll(findCannonStep(game, piece, directions));
        return possibleMoves;
    }

    @Override
    public Step doMove(Game game, Piece piece, Cell finCell) {
        Step stepCannon = new Step();
        Cell currCell = game.getPieceToCellMap().get(piece);
        stepCannon.setPlayer(game.getPieceToPlayerMap().get(piece));
        stepCannon.setStartCell(currCell);
        stepCannon.setEndCell(finCell);
        stepCannon.setPiece(piece);
        if (isFinCellNotEmpty(game, finCell)) {
            stepCannon.setKilledPiece(game.getCellToPieceMap().get(finCell));
        }
        game.getSteps().add(stepCannon);
        changeOnBoardPlacement(game, piece, finCell, currCell);
        return stepCannon;
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
}
