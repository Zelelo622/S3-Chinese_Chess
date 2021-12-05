package ru.vsu.common.services;

import ru.vsu.common.models.Cell;
import ru.vsu.common.models.Game;
import ru.vsu.common.models.Piece;
import ru.vsu.common.models.enums.ColorEnum;
import ru.vsu.common.models.enums.Direction;

public class PieceServiceUtil {

    public static Direction getDirection(Piece piece) {
        if (piece.getPieceColor() == ColorEnum.BLACK) {
            return Direction.SOUTH;
        } else {
            return Direction.NORTH;
        }
    }

    public static boolean isMoveAvailable(Game game, Piece piece, Cell testedCell) {
        if (testedCell != null) {
            return ((game.getCellToPieceMap().get(testedCell) == null) ||
                    ((game.getCellToPieceMap().get(testedCell) != null) &&
                            (game.getCellToPieceMap().get(testedCell).getPieceColor() != piece.getPieceColor())));
        }
        return false;
    }
}
