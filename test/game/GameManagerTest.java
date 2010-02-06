package game;

import org.junit.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Copyright (c) 2008 Kay Johansen
 */
public class GameManagerTest {
    GameStats mockReporter = mock(GameStats.class);
    GameManager mgr = new GameManager(mockReporter);

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
