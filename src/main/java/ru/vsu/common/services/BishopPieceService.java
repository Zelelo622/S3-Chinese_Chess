package ru.vsu.common.services;

import ru.vsu.common.models.*;
import ru.vsu.common.models.enums.Direction;

import java.util.*;

public class BishopPieceService implements IPieceService {

    @Override
    public List<Cell> getPossibleMoves(Game game, Piece piece) {
        List<Direction> directions = Arrays.asList(Direction.NORTH_EAST, Direction.NORTH_WEST,
                Direction.SOUTH_EAST, Direction.SOUTH_WEST);
        return new ArrayList<>(findBishopSteps(game, piece, directions));
    }

    private List<Cell> findBishopSteps(Game game, Piece piece, List<Direction> directions) {
        List<Cell> possibleMoves = new ArrayList<>();
        Cell pieceCell = game.getPieceToCellMap().get(piece);
        Cell currCell;
        Cell nextCell;
        Direction direction;
        for (Direction value : directions) {
            direction = value;
            nextCell = pieceCell.getNeighbors().get(direction);
            for (int j = 0; j < 2; j++) {
                if (isMoveAvailable(game, piece, nextCell)) {
                    currCell = nextCell;
                    nextCell = currCell.getNeighbors().get(direction);
                    if (j == 1) {
                        possibleMoves.add(currCell);
                    }
                } else {
                    break;
                }
            }
        }
        return possibleMoves;
    }

    private boolean isMoveAvailable(Game game, Piece piece, Cell testedCell) {
        if(testedCell != null) {
            for (int i = 0; i < game.getRiverCells().size(); i++) {
                if (testedCell == game.getRiverCells().get(i)) {
                    return false;
                }
            }
            return ((game.getCellToPieceMap().get(testedCell) == null) ||
                    (game.getCellToPieceMap().get(testedCell) != null) &&
                            (game.getCellToPieceMap().get(testedCell).getPieceColor() != piece.getPieceColor()));
        }
        return false;
    }

    @Override
    public Step doMove(Game game, Piece piece, Cell finCell) {
        Step stepBishop = new Step();
        Cell currCell = game.getPieceToCellMap().get(piece);
        stepBishop.setPlayer(game.getPieceToPlayerMap().get(piece));
        stepBishop.setStartCell(currCell);
        stepBishop.setEndCell(finCell);
        stepBishop.setPiece(piece);
        if (isFinCellNotEmpty(game, finCell)) {
            stepBishop.setKilledPiece(game.getCellToPieceMap().get(finCell));
        }
        game.getSteps().add(stepBishop);
        changeOnBoardPlacement(game, piece, finCell, currCell);
        return stepBishop;
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
