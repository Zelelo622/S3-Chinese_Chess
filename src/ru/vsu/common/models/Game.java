package ru.vsu.common.models;

import java.util.*;

public class Game {

    private Map<Piece, Cell> pieceToCellMap = new LinkedHashMap<>();
    private Map<Cell, Piece> cellToPieceMap = new HashMap();
    private Map<Player, Set<Piece>> PlayerToPieceMap = new HashMap();
    private Map<Piece, Player> pieceToPlayerMap = new LinkedHashMap<>();
    private List<Step> steps = new ArrayList<>();
    private Queue<Player> players = new LinkedList<>();

    public Map<Piece, Cell> getPieceToCellMap() {
        return pieceToCellMap;
    }

    public void setPieceToCellMap(Map<Piece, Cell> pieceToCellMap) {
        this.pieceToCellMap = pieceToCellMap;
    }

    public Map<Cell, Piece> getCellToPieceMap() {
        return cellToPieceMap;
    }

    public void setCellToPieceMap(Map<Cell, Piece> cellToPieceMap) {
        this.cellToPieceMap = cellToPieceMap;
    }

    public Map<Player, Set<Piece>> getPlayerToPieceMap() {
        return PlayerToPieceMap;
    }

    public void setPlayerToPieceMap(Map<Player, Set<Piece>> playerToPieceMap) {
        PlayerToPieceMap = playerToPieceMap;
    }

    public Map<Piece, Player> getPieceToPlayerMap() {
        return pieceToPlayerMap;
    }

    public void setPieceToPlayerMap(Map<Piece, Player> pieceToPlayerMap) {
        this.pieceToPlayerMap = pieceToPlayerMap;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }

    public Queue<Player> getPlayers() {
        return players;
    }

    public void setPlayers(Queue<Player> players) {
        this.players = players;
    }
}
