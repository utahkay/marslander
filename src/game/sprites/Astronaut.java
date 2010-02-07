package game.sprites;

import sprites.ImageManager;
import sprites.SpriteContainer;

import java.awt.*;

import sprites.BasicSprite;
import sprites.SpriteVector;

/**
 * Copyright (c) 2008 Kay Johansen
 */
public class Astronaut extends BasicSprite {
    private final static String ASTRONAUT_IMAGE_PATH = "images/astronaut.gif";
    private final static String ASTRONAUT__WITH_FLAG_IMAGE_PATH = "images/astronaut_flag.gif";
    private final Image astronaut;
    private final Image astronautWithFlag;
    private int astronautSteps = 0;

    public Astronaut(final ImageManager images, final SpriteContainer container) {
        super(container);
        astronaut = images.load(ASTRONAUT_IMAGE_PATH);
        astronautWithFlag = images.load(ASTRONAUT__WITH_FLAG_IMAGE_PATH);
        showAstronaut();
    }

    public void showAstronaut() {
        setImage(astronaut);
    }

    public void showAstronautWithFlag() {
        setImage(astronautWithFlag);
    }

    protected void spriteStep() {
        while (!stopRequest) {
            updatePosition();
            astronautSteps++;
            if (astronautSteps > 400) {
                showAstronautWithFlag();
                setVelocity(new SpriteVector(0, 0));
            }
            repaint();
            clockTick();
        }
    }
}
