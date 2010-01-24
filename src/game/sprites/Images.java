package game.sprites;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Images {
    public static Image loadImage(String path) {
        ImageIcon icon = new ImageIcon(path);
        return createBufferedImage(icon.getImage());
    }

    public static Image createBufferedImage(Image image) {
        int width = image.getWidth(null);
        int height = image.getHeight(null);
        BufferedImage buffImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics g = buffImage.getGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return buffImage;
    }

    public static Image concatImagesVertically(Image image1, Image image2) {
        int height = image1.getHeight(null) + image2.getHeight(null);
        int width = (image1.getWidth(null) > image2.getWidth(null)) ? image1.getWidth(null) : image2.getWidth(null);
        BufferedImage buffImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics g = buffImage.getGraphics();
        g.drawImage(image1, 0, 0, null);
        g.drawImage(image2, 0, image2.getHeight(null), null);
        g.dispose();
        return buffImage;
    }
}