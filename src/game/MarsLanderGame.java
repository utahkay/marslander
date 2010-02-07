package game;

import game.sprites.Astronaut;
import game.sprites.MarsLander;
import game.ui.GameFrame;
import game.ui.GameUI;
import game.controllers.AstronautController;
import game.controllers.LanderController;
import game.controllers.WinLossStatsController;
import sprites.SpriteContainer;
import sprites.SpriteImageManager;
import sprites.SpriteVector;

import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;

public class MarsLanderGame implements SpriteContainer {
    private final WinLossStatsController winLossStats;
    private final GameUI uiComponent;
    private final Rectangle bounds;
    private LanderController landerController;
    private AstronautController astronautController;
    private static final double CRASH_VELOCITY = 0.2;

    public static void main(String[] args) {
        JFrame frame = new GameFrame();
        frame.setSize(600,450);
        frame.setVisible(true);
    }

    public MarsLanderGame(int width, int height, GameUI uiComponent, WinLossStatsController winLossStats) {
        this.uiComponent = uiComponent;
        this.winLossStats = winLossStats;
        bounds = new Rectangle(0, 0, width, height);
        startGame();
    }

    public void keyPressed() {
        if (!astronautController.isAstronautOutside()) {
            landerController.jetOn();
        }
    }

    public void keyReleased() {
        landerController.jetOff();
    }

    public void startNewGame() {
        stopSprites();
        waitOneSecond();
        startGame();
    }

    public void paintSprites(Graphics g, ImageObserver observer) {
        landerController.paint(g, observer);
        if (astronautController.isAstronautOutside()) {
            astronautController.paint(g, observer);
        }
    }

    // SpriteContainer method
    public void hitBottom(final double velocity) {
        stopGame(velocity < CRASH_VELOCITY);
    }

    // SpriteContainer method
    public void repaint() {
        uiComponent.repaint();
    }

    private void startGame() {
        Astronaut astronautSprite = new Astronaut(new SpriteImageManager(), this, bounds);
        astronautController = new AstronautController(astronautSprite);
        MarsLander marsLanderSprite = new MarsLander(new SpriteImageManager(), this, bounds);
        landerController = new LanderController(marsLanderSprite);
        uiComponent.requestFocus();
    }

    private void waitOneSecond() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e1) {
            Thread.currentThread().interrupt();
        }
    }

    private void stopSprites() {
        landerController.requestStop();
        astronautController.requestStop();
    }

    private void stopGame(boolean didPlayerWin) {
        landerController.jetOff();
        if (didPlayerWin) {
            showWin();
        } else {
            showLoss();
        }
        landerController.requestStop();
    }

    private void showLoss() {
        landerController.crash();
        winLossStats.gameLost();
    }

    private void showWin() {
        SpriteVector position = landerController.startingPositionForAstronaut();
        astronautController.startExcursion(position);
        winLossStats.gameWon();
    }

}

