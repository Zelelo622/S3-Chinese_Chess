package ru.vsu.common.models.enums;

public enum PieceType {

    ROOK("R") {
    }, //ладья

    KNIGHT("N") {
    }, //конь

    BISHOP("B") {
    }, //слон

    GUARD("G") {
    }, //стража

    KING("K") {
    }, //король

    CANNON("C") {
    }, //пушка

    PAWN("P") {
    }; //пешка


    private final String pieceName;

    PieceType(String pieceName) {
        this.pieceName = pieceName;
    }

    @Override
    public String toString() {
        return this.pieceName;
    }
}
