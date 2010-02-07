package game;

import game.ui.GameStatsUI;

/**
 * Copyright (c) 2008 Kay Johansen
 */
public class BasicWinLossStatsController implements WinLossStatsController {
    GameStatsUI gameStatsUI;
    private int numberGamesWon = 0;
    private int numberGamesLost = 0;

    public BasicWinLossStatsController(GameStatsUI gameStatsUI) {
        this.gameStatsUI = gameStatsUI;
    }

    public void gameWon() {
        numberGamesWon++;
        gameStatsUI.updateGameStats(numberGamesWon, numberGamesLost);
    }

    public void gameLost() {
        numberGamesLost++;
        gameStatsUI.updateGameStats(numberGamesWon, numberGamesLost);
    }
}
