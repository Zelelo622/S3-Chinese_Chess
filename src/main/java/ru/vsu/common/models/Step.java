package ru.vsu.common.models;

public class Step {

    private Player player;
    private Cell startCell;
    private Cell endCell;
    private Piece piece;
    private Piece killedPiece;

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Cell getStartCell() {
        return startCell;
    }

    public void setStartCell(Cell startCell) {
        this.startCell = startCell;
    }

    public Cell getEndCell() {
        return endCell;
    }

    public void setEndCell(Cell endCell) {
        this.endCell = endCell;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public Piece getKilledPiece() {
        return killedPiece;
    }

    public void setKilledPiece(Piece killedPiece) {
        this.killedPiece = killedPiece;
    }
}
