package ru.vsu.common.services;

import ru.vsu.common.models.*;
import ru.vsu.common.models.enums.Direction;

import java.util.*;

public class KingPieceService implements IPieceService {

    @Override
    public List<Cell> getPossibleMoves(Game game, Piece piece) {
        List<Direction> directions = Arrays.asList(Direction.NORTH, Direction.EAST, Direction.WEST, Direction.SOUTH, Direction.NORTH_EAST, Direction.NORTH_WEST,
                Direction.SOUTH_EAST, Direction.SOUTH_WEST);
        return new ArrayList<>(findKingStep(game, piece, directions));
    }

    private List<Cell> findKingStep(Game game, Piece piece, List<Direction> directions) {
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

    @Override
    public Step doMove(Game game, Piece piece, Cell targetCell) {
        Step kingStep = new Step();
        Cell currPosition = game.getPieceToCellMap().get(piece);

        kingStep.setPlayer(game.getPieceToPlayerMap().get(piece));
        kingStep.setStartCell(currPosition);
        kingStep.setEndCell(targetCell);
        kingStep.setPiece(piece);
        if(isTargetCellNotEmpty(game, targetCell)) {
            kingStep.setKilledPiece(game.getCellToPieceMap().get(targetCell));
        }
        game.getSteps().add(kingStep);
        changeOnBoardPlacement(game, piece, targetCell, currPosition);
        return kingStep;
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
