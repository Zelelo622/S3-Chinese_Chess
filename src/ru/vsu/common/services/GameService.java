package ru.vsu.common.services;

import ru.vsu.common.models.Cell;
import ru.vsu.common.models.Game;
import ru.vsu.common.models.Piece;
import ru.vsu.common.models.Player;
import ru.vsu.common.models.enums.ColorEnum;
import ru.vsu.common.models.enums.Direction;
import ru.vsu.common.models.enums.PieceType;

import java.util.*;

public class GameService {

    public static final Integer BOARD_COL = 9;
    public static final Integer BOARD_ROW = 10;

    public List<List<Cell>> initBoard() {
        List<List<Cell>> graph = new ArrayList<>();

        Cell prevCell;
        Cell currCell;
        List<Cell> prevRow = null;
        for (int row = 0; row < BOARD_ROW; row++) {
            prevCell = null;
            List<Cell> currRow = new ArrayList<>();
            for (int col = 0; col < BOARD_COL; col++) {
                currCell = new Cell();
                if (prevCell != null) {
                    currCell.getNeighbors().put(Direction.WEST, prevCell);
                    prevCell.getNeighbors().put(Direction.EAST, currCell);
                }
                currRow.add(currCell);
                prevCell = currCell;
                if (prevRow != null) {
                    for (int k = 0; k < currRow.size(); k++) {
                        Cell currRowCell = currRow.get(k);
                        Cell prevRowCell = prevRow.get(k);
                        currRowCell.getNeighbors().put(Direction.NORTH, prevRowCell);
                        prevRowCell.getNeighbors().put(Direction.SOUTH, currRowCell);
                    }
                }
            }
            graph.add(currRow);
            prevRow = currRow;
        }
        initWizardsCells(graph);
        return graph;
    }

    public void initPieces(List<List<Cell>> board, Game game, Player firstPlayer, Player secondPlayer) {
        List<PieceType> pieces =
                Arrays.asList(PieceType.BISHOP, PieceType.KNIGHT, PieceType.ROOK, PieceType.GUARD);
        Set<Piece> firstPlayerPieces = new LinkedHashSet<>();
        Set<Piece> secondPlayerPieces = new LinkedHashSet<>();

        initWhitePawnsPieces(board, firstPlayerPieces, game, firstPlayer);
        initWhitePieces(board, pieces, firstPlayerPieces, game, firstPlayer);
        initBlackPawnsPieces(board, secondPlayerPieces, game, secondPlayer);
        initBlackPieces(board, pieces, secondPlayerPieces, game, secondPlayer);
    }

    private void initBlackPawnsPieces(
            List<List<Cell>> board,
            Set<Piece> playerPieces,
            Game game,
            Player firstPlayer
    ) {

        for (int i = 0; i < BOARD_COL; i += 2) {
            Piece pawn = new Piece(PieceType.PAWN, ColorEnum.BLACK);
            game.getPieceToCellMap().put(pawn, board.get(3).get(i));
            game.getCellToPieceMap().put(board.get(3).get(i), pawn);
            playerPieces.add(pawn);
            game.getPlayerToPieceMap().put(firstPlayer, playerPieces);
            game.getPieceToPlayerMap().put(pawn, firstPlayer);
        }

//        for (int i = 0; i < BOARD_COL; i += 2) {
//            game.getPieceToCellMap().put(new Piece(PieceType.PAWN, ColorEnum.WHITE), board.get(3).get(i));
//            game.getCellToPieceMap().put(board.get(3).get(i), new Piece(PieceType.PAWN, ColorEnum.WHITE));
//            playerPieces.add(new Piece(PieceType.PAWN, ColorEnum.WHITE));
//            game.getPlayerToPieceMap().put(firstPlayer, playerPieces);
//            game.getPieceToPlayerMap().put(new Piece(PieceType.PAWN, ColorEnum.WHITE), firstPlayer);
//        }
    }

    private void initWhitePieces(
            List<List<Cell>> board,
            List<PieceType> pieces,
            Set<Piece> playerPieces,
            Game game,
            Player firstPlayer
    ) {
        initLeftWhitePieces(board, pieces, playerPieces, game, firstPlayer);
        initRightWhitePieces(board, pieces, playerPieces, game, firstPlayer);
    }

    private void initWhitePawnsPieces(
            List<List<Cell>> board,
            Set<Piece> playerPieces,
            Game game,
            Player secondPlayer
    ) {
        for (int i = 0; i < BOARD_COL; i += 2) {
            Piece pawn = new Piece(PieceType.PAWN, ColorEnum.WHITE);
            game.getPieceToCellMap().put(pawn, board.get(6).get(i));
            game.getCellToPieceMap().put(board.get(6).get(i), pawn);
            playerPieces.add(pawn);
            game.getPlayerToPieceMap().put(secondPlayer, playerPieces);
            game.getPieceToPlayerMap().put(pawn, secondPlayer);
        }
    }

    private void initBlackPieces(
            List<List<Cell>> board,
            List<PieceType> pieces,
            Set<Piece> playerPieces,
            Game game,
            Player secondPlayer
    ) {
        initLeftBlackPieces(board, pieces, playerPieces, game, secondPlayer);
        initRightBlackPieces(board, pieces, playerPieces, game, secondPlayer);
    }

    private void initLeftBlackPieces(
            List<List<Cell>> board,
            List<PieceType> pieces,
            Set<Piece> playerPieces,
            Game game,
            Player player
    ) {
        Piece cannon = new Piece(PieceType.CANNON, ColorEnum.BLACK);
        game.getPieceToCellMap().put(cannon, board.get(2).get(2));
        game.getCellToPieceMap().put(board.get(2).get(2), cannon);
        playerPieces.add(cannon);
        game.getPlayerToPieceMap().put(player, playerPieces);
        game.getPieceToPlayerMap().put(cannon, player);
        for (int i = 0; i < (BOARD_COL - 1) / 2; i++) {
            Piece piece = new Piece(pieces.get(i), ColorEnum.BLACK);
            game.getPieceToCellMap().put(piece, board.get(0).get(i));
            game.getCellToPieceMap().put(board.get(0).get(i), piece);
            playerPieces.add(piece);
            game.getPlayerToPieceMap().put(player, playerPieces);
            game.getPieceToPlayerMap().put(piece, player);
        }
    }

    private void initRightBlackPieces(
            List<List<Cell>> board,
            List<PieceType> pieces,
            Set<Piece> playerPieces,
            Game game,
            Player player
    ) {
        Piece cannon = new Piece(PieceType.CANNON, ColorEnum.BLACK);
        game.getPieceToCellMap().put(cannon, board.get(2).get(7));
        game.getCellToPieceMap().put(board.get(2).get(7), cannon);
        playerPieces.add(cannon);
        game.getPlayerToPieceMap().put(player, playerPieces);
        game.getPieceToPlayerMap().put(cannon, player);

        Piece king = new Piece(PieceType.KING, ColorEnum.BLACK);
        game.getPieceToCellMap().put(king, board.get(0).get(4));
        game.getCellToPieceMap().put(board.get(0).get(4), king);
        playerPieces.add(king);
        game.getPlayerToPieceMap().put(player, playerPieces);
        game.getPieceToPlayerMap().put(king, player);

        for (int i = BOARD_COL - 1, k = 0; i > (BOARD_COL - 1) / 2 && k < pieces.size(); i--, k++) {
            Piece piece = new Piece(pieces.get(k), ColorEnum.BLACK);
            game.getPieceToCellMap().put(piece, board.get(0).get(i));
            game.getCellToPieceMap().put(board.get(0).get(i), piece);
            playerPieces.add(piece);
            game.getPlayerToPieceMap().put(player, playerPieces);
            game.getPieceToPlayerMap().put(piece, player);
        }
    }

    private void initLeftWhitePieces(
            List<List<Cell>> board,
            List<PieceType> pieces,
            Set<Piece> playerPieces,
            Game game,
            Player player
    ) {
        Piece cannon = new Piece(PieceType.CANNON, ColorEnum.WHITE);
        game.getPieceToCellMap().put(cannon, board.get(7).get(2));
        game.getCellToPieceMap().put(board.get(7).get(2), cannon);
        playerPieces.add(cannon);
        game.getPlayerToPieceMap().put(player, playerPieces);
        game.getPieceToPlayerMap().put(cannon, player);
        for (int i = 0; i < (BOARD_COL - 1) / 2; i++) {
            Piece piece = new Piece(pieces.get(i), ColorEnum.WHITE);
            game.getPieceToCellMap().put(piece, board.get(BOARD_COL).get(i));
            game.getCellToPieceMap().put(board.get(BOARD_COL).get(i), piece);
            playerPieces.add(piece);
            game.getPlayerToPieceMap().put(player, playerPieces);
            game.getPieceToPlayerMap().put(piece, player);
        }
    }

    private void initRightWhitePieces(
            List<List<Cell>> board,
            List<PieceType> pieces,
            Set<Piece> playerPieces,
            Game game,
            Player player
    ) {
        Piece cannon = new Piece(PieceType.CANNON, ColorEnum.WHITE);
        game.getPieceToCellMap().put(cannon, board.get(7).get(7));
        game.getCellToPieceMap().put(board.get(7).get(7), cannon);
        playerPieces.add(cannon);
        game.getPlayerToPieceMap().put(player, playerPieces);
        game.getPieceToPlayerMap().put(cannon, player);

        Piece king = new Piece(PieceType.KING, ColorEnum.WHITE);
        game.getPieceToCellMap().put(king, board.get(9).get(4));
        game.getCellToPieceMap().put(board.get(9).get(4), king);
        playerPieces.add(king);
        game.getPlayerToPieceMap().put(player, playerPieces);
        game.getPieceToPlayerMap().put(king, player);

        for (int i = BOARD_COL - 1, k = 0; i > (BOARD_COL - 1) / 2 && k < pieces.size(); i--, k++) {
            Piece piece = new Piece(pieces.get(k), ColorEnum.WHITE);
            game.getPieceToCellMap().put(piece, board.get(BOARD_COL).get(i));
            game.getCellToPieceMap().put(board.get(BOARD_COL).get(i), piece);
            playerPieces.add(piece);
            game.getPlayerToPieceMap().put(player, playerPieces);
            game.getPieceToPlayerMap().put(piece, player);
        }
    }

    private void initWizardsCells(List<List<Cell>> board) {
        Cell northWestCell = board.get(0).get(0);
        Cell northEastCell = board.get(0).get(board.size() - 2);
        Cell southWestCell = board.get(board.size() - 1).get(0);
        Cell southEastCell = board.get(board.size() - 1).get(board.size() - 2);


        northWestCell.getNeighbors().put(Direction.NORTH_WEST, new Cell());
        northWestCell.getNeighbors().get(Direction.NORTH_WEST).getNeighbors().
                put(Direction.SOUTH_EAST, northWestCell);

        northEastCell.getNeighbors().put(Direction.NORTH_EAST, new Cell());
        northEastCell.getNeighbors().get(Direction.NORTH_EAST).getNeighbors().
                put(Direction.SOUTH_WEST, northEastCell);

        southWestCell.getNeighbors().put(Direction.SOUTH_WEST, new Cell());
        southWestCell.getNeighbors().get(Direction.SOUTH_WEST).getNeighbors().
                put(Direction.NORTH_EAST, southWestCell);

        southEastCell.getNeighbors().put(Direction.SOUTH_EAST, new Cell());
        southEastCell.getNeighbors().get(Direction.SOUTH_EAST).getNeighbors().
                put(Direction.NORTH_WEST, southEastCell);
    }
}
