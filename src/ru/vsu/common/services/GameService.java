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
        Cell leftDiagonalCell;
        Cell rightDiagonalCell;
        List<Cell> prevRow = null;
        for (int i = 0; i < BOARD_ROW; i++) {
            prevCell = null;
            List<Cell> currRow = new ArrayList<>();
            for (int j = 0; j < BOARD_COL; j++) {
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
                        if(k > 0) {
                            leftDiagonalCell = prevRow.get(k - 1);
                            currRowCell.getNeighbors().put(Direction.NORTH_WEST, leftDiagonalCell);
                            leftDiagonalCell.getNeighbors().put(Direction.SOUTH_EAST, currRowCell);
                        }
                        if(k < currRow.size() - 1) {
                            rightDiagonalCell = prevRow.get(k + 1);
                            currRowCell.getNeighbors().put(Direction.NORTH_EAST, rightDiagonalCell);
                            rightDiagonalCell.getNeighbors().put(Direction.SOUTH_WEST, currRowCell);
                        }
                    }
                }
            }
            graph.add(currRow);
            prevRow = currRow;
        }
        return graph;
    }

    public void initBorderCells(List<List<Cell>> board, Game game ) {
        List<Cell> list = game.getBorderCells();
        list.addAll(board.get(4));
        list.addAll(board.get(5));
    }

    public void initPieces(List<List<Cell>> board, Game game, Player firstPlayer, Player secondPlayer) {
        List<PieceType> pieces =
                Arrays.asList(PieceType.ROOK, PieceType.KNIGHT, PieceType.BISHOP, PieceType.GUARD);
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

        /**/
        Piece rook = new Piece(PieceType.ROOK, ColorEnum.BLACK);
        game.getPieceToCellMap().put(rook, board.get(4).get(2));
        game.getCellToPieceMap().put(board.get(4).get(2), rook);
        playerPieces.add(rook);
        game.getPlayerToPieceMap().put(player, playerPieces);
        game.getPieceToPlayerMap().put(rook, player);
        /**/
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
}
