package game;

import game.sprites.Astronaut;
import game.sprites.MarsLander;
import game.ui.GameFrame;
import game.ui.GameUI;
import sprites.SpriteContainer;
import sprites.SpriteImageManager;
import sprites.SpriteVector;

import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;

public class Game implements SpriteContainer {
    private final WinLossStatsController winLossStats;
    private final GameUI uiComponent;
    private final Rectangle bounds;
    private LanderController landerController;
    private Astronaut astronaut;
    private static final double CRASH_VELOCITY = 0.2;

    public static void main(String[] args) {
        JFrame frame = new GameFrame();
        frame.setSize(600,450);
        frame.setVisible(true);
    }

    public Game(int width, int height, GameUI uiComponent, WinLossStatsController winLossStats) {
        this.uiComponent = uiComponent;
        this.winLossStats = winLossStats;
        bounds = new Rectangle(0, 0, width, height);
        startGame();
    }

    public void keyPressed() {
        if (astronaut == null) {
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
        if (astronaut != null) {
            astronaut.paint(g, observer);
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
        astronaut = null;
        MarsLander lander = new MarsLander(new SpriteImageManager(), this, bounds);
        landerController = new LanderController(lander);
        uiComponent.requestFocus();
    }

    private void waitOneSecond() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e1) {
            Thread.currentThread().interrupt();
        }
    }

    private Astronaut createAstronautSprite() {
        SpriteVector position = landerController.startingPositionForAstronaut();
        Astronaut sprite = new Astronaut(new SpriteImageManager(), this);
        sprite.setBounds(bounds);
        sprite.setPosition(position);
        sprite.setVelocity(new SpriteVector(0.15, 0.0));
        return sprite;
    }

    private void stopSprites() {
        landerController.requestStop();
        if (astronaut != null) {
            astronaut.requestStop();
        }
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
        astronaut = createAstronautSprite();
        astronaut.start();
        winLossStats.gameWon();
    }

}

