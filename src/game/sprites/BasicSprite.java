package game.sprites;

import game.SpriteContainer;

import java.awt.*;
import java.awt.image.ImageObserver;

/**
 * Copyright (c) 2008 Kay Johansen
 */
public class BasicSprite implements Sprite {
    private final SpriteContainer container;
    private Image image;
    private int spriteSize;
    private double x, y;
    private double x_velocity, y_velocity;
    private double x_acc, y_acc;
    private double friction_loss;
    private static final int OFFSET_Y = 30;
    private Rectangle bounds;
    private static final long TIME_INTERVAL_MILLIS = 4;
    protected volatile boolean stopRequest;

    public BasicSprite(final SpriteContainer container) {
        this.container = container;
    }

    public void paint(final Graphics g, final ImageObserver observer) {
        final int drawX = (int)(x - spriteSize/2);
        final int drawY = (int)(y - spriteSize/2 - OFFSET_Y);
        g.drawImage(image, drawX, drawY, observer);
    }

    public void start() {
        final Runnable r = new Runnable() {
            public void run() {
                try {
                    spriteStep();
                }
                catch (Exception x) {
                    x.printStackTrace();
                }
            }
        };

        stopRequest = false;
        final Thread thread = new Thread(r);
        thread.start();
    }

    public void requestStop() {
        stopRequest = true;
    }

    public int getSpriteSize() {
        return spriteSize;
    }

    public void setSpriteSize(final int size) {
        spriteSize = size;
    }

    public SpriteVector getPosition() {
        return new SpriteVector(x, y);
    }

    public void setPosition(final SpriteVector position) {
        x = position.x;
        y = position.y;
    }

    public SpriteVector getVelocity() {
        return new SpriteVector(x_velocity, y_velocity);
    }

    public void setVelocity(final SpriteVector v) {
        x_velocity = v.x;
        y_velocity = v.y;
    }

    public SpriteVector getAcceleration() {
        return new SpriteVector(x_acc, y_acc);
    }

    public void setAcceleration(final SpriteVector acc) {
        x_acc = acc.x;
        y_acc = acc.y;
    }

    public void setCollisionLoss(final double f) {
        friction_loss = f;
    }

    public void setImage(final Image image) {
        this.image = image;
        setSpriteSize(image.getHeight(null));
    }

    public void setBounds(final Rectangle bounds) {
        this.bounds = bounds;
    }

    protected void hitTop() {
        y_velocity = -(y_velocity*friction_loss);
        x_velocity *= friction_loss;
    }

    protected void hitBottom() {
        container.hitBottom(y_velocity);
        hitTop();
    }

    protected void hitLeft() {
        x_velocity = -(x_velocity*friction_loss);
        y_velocity *= friction_loss;
    }

    protected void hitRight() {
        hitLeft();
    }

    protected void spriteStep() {
        while (!stopRequest) {
            updatePosition();
            repaint();
            clockTick();
        }
    }

    protected void updatePosition() {
        final double spriteTop = y + y_velocity - spriteSize /2;
        final double spriteBottom = y + y_velocity + spriteSize /2;
        if (spriteTop <= 0) {
            hitTop();
        } else if (spriteBottom >= bounds.getHeight()) {
            hitBottom();
        } else {
            y_velocity += y_acc;
        }

        final double spriteLeft = x + x_velocity - spriteSize /2;
        final double spriteRight = x + x_velocity + spriteSize /2;
        if (spriteLeft <= 0) {
            hitLeft();
        } else if (spriteRight >= bounds.getWidth()) {
            hitRight();
        } else {
            x_velocity += x_acc;
        }

        y += y_velocity;
        x += x_velocity;
    }

    protected void repaint() {
        container.repaint();
    }

    protected void clockTick() {
        try {
            Thread.sleep(TIME_INTERVAL_MILLIS);
        } catch (InterruptedException x) {
            Thread.currentThread().interrupt();
        }
    }

}
