package game;

import javax.swing.*;
import java.awt.*;

import sprites.SpriteImageManager;
import sprites.SpriteVector;
import sprites.SpriteContainer;

public class Game extends JComponent implements SpriteContainer {
    private final GameManager gameManager;
    private MarsLander lander;
    private Astronaut astronaut;
    private boolean isJetFiring;
    private static final double JET_ACCELERATION = 0.005;
    private static final double CRASH_VELOCITY = 0.2;

    public Game(int width, int height, GameManager manager) {
        this.gameManager = manager;
        setPreferredSize(new Dimension(width, height));
        setBounds(0,0,width, height);
        startGame();
    }

    private void startGame() {
        astronaut = null;
        lander = createLanderSprite();
        setInitalParameters();
        lander.start();
        requestFocus();
    }

    private void setInitalParameters() {
        isJetFiring = false;
        lander.showLander();
        lander.setPosition(new SpriteVector(100.0, 100.0));
        lander.setVelocity(new SpriteVector(0.095, 0.0));
        lander.setAcceleration(new SpriteVector(0.0, 0.003)); // Gravity
        lander.setCollisionLoss(0.95);
    }

    @Override
    public void paintComponent(Graphics g) {
        lander.paint(g, this);
        if (astronaut != null) {
            astronaut.paint(g, this);
        }
    }

    public void restart() {
        stopSprites();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e1) {
            Thread.currentThread().interrupt();
        }
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
        sprite.setBounds(getBounds());
        return sprite;
    }

    private Astronaut createAstronautSprite() {
        SpriteVector position = lander.getPosition();
        position.x += lander.getSpriteSize()/2;
        Astronaut sprite = new Astronaut(new SpriteImageManager(), this);
        sprite.setBounds(getBounds());
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

    // SpriteContainer method
    public void hitBottom(final double velocity) {
        stopGame(velocity < CRASH_VELOCITY);
    }

    private void stopGame(boolean didPlayerWin) {
        if (isJetFiring) {
            jetOff();
        }
        if (didPlayerWin) {
            showWin();
            gameManager.gameWon();
        } else {
            showLoss();
            gameManager.gameLost();
        }
        lander.requestStop();
    }

    private void showLoss() {
        lander.showCrash();
    }

    private void showWin() {
        astronaut = createAstronautSprite();
        astronaut.start();
    }

    public static void main(String[] args) {
        JFrame frame = new GameFrame();
        frame.setSize(600,450);
        frame.setVisible(true);
    }

}

