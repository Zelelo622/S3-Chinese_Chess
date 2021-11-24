package ru.vsu.common;

import ru.vsu.common.models.Cell;
import ru.vsu.common.models.Game;
import ru.vsu.common.models.Piece;
import ru.vsu.common.models.Player;
import ru.vsu.common.services.GameService;
import ru.vsu.common.services.PawnPieceService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Main {

//    public static void main(String[] args) {
////        GameService gameService = new GameService();
//////        for (int i = 0; i < gameService.initBoard().size(); i++) {
//////            System.out.println(gameService.initBoard().remove(i));
//////        }
////        Game game = new Game();
////        Player player1 = new Player("1");
////        Player player2 = new Player("2");
////        List<List<Cell>> board = gameService.initBoard();
////        gameService.initBlackPawns(board, game, player1);
////        gameService.initWhitePawns(board, game, player2);
//
//        Game game = new Game();
//        Player player1 = new Player("Василий");
//        Player player2 = new Player("Геннадий");
//        GameService gs = new GameService();
//        List<List<Cell>> board = gs.initBoard();
//        gs.initPieces(board, game, player1, player2);
//
////        gs.initWhitePawns(board, game, player1);
////        gs.initBlackPawns(board, game, player2);
//    }

    public static void main(String[] args) {
        Game game = new Game();
        Player player1 = new Player("Василий");
        Player player2 = new Player("Геннадий");
        GameService gs = new GameService();
        List<List<Cell>> board = gs.initBoard();
        gs.initPieces(board, game, player1, player2);
        PawnPieceService pawnPieceService = new PawnPieceService();
        Set<Piece> pieces = game.getPlayerToPieceMap().get(player1);
        List<Piece> pieces1 = new ArrayList<>(pieces);
//        Cell testCell = game.getPieceToCellMap().get(pieces1.get(0));
        Cell testCell = game.getPieceToCellMap().get(pieces1.get(0));
        pawnPieceService.getPossibleMoves(game, pieces1.get(0));
    }
}
