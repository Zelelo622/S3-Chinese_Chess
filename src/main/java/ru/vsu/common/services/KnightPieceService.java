package ru.vsu.common.services;

import ru.vsu.common.models.*;
import ru.vsu.common.models.enums.Direction;

import java.util.*;

public class KnightPieceService implements IPieceService {

    @Override
    public List<Cell> getPossibleMoves(Game game, Piece piece) {
        List<Direction> directionEnumList = Arrays.asList(Direction.NORTH, Direction.EAST, Direction.SOUTH,
                Direction.WEST);
        return new ArrayList<>(findKnightMoves(game, piece, directionEnumList));
    }

    private List<Cell> findKnightMoves(Game game, Piece piece, List<Direction> directionsList) {
        List<Cell> possibleMoves = new ArrayList<>();
        Direction dir;
        Cell receivedCell = game.getPieceToCellMap().get(piece);
        Cell currentCell;
        Cell nextCell;
        Cell tempCell;

        for (int i = 0; i < directionsList.size(); i++) {
            dir = directionsList.get(i);
            currentCell = receivedCell;
            nextCell = currentCell.getNeighbors().get(dir);
            if (nextCell != null) {
                currentCell = nextCell;
                nextCell = currentCell.getNeighbors().get(dir);
                if (nextCell != null) {
                    currentCell = nextCell;
                    for (int j = 0, k = 1; j < directionsList.size(); j += 2, k += 2) {
                        if (i % 2 == 0) {
                            dir = directionsList.get(k);
                        } else {
                            dir = directionsList.get(j);
                        }
                        tempCell = currentCell.getNeighbors().get(dir);
                        if (isMoveAvailable(game, piece, tempCell)) {
                            possibleMoves.add(tempCell);
                        }
                    }
                }
            }
        }
        return possibleMoves;
    }

    private boolean isMoveAvailable(Game game, Piece piece, Cell testedCell) {
        if (testedCell != null) {
            return ((game.getCellToPieceMap().get(testedCell) == null) ||
                    ((game.getCellToPieceMap().get(testedCell) != null) &&
                            (game.getCellToPieceMap().get(testedCell).getPieceColor() != piece.getPieceColor())));
        }
        return false;
    }

    @Override
    public Step doMove(Game game, Piece piece, Cell finCell) {
        Step stepKnight = new Step();
        Cell currCell = game.getPieceToCellMap().get(piece);
        stepKnight.setPlayer(game.getPieceToPlayerMap().get(piece));
        stepKnight.setStartCell(currCell);
        stepKnight.setEndCell(finCell);
        stepKnight.setPiece(piece);
        if (isFinCellNotEmpty(game, finCell)) {
            stepKnight.setKilledPiece(game.getCellToPieceMap().get(finCell));
        }
        game.getSteps().add(stepKnight);
        changeOnBoardPlacement(game, piece, finCell, currCell);
        return stepKnight;
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
