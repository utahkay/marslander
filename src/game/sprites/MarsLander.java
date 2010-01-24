package game.sprites;

import java.awt.*;

/**
 * Copyright (c) 2008 Kay Johansen
 */
public class MarsLander extends BasicSprite {
    private static final String LANDER_IMAGE_PATH = "images/lander.gif";
    private static final String FLAMES_IMAGE_PATH = "images/RocketFlame.jpg";
    private static final String CRASH_IMAGE_PATH = "images/Explosion.gif";
    private Image lander;
    private Image landerWithFlame;
    private Image crash;

    public MarsLander() {
        loadImages();
        showLander();
    }

    public void showLander() {
        setImage(lander);
    }

    public void showLanderWithFlame() {
        setImage(landerWithFlame);
        setSpriteSize(lander.getHeight(null));
    }

    public void showCrash() {
        setImage(crash);
    }

    private void loadImages() {
        lander = Images.loadImage(LANDER_IMAGE_PATH);
        landerWithFlame = Images.concatImagesVertically(lander, Images.loadImage(FLAMES_IMAGE_PATH));
        crash = Images.loadImage(CRASH_IMAGE_PATH);
    }

}
