package game.controllers;

import game.sprites.MarsLander;
import sprites.SpriteVector;

import java.awt.*;
import java.awt.image.ImageObserver;

public class LanderController {
    private final MarsLander lander;
    private boolean isJetFiring = false;
    private static final double JET_ACCELERATION = 0.005;

    public LanderController(final MarsLander lander) {
        this.lander = lander;
        setInitalParameters();
        lander.start();
        lander.showLander();
    }

    public void jetOn() {
        if (!isJetFiring) {
            isJetFiring = true;
            lander.showLanderWithFlame();
            lander.changeAcceleration(new SpriteVector(0, -JET_ACCELERATION));
        }
    }

    public void jetOff() {
        if (isJetFiring) {
            isJetFiring = false;
            lander.showLander();
            lander.changeAcceleration(new SpriteVector(0, JET_ACCELERATION));
        }
    }

    public void requestStop() {
        lander.requestStop();
    }

    public void crash() {
        lander.showCrash();
    }

    public void paint(final Graphics g, final ImageObserver observer) {
        lander.paint(g, observer);
    }

    public SpriteVector startingPositionForAstronaut() {
        final SpriteVector position = lander.getPosition();
        position.x += lander.getSpriteSize()/2;
        return position;
    }

    private void setInitalParameters() {
        lander.setPosition(new SpriteVector(100.0, 100.0));
        lander.setVelocity(new SpriteVector(0.095, 0.0));
        lander.setAcceleration(new SpriteVector(0.0, 0.003)); // Gravity
        lander.setCollisionLoss(0.95);
    }

}