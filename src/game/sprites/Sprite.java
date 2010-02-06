package game.sprites;

import java.awt.*;
import java.awt.image.ImageObserver;

/**
 * Copyright (c) 2008 Kay Johansen
 */
public interface Sprite {
    void paint(Graphics g, ImageObserver observer);
    void start();
    void requestStop();

    int getSpriteSize();
    void setSpriteSize(int size);

    SpriteVector getPosition();
    void setPosition(SpriteVector position);

    SpriteVector getVelocity();
    void setVelocity(SpriteVector v);

    SpriteVector getAcceleration();
    void setAcceleration(SpriteVector acc);
    void changeAcceleration(SpriteVector deltaAcceleration);

    void setCollisionLoss(double f);
}
