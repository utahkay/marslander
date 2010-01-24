package game.sprites;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;

/**
 * Copyright (c) 2008 Kay Johansen
 */
public class Ball extends BasicSprite {

    public Ball(int ballWidth, Color ballColor) {
        setImage(createBallImage(ballWidth, ballColor));
    }

    private Image createBallImage(int ballWidth, Color color) {
        BufferedImage image = new BufferedImage(2*ballWidth+1, 2*ballWidth+1, BufferedImage.TYPE_INT_ARGB);
        Ellipse2D ball = new Ellipse2D.Double(0, 0, ballWidth, ballWidth);
        Graphics2D g2 = image.createGraphics();
        g2.setColor(color);
        g2.fill(ball);
        g2.dispose();
        return image;
    }
}
