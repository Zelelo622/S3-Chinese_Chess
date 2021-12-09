package ru.vsu.common.services;

import ru.vsu.common.Main;
import ru.vsu.common.models.*;
import ru.vsu.common.models.enums.ColorEnum;
import ru.vsu.common.models.enums.Direction;
import ru.vsu.common.models.enums.PieceType;

import java.util.*;

public class GameService {

    public static final Integer BOARD_COL = 9;
    public static final Integer BOARD_ROW = 10;

    private final Map<PieceType, IPieceService> pieceToServiceMap;

    public GameService(Map<PieceType, IPieceService> pieceToServiceMap) {
        this.pieceToServiceMap = pieceToServiceMap;
    }

    public Game initGame() {
        Game game = new Game();
        Player firstPlayer = new Player("Игрок белый");
        Player secondPlayer = new Player("Игрок черный");
        List<List<Cell>> gameBoard = initBoard();
        initKingBorderCells(gameBoard, game);
        initRiverCells(gameBoard, game);
        initPieces(gameBoard, game, firstPlayer, secondPlayer);
        return game;
    }

    public void startGameProcess(Game game) {
        int i = 0;
        Queue<Player> players = new ArrayDeque<>();
        game.getPlayerToPieceMap().forEach((key, value) -> players.add(key));
        Set<Piece> pieces;
        do {
            Player currPlayer = players.poll();
            players.add(currPlayer);
            pieces = game.getPlayerToPieceMap().get(currPlayer);
            List<Piece> piecesList = new ArrayList<>(pieces);

            Random randomPiece = new Random();
            Random randomStep = new Random();
            Piece piece;
            List<Cell> possibleMoves;
            IPieceService pieceService;

            do {
                piece = piecesList.get(randomPiece.nextInt(piecesList.size()));
                pieceService = pieceToServiceMap.get(piece.getPieceType());
                possibleMoves = pieceService.getPossibleMoves(game, piece);
            } while(possibleMoves.size() == 0);
            pieceService.doMove(game, piece, possibleMoves.get(randomStep.nextInt(
                    possibleMoves.size())));
            i++;
        } while(isKingAlive(pieces));
    }

    public void printGameResult(Game currGame) {
        List<Step> gameSteps = currGame.getSteps();
        Step currStep;
        String currPlayer;
        String piece;
        String currCell;
        String targetCell;
        String killedPiece;
        for (int i = 0; i < currGame.getSteps().size(); i++) {
            currStep = gameSteps.get(i);
            currPlayer = currStep.getPlayer().getName();
            piece = currStep.getPiece().getPieceType().toString();
            currCell = currStep.getStartCell().getConsoleCoordinates();
            targetCell = currStep.getEndCell().getConsoleCoordinates();
            if(currStep.getKilledPiece() != null) {
                killedPiece = currStep.getKilledPiece().getPieceType().toString();
            } else {
                killedPiece = "Вражеских фигур не срублено";
            }
            System.out.println("Игрок: " + currPlayer + " ходит фигурой " + piece + " с ячейки " + currCell +
                    " на ячейку " + targetCell);
            System.out.println("В результате хода срублена фигура: " + killedPiece  + "\n");
        }
    }

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
            char column = 'a';
            for (int j = 0; j < BOARD_COL; j++, column++) {
                currCell = new Cell(Integer.toString(i) + Character.toString(column));
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

    public void initRiverCells(List<List<Cell>> board, Game game) {
        List<Cell> list = game.getRiverCells();
        list.addAll(board.get(4));
        list.addAll(board.get(5));
    }

    public void initKingBorderCells(List<List<Cell>> board, Game game) {
        List<Cell> list = game.getKingBorderCells();
        for (int i = 0; i < 3; i++) {
            for (int j = 3; j < 6; j++) {
                list.add(board.get(i).get(j));
            }
        }
        for (int i = 7; i < 10; i++) {
            for (int j = 3; j < 6; j++) {
                list.add(board.get(i).get(j));
            }
        }
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
        game.getPieceToCellMap().put(cannon, board.get(2).get(1));
        game.getCellToPieceMap().put(board.get(2).get(1), cannon);
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
        game.getPieceToCellMap().put(cannon, board.get(7).get(1));
        game.getCellToPieceMap().put(board.get(7).get(1), cannon);
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

    public static boolean isKingAlive(Set<Piece> pieces) {
        for (Piece piece: pieces) {
            if(piece.getPieceType().equals(PieceType.KING)) {
                return true;
            }
        }
        return false;
    }
}
