package ru.vsu.common.services;

import ru.vsu.common.models.Cell;
import ru.vsu.common.models.Game;
import ru.vsu.common.models.Piece;
import ru.vsu.common.models.Step;
import ru.vsu.common.models.enums.Direction;

import java.util.*;

public class KingPieceService implements IPieceService {

    @Override
    public List<Cell> getPossibleMoves(Game game, Piece piece) {
        List<Cell> possibleMoves = new ArrayList<>();
        Set<Cell> beatMoves = new LinkedHashSet<>();
        List<Direction> directions = Arrays.asList(Direction.NORTH, Direction.EAST, Direction.WEST, Direction.SOUTH, Direction.NORTH_EAST, Direction.NORTH_WEST,
                Direction.SOUTH_EAST, Direction.SOUTH_WEST);
        possibleMoves.addAll(findKingStep(game, piece, directions));
        return possibleMoves;
    }

    @Override
    public Step doMove(Game game, Piece piece, Cell cell) {
        return null;
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
}
