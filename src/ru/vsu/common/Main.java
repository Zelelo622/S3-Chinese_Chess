package ru.vsu.common;

import ru.vsu.common.services.GameService;
import ru.vsu.common.utils.Graph;

public class Main {

    public static void main(String[] args) {
        GameService gameService = new GameService();
        for (int i = 0; i < gameService.initBoard().size(); i++) {
            System.out.println(gameService.initBoard().remove(i));
        }
    }
}
