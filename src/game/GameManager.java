package game;

/**
 * Copyright (c) 2008 Kay Johansen
 */
public class GameManager implements IGameManager {
    GameStats statsReporter;
    private int numberGamesWon = 0;
    private int numberGamesLost = 0;

    public GameManager(GameStats statsReporter) {
        this.statsReporter = statsReporter;
    }

    public void gameWon() {
        numberGamesWon++;
        statsReporter.updateGameStats(numberGamesWon, numberGamesLost);
    }

    public void gameLost() {
        numberGamesLost++;
        statsReporter.updateGameStats(numberGamesWon, numberGamesLost);
    }
}
