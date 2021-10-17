package ru.vsu.common.services;

import ru.vsu.common.models.Cell;
import ru.vsu.common.models.Game;
import ru.vsu.common.models.Piece;
import ru.vsu.common.models.Step;

import java.util.List;

public interface IPieceService {

    public List<Cell> getPossibleMoves(Game game, Piece piece);

    public Step makeMove();
}
