package game.sprites;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

import java.awt.*;

import sprites.ImageManager;
import game.sprites.Astronaut;


/**
 * Copyright (c) 2008 Kay Johansen
 */
public class AstronautTest {
    ImageManager mockImageManager = mock(ImageManager.class);
    Image mockImage = mock(Image.class);
    Rectangle bounds;

    @Before
    public void imageManagerReturnsMockImage() {
        when(mockImageManager.load(anyString())).thenReturn(mockImage);
    }

    @Test
    public void loadsImagesWhenConstructed() {
        new Astronaut(mockImageManager, null, bounds);
        verify(mockImageManager).load(contains("astronaut.gif"));
        verify(mockImageManager).load(contains("astronaut_flag.gif"));
    }

    @Test
    public void setsInitialImageWhenConstructed() {
        when(mockImage.getHeight(null)).thenReturn(100);
        Astronaut astronaut = new Astronaut(mockImageManager, null, bounds);
        assertEquals(100, astronaut.getSpriteSize());        
    }

}

