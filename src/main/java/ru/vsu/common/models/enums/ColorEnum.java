package ru.vsu.common.models.enums;

public enum ColorEnum {

    BLACK {
        @Override
        public boolean isBlack() {
            return true;
        }

        @Override
        public boolean isWhite() {
            return false;
        }
    },

    WHITE {
        @Override
        public boolean isBlack() {
            return false;
        }

        @Override
        public boolean isWhite() {
            return true;
        }
    };

    public abstract boolean isBlack();
    public abstract boolean isWhite();
}
