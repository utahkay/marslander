package game.sprites;

import game.ImageManager;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class SpriteImageManager implements ImageManager {
    public Image load(final String path) {
        final ImageIcon icon = new ImageIcon(path);
        return createBufferedImage(icon.getImage());
    }

    public Image concatImagesVertically(final Image image1, final Image image2) {
        final int height = image1.getHeight(null) + image2.getHeight(null);
        final int width = (image1.getWidth(null) > image2.getWidth(null)) ? image1.getWidth(null) : image2.getWidth(null);
        final BufferedImage buffImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        final Graphics g = buffImage.getGraphics();
        g.drawImage(image1, 0, 0, null);
        g.drawImage(image2, 0, image2.getHeight(null), null);
        g.dispose();
        return buffImage;
    }

    private Image createBufferedImage(final Image image) {
        final int width = image.getWidth(null);
        final int height = image.getHeight(null);
        final BufferedImage buffImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        final Graphics g = buffImage.getGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return buffImage;
    }
}