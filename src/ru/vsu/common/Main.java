package ru.vsu.common;

import ru.vsu.common.models.Cell;
import ru.vsu.common.models.Game;
import ru.vsu.common.models.Piece;
import ru.vsu.common.models.Player;
import ru.vsu.common.services.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Main {

    public static void main(String[] args) {
        Game game = new Game();
        Player player1 = new Player("Василий");
        Player player2 = new Player("Геннадий");
        GameService gs = new GameService();
        List<List<Cell>> board = gs.initBoard();
        gs.initRiverCells(board, game);
        gs.initKingBorderCells(board, game);
        gs.initPieces(board, game, player1, player2);
        KingPieceService rookPieceService = new KingPieceService();
        Set<Piece> pieces = game.getPlayerToPieceMap().get(player2);
        List<Piece> pieces1 = new ArrayList<>(pieces);
        List<Cell> testList = rookPieceService.getPossibleMoves(game, pieces1.get(11));
    }

    private static void printBoard(List<List<Cell>> board, Game game) {
        for (int i = 0; i < board.size(); i++) {
            for (int j = 0; j < board.size(); j++) {
                if (game.getCellToPieceMap().get(board.get(i).get(j)).getPieceType() != null) {
                    System.out.println(i + " " + j + " " + "фигура - " + game.getCellToPieceMap().get(board.get(i).get(j)).getPieceType());
                } else {
                    System.out.println(i + " " + j);
                }
            }
        }
    }
}
