package ru.vsu.common.services;

import ru.vsu.common.models.*;
import ru.vsu.common.models.enums.Direction;

import java.util.*;

public class GuardPieceService implements IPieceService {

    @Override
    public List<Cell> getPossibleMoves(Game game, Piece piece) {
        List<Cell> possibleMoves = new ArrayList<>();
        Set<Cell> beatMoves = new LinkedHashSet<>();
        List<Direction> directions = Arrays.asList(Direction.NORTH, Direction.EAST, Direction.WEST, Direction.SOUTH);
        possibleMoves.addAll(findGuardStep(game, piece, directions));
        return possibleMoves;
    }

    @Override
    public Step doMove(Game game, Piece piece, Cell finCell) {
        Step stepGuard = new Step();
        Cell currCell = game.getPieceToCellMap().get(piece);
        stepGuard.setPlayer(game.getPieceToPlayerMap().get(piece));
        stepGuard.setStartCell(currCell);
        stepGuard.setEndCell(finCell);
        stepGuard.setPiece(piece);
        if (isFinCellNotEmpty(game, finCell)) {
            stepGuard.setKilledPiece(game.getCellToPieceMap().get(finCell));
        }
        game.getSteps().add(stepGuard);
        changeOnBoardPlacement(game, piece, finCell, currCell);
        return stepGuard;
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

    private List<Cell> findGuardStep(Game game, Piece piece, List<Direction> directions) {
        List<Cell> possibleMoves = new ArrayList<>();
        Cell pieceCell = game.getPieceToCellMap().get(piece);
        Cell nextCell;
        Direction direction;
        for (Direction value : directions) {
            direction = value;
            nextCell = pieceCell.getNeighbors().get(direction);
            if (isMoveAvailable(game, piece, nextCell)) {
                possibleMoves.add(nextCell);
            }
        }
        return possibleMoves;
    }

    private boolean isMoveAvailable(Game game, Piece piece, Cell testedCell) {
        if(testedCell != null) {
            for (int i = 0; i < game.getKingBorderCells().size(); i++) {
                if (testedCell == game.getKingBorderCells().get(i)) {
                    return ((game.getCellToPieceMap().get(testedCell) == null) ||
                            (game.getCellToPieceMap().get(testedCell) != null) &&
                                    (game.getCellToPieceMap().get(testedCell).getPieceColor() != piece.getPieceColor()));
                }
            }
        }
        return false;
    }


    private boolean stopsAfterKill(Game game, Piece piece, Cell testedCell) {
        return ((game.getCellToPieceMap().get(testedCell) != null) &&
                (game.getCellToPieceMap().get(testedCell).getPieceColor() != piece.getPieceColor()));
    }
}
