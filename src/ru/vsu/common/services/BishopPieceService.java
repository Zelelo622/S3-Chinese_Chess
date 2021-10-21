package ru.vsu.common.services;

import ru.vsu.common.models.Cell;
import ru.vsu.common.models.Game;
import ru.vsu.common.models.Piece;
import ru.vsu.common.models.Step;

import java.util.List;

public class BishopPieceService implements IPieceService {

    @Override
    public List<Cell> getPossibleMoves(Game game, Piece piece) {
        return null;
    }

    @Override
    public Step doMove() {
        return null;
    }
}
