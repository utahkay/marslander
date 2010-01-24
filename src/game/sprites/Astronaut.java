package game.sprites;

import java.awt.*;

/**
 * Copyright (c) 2008 Kay Johansen
 */
public class Astronaut extends BasicSprite {
    private final static String ASTRONAUT_IMAGE_PATH = "images/astronaut.gif";
    private final static String ASTRONAUT__WITH_FLAG_IMAGE_PATH = "images/astronaut_flag.gif";
    private Image astronaut;
    private Image astronautWithFlag;
    private int astronautSteps = 0;

    public Astronaut() {
        loadImages();
        showAstronaut();
    }

    public void showAstronaut() {
        setImage(astronaut);
    }

    public void showAstronautWithFlag() {
        setImage(astronautWithFlag);
    }

    private void loadImages() {
        astronaut = Images.loadImage(ASTRONAUT_IMAGE_PATH);
        astronautWithFlag = Images.loadImage(ASTRONAUT__WITH_FLAG_IMAGE_PATH);
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
