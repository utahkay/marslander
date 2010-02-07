package game.controllers;

import game.sprites.Astronaut;
import sprites.SpriteVector;

import java.awt.*;
import java.awt.image.ImageObserver;

public class AstronautController {
    private Astronaut astronaut;
    private boolean isOutside = false;

    public AstronautController(Astronaut astronaut) {
        this.astronaut = astronaut;
    }

    public void requestStop() {
        astronaut.requestStop();
    }

    public void paint(Graphics g, ImageObserver observer) {
        astronaut.paint(g, observer);
    }

    public void startExcursion(SpriteVector startingPosition) {
        astronaut.setPosition(startingPosition);
        astronaut.setVelocity(new SpriteVector(0.15, 0.0));
        astronaut.showAstronaut();
        isOutside = true;
        astronaut.start();
    }

    public boolean isAstronautOutside() {
        return isOutside;
    }

    public void reset() {
        requestStop();
        isOutside = false;
    }
}