package game;

import java.awt.*;

/**
 * Copyright (c) 2008 Kay Johansen
 */
public interface ImageManager {
    Image load(final String path);
    Image concatImagesVertically(final Image image1, final Image image2);
}
