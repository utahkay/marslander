package game;

import org.junit.Test;
import static org.mockito.Mockito.mock;
import game.ui.GameUI;
import game.controllers.WinLossStatsController;

/**
 * Copyright (c) 2008 Kay Johansen
 */
public class MarsLanderGameTest {
    private static final int WIDTH = 200;
    private static final int HEIGHT = 500;
    private final GameUI mockGameUI = mock(GameUI.class);
    private final WinLossStatsController mockWinLossStats = mock(WinLossStatsController.class);
    private final MarsLanderGame game = new MarsLanderGame(WIDTH, HEIGHT, mockGameUI, mockWinLossStats);

    @Test
    public void foo() {

    }
}
