package game;

import org.junit.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import game.ui.GameStatsUI;

/**
 * Copyright (c) 2008 Kay Johansen
 */
public class BasicGameManagerTest {
    GameStatsUI mockReporter = mock(GameStatsUI.class);
    BasicGameManager mgr = new BasicGameManager(mockReporter);

    @Test
    public void winningIncrementsGamesWon() {
        mgr.gameWon();
        verify(mockReporter).updateGameStats(1, 0);
    }

    @Test
    public void losingIncrementsGamesLost() {
        mgr.gameLost();
        verify(mockReporter).updateGameStats(0, 1);
    }
}
