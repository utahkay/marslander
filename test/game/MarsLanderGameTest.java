package game;

import org.junit.Test;
import static org.mockito.Mockito.*;
import game.controllers.WinLossStatsController;
import game.controllers.LanderController;
import game.controllers.AstronautController;

import java.awt.*;
import java.awt.image.ImageObserver;

import sprites.SpriteVector;

/**
 * Copyright (c) 2008 Kay Johansen
 */
public class MarsLanderGameTest {
    private final LanderController mockLander = mock(LanderController.class);
    private final AstronautController mockAstronaut = mock(AstronautController.class);
    private final WinLossStatsController mockWinLossStats = mock(WinLossStatsController.class);
    private final MarsLanderGame game = new MarsLanderGame(mockLander, mockAstronaut, mockWinLossStats);
    private final Graphics mockGraphics = mock(Graphics.class);
    private final ImageObserver mockImageObserver = mock(ImageObserver.class);

    @Test
    public void pressingKeyFiresTheJet() {
        game.keyPressed();
        verify(mockLander).jetOn();
    }

    @Test
    public void releasingKeyTurnsJetOff() {
        game.keyReleased();
        verify(mockLander).jetOff();
    }

    @Test
    public void jetWillNotFireIfAstronautIsOutside() {
        setStateAstronautOutside();
        game.keyPressed();
        verify(mockLander, never()).jetOn();
    }

    @Test
    public void paintOnlyPaintsLanderWhenAstronautIsInside() {
        setStateAstronautInside();
        game.paintSprites(mockGraphics, mockImageObserver);
        verify(mockLander).paint(mockGraphics, mockImageObserver);
        verify(mockAstronaut, never()).paint(mockGraphics, mockImageObserver);
    }

    @Test
    public void paintPaintsLanderAndAstronautWhenAstronautIsOutside() {
        setStateAstronautOutside();
        game.paintSprites(mockGraphics, mockImageObserver);
        verify(mockLander).paint(mockGraphics, mockImageObserver);
        verify(mockAstronaut).paint(mockGraphics, mockImageObserver);
    }

    @Test
    public void gameOverWinTheAstronautComesOutside() {
        winTheGame();
        verify(mockAstronaut).startExcursion(any(SpriteVector.class));
        verify(mockLander, never()).crash();
    }

    @Test
    public void gameOverLoseTheLanderCrashes() {
        loseTheGame();
        verify(mockLander).crash();
        verify(mockAstronaut, never()).startExcursion(any(SpriteVector.class));
    }

    @Test
    public void gameOverWinStopsTheLander() {
        winTheGame();
        verify(mockLander).requestStop();
    }

    @Test
    public void gameOverLoseStopsTheLander() {
        loseTheGame();
        verify(mockLander).requestStop();
    }

    @Test
    public void gameOverWinUpdatesGameStats() {
        winTheGame();
        verify(mockWinLossStats).gameWon();  
    }

    @Test
    public void gameOverLoseUpdatesGameStats() {
        loseTheGame();
        verify(mockWinLossStats).gameLost();
    }

    @Test
    public void startNewGameStopsThenResetsSprites() {
        game.startNewGame();
        verify(mockLander).requestStop();
        verify(mockLander).reset();
        verify(mockAstronaut).requestStop();
        verify(mockAstronaut).reset();
    }

    private void setStateAstronautInside() {
        when(mockAstronaut.isAstronautOutside()).thenReturn(false);
    }

    private void setStateAstronautOutside() {
        when(mockAstronaut.isAstronautOutside()).thenReturn(true);
    }

    private void winTheGame() {
        game.gameOver(0.0);
    }

    private void loseTheGame() {
        game.gameOver(100.0);
    }

}
