package game.controllers;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Matchers.any;
import game.sprites.Astronaut;
import game.controllers.AstronautController;

import java.awt.*;
import java.awt.image.ImageObserver;

import sprites.SpriteVector;

/**
 * Copyright (c) 2008 Kay Johansen
 */
public class AstronautControllerTest {
    Astronaut mockAstronaut = mock(Astronaut.class);
    AstronautController controller = new AstronautController(mockAstronaut);
    SpriteVector startPosition = new SpriteVector(4,5);

    @Test
    public void passThroughRequestStop() {
        controller.requestStop();
        verify(mockAstronaut).requestStop();
    }

    @Test
    public void passThroughPaint() {
        Graphics g = mock(Graphics.class);
        ImageObserver imageObserver = mock(ImageObserver.class);
        controller.paint(g, imageObserver);
        verify(mockAstronaut).paint(g, imageObserver);
    }

    @Test
    public void startExcursion() {
        controller.startExcursion(startPosition);
        verify(mockAstronaut).setPosition(startPosition);
        verify(mockAstronaut).setVelocity(any(SpriteVector.class));
        verify(mockAstronaut).start();
    }

    @Test
    public void astronautStartsInside() {
        assertEquals(false, controller.isAstronautOutside());
    }

    @Test
    public void astronautOutside() {
        controller.startExcursion(startPosition);
        assertEquals(true, controller.isAstronautOutside());        
    }
}
