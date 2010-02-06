package game.sprites;

import game.ImageManager;
import game.SpriteContainer;

import java.awt.*;

/**
 * Copyright (c) 2008 Kay Johansen
 */
public class MarsLander extends BasicSprite {
    private static final String LANDER_IMAGE_PATH = "images/lander.gif";
    private static final String FLAMES_IMAGE_PATH = "images/RocketFlame.jpg";
    private static final String CRASH_IMAGE_PATH = "images/Explosion.gif";
    private final Image lander;
    private final Image landerWithFlame;
    private final Image crash;

    public MarsLander(final ImageManager images, final SpriteContainer container) {
        super(container);
        lander = images.load(LANDER_IMAGE_PATH);
        landerWithFlame = images.concatImagesVertically(lander, images.load(FLAMES_IMAGE_PATH));
        crash = images.load(CRASH_IMAGE_PATH);
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

}
