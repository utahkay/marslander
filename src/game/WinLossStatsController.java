package game;

import game.ui.WinLossStatsUI;

/**
 * Copyright (c) 2008 Kay Johansen
 */
public class WinLossStatsController {
    WinLossStatsUI winLossStatsUI;
    private int numberGamesWon = 0;
    private int numberGamesLost = 0;

    public WinLossStatsController(WinLossStatsUI winLossStatsUI) {
        this.winLossStatsUI = winLossStatsUI;
    }

    public void gameWon() {
        numberGamesWon++;
        winLossStatsUI.updateGameStats(numberGamesWon, numberGamesLost);
    }

    public void gameLost() {
        numberGamesLost++;
        winLossStatsUI.updateGameStats(numberGamesWon, numberGamesLost);
    }
}
