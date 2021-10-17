package ru.vsu.common.models.enums;

public enum PieceType {

    ROOK(" R") {
        @Override
        public boolean isKing() {
            return false;
        }
    }, //ладья

    KNIGHT(" N") {
        @Override
        public boolean isKing() {
            return false;
        }
    }, //конь

    BISHOP(" B") {
        @Override
        public boolean isKing() {
            return false;
        }
    }, //слон

    GUARD(" G") {
        @Override
        public boolean isKing() {
            return false;
        }
    }, //стража

    KING(" K") {
        @Override
        public boolean isKing() {
            return true;
        }
    }, //король

    CANNON(" C") {
        @Override
        public boolean isKing() {
            return false;
        }
    }, //пушка

    PAWN(" P") {
        @Override
        public boolean isKing() {
            return false;
        }
    }; //пешка


    private final String pieceName;

    PieceType(String pieceName) {
        this.pieceName = pieceName;
    }

    @Override
    public String toString() {
        return this.pieceName;
    }

    public abstract boolean isKing();
}
