package game;

import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;

import sprites.SpriteImageManager;
import sprites.SpriteVector;
import sprites.SpriteContainer;
import game.ui.GameFrame;
import game.ui.GameUI;
import game.sprites.Astronaut;
import game.sprites.MarsLander;

public class Game implements SpriteContainer {
    private final WinLossStatsController winLossStats;
    private final GameUI uiComponent;
    private final Rectangle bounds;
    private MarsLander lander;
    private Astronaut astronaut;
    private boolean isJetFiring;
    private static final double JET_ACCELERATION = 0.005;
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
        if (!isJetFiring && astronaut == null) {
            jetOn();
        }
    }

    public void keyReleased() {
        if (isJetFiring) {
            jetOff();
        }
    }

    public void startNewGame() {
        stopSprites();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e1) {
            Thread.currentThread().interrupt();
        }
        startGame();
    }

    public void paintSprites(Graphics g, ImageObserver observer) {
        lander.paint(g, observer);
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
        lander = createLanderSprite();
        setInitalParameters();
        lander.start();
        uiComponent.requestFocus();
    }

    private void setInitalParameters() {
        isJetFiring = false;
        lander.showLander();
        lander.setPosition(new SpriteVector(100.0, 100.0));
        lander.setVelocity(new SpriteVector(0.095, 0.0));
        lander.setAcceleration(new SpriteVector(0.0, 0.003)); // Gravity
        lander.setCollisionLoss(0.95);
    }

    private void jetOn() {
        isJetFiring = true;
        lander.showLanderWithFlame();
        lander.changeAcceleration(new SpriteVector(0, -JET_ACCELERATION));
    }

    private void jetOff() {
        isJetFiring = false;
        lander.showLander();
        lander.changeAcceleration(new SpriteVector(0, JET_ACCELERATION));
    }

    private MarsLander createLanderSprite() {
        MarsLander sprite = new MarsLander(new SpriteImageManager(), this);
        sprite.setBounds(bounds);
        return sprite;
    }

    private Astronaut createAstronautSprite() {
        SpriteVector position = lander.getPosition();
        position.x += lander.getSpriteSize()/2;
        Astronaut sprite = new Astronaut(new SpriteImageManager(), this);
        sprite.setBounds(bounds);
        sprite.setPosition(position);
        sprite.setVelocity(new SpriteVector(0.15, 0.0));
        return sprite;
    }

    private void stopSprites() {
        lander.requestStop();
        if (astronaut != null) {
            astronaut.requestStop();
        }
    }

    private void stopGame(boolean didPlayerWin) {
        if (isJetFiring) {
            jetOff();
        }
        if (didPlayerWin) {
            showWin();
        } else {
            showLoss();
        }
        lander.requestStop();
    }

    private void showLoss() {
        lander.showCrash();
        winLossStats.gameLost();
    }

    private void showWin() {
        astronaut = createAstronautSprite();
        astronaut.start();
        winLossStats.gameWon();
    }

}

