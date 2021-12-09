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
        Player player1 = new Player("Игрок Белый");
        Player player2 = new Player("Игрок Черный");
        GameService gs = new GameService();
        List<List<Cell>> board = gs.initBoard();
        gs.initRiverCells(board, game);
        gs.initKingBorderCells(board, game);
        gs.initPieces(board, game, player1, player2);
        PawnPieceService pawnPieceService = new PawnPieceService();
        KnightPieceService knightPieceService = new KnightPieceService();
        BishopPieceService bishopPieceService = new BishopPieceService();
        RookPieceService rookPieceService = new RookPieceService();
        CannonPieceService cannonPieceService = new CannonPieceService();
        KingPieceService kingPieceService = new KingPieceService();
        GuardPieceService guardPieceService = new GuardPieceService();
        Set<Piece> pieces = game.getPlayerToPieceMap().get(player2);
        List<Piece> pieces1 = new ArrayList<>(pieces);
        List<Cell> testList = pawnPieceService.getPossibleMoves(game, pieces1.get(0));
    }
}
