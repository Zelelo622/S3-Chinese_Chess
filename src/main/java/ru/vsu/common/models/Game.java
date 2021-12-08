package ru.vsu.common.models;

import java.util.*;

public class Game {

    private Map<Piece, Cell> pieceToCellMap = new LinkedHashMap<>();
    private Map<Cell, Piece> cellToPieceMap = new HashMap();
    private Map<Player, Set<Piece>> playerToPieceMap = new HashMap();
    private Map<Piece, Player> pieceToPlayerMap = new LinkedHashMap<>();
    private List<Step> steps = new ArrayList<>();
    private Queue<Player> players = new LinkedList<>();
    private List<Cell> riverCells = new ArrayList<>();
    private List<Cell> kingBorderCells = new ArrayList<>();

    private Map<String, Cell> number2CellMap;
    private Map<Cell, String> cellToNumberMap;

    public Map<String, Cell> getNumber2CellMap() {
        return number2CellMap;
    }

    public void setNumber2CellMap(Map<String, Cell> number2CellMap) {
        this.number2CellMap = number2CellMap;
    }

    public Map<Cell, String> getCellToNumberMap() {
        return cellToNumberMap;
    }

    public void setCellToNumberMap(Map<Cell, String> cellToNumberMap) {
        this.cellToNumberMap = cellToNumberMap;
    }

    public List<Cell> getKingBorderCells() {
        return kingBorderCells;
    }

    public void setKingBorderCells(List<Cell> kingBorderCells) {
        this.kingBorderCells = kingBorderCells;
    }

    public List<Cell> getRiverCells() {
        return riverCells;
    }

    public void setRiverCells(List<Cell> riverCells) {
        this.riverCells = riverCells;
    }

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
        return playerToPieceMap;
    }

    public void setPlayerToPieceMap(Map<Player, Set<Piece>> playerToPieceMap) {
        this.playerToPieceMap = playerToPieceMap;
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
