package ru.vsu.common.models;


import ru.vsu.common.models.enums.ColorEnum;
import ru.vsu.common.models.enums.PieceType;

public class Piece {

    private PieceType pieceType;
    private ColorEnum colorEnum;

    public Piece(PieceType pieceType, ColorEnum colorEnum) {
        this.pieceType = pieceType;
        this.colorEnum = colorEnum;
    }

    public PieceType getPieceType() {
        return pieceType;
    }

    public ColorEnum getPieceColor() {
        return colorEnum;
    }
}
