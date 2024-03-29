package ru.vsu.common;

import ru.vsu.common.models.Cell;
import ru.vsu.common.models.Game;
import ru.vsu.common.models.Piece;
import ru.vsu.common.models.Player;
import ru.vsu.common.models.enums.PieceType;
import ru.vsu.common.services.*;

import java.util.*;

public class Main {

    public static void main(String[] args) {
//        Game game = new Game();
//        Player player1 = new Player("Игрок Белый");
//        Player player2 = new Player("Игрок Черный");
//        GameService gs = new GameService();
//        List<List<Cell>> board = gs.initBoard();
//        gs.initRiverCells(board, game);
//        gs.initKingBorderCells(board, game);
//        gs.initPieces(board, game, player1, player2);
//        PawnPieceService pawnPieceService = new PawnPieceService();
//        KnightPieceService knightPieceService = new KnightPieceService();
//        BishopPieceService bishopPieceService = new BishopPieceService();
//        RookPieceService rookPieceService = new RookPieceService();
//        CannonPieceService cannonPieceService = new CannonPieceService();
//        KingPieceService kingPieceService = new KingPieceService();
//        GuardPieceService guardPieceService = new GuardPieceService();
//        Set<Piece> pieces = game.getPlayerToPieceMap().get(player2);
//        List<Piece> pieces1 = new ArrayList<>(pieces);
//        List<Cell> testList = cannonPieceService.getPossibleMoves(game, pieces1.get(5));

        Map<PieceType, IPieceService> pieceToServiceMap = new HashMap<>();
        pieceToServiceMap.put(PieceType.PAWN, new PawnPieceService());
        pieceToServiceMap.put(PieceType.ROOK, new RookPieceService());
        pieceToServiceMap.put(PieceType.KNIGHT, new KnightPieceService());
        pieceToServiceMap.put(PieceType.BISHOP, new BishopPieceService());
        pieceToServiceMap.put(PieceType.CANNON, new CannonPieceService());
        pieceToServiceMap.put(PieceType.KING, new KingPieceService());
        pieceToServiceMap.put(PieceType.GUARD, new GuardPieceService());
        GameService gameService = new GameService(pieceToServiceMap);
        Game game = gameService.initGame();
        gameService.startGameProcess(game);
        gameService.printGameResult(game);
    }
}
