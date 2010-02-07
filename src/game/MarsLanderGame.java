package game;

import game.controllers.AstronautController;
import game.controllers.LanderController;
import game.controllers.WinLossStatsController;
import game.ui.GameFrame;
import sprites.SpriteVector;

import java.awt.*;
import java.awt.image.ImageObserver;

public class MarsLanderGame {
    private final LanderController landerController;
    private final AstronautController astronautController;
    private final WinLossStatsController winLossStats;
    private static final double CRASH_VELOCITY = 0.2;

    public static void main(String[] args) {
        GameFrame frame = new GameFrame(450, 450);
        frame.setSize(600, 450);
        frame.setVisible(true);
    }

    public MarsLanderGame(LanderController landerController, AstronautController astronautController, WinLossStatsController winLossStats) {
        this.landerController = landerController;
        this.astronautController = astronautController;
        this.winLossStats = winLossStats;
    }

    public void keyPressed() {
        if (!astronautController.isAstronautOutside()) {
            landerController.jetOn();
        }
    }

    public void keyReleased() {
        landerController.jetOff();
    }

    public void paintSprites(Graphics g, ImageObserver observer) {
        landerController.paint(g, observer);
        if (astronautController.isAstronautOutside()) {
            astronautController.paint(g, observer);
        }
    }

    public void gameOver(final double velocity) {
        if (didPlayerWin(velocity)) {
            win();
        } else {
            lose();
        }
        landerController.requestStop();
    }

    public void startNewGame() {
        stopSprites();
        waitOneSecond();
        restartSprites();
    }

    private void stopSprites() {
        landerController.requestStop();
        astronautController.requestStop();
    }

    private void waitOneSecond() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e1) {
            Thread.currentThread().interrupt();
        }
    }

    private void restartSprites() {
        landerController.reset();
        astronautController.reset();
    }

    private boolean didPlayerWin(final double finalVelocity) {
        return finalVelocity < CRASH_VELOCITY;
    }

    private void win() {
        SpriteVector position = landerController.startingPositionForAstronaut();
        astronautController.startExcursion(position);
        winLossStats.gameWon();
    }

    private void lose() {
        landerController.crash();
        winLossStats.gameLost();
    }
}

