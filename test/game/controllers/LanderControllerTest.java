package game.controllers;

import game.sprites.MarsLander;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyDouble;
import static org.mockito.Mockito.*;
import sprites.SpriteVector;

import java.awt.*;
import java.awt.image.ImageObserver;

/**
 * Copyright (c) 2008 Kay Johansen
 */
public class LanderControllerTest {
    MarsLander mockLander = mock(MarsLander.class);
    LanderController controller = new LanderController(mockLander);

    @Test
    public void constructingShowsCorrectSpriteImageAndStartsSprite() {
        verify(mockLander).showLander();
        verify(mockLander).start();
    }

    @Test
    public void constructingSetsSpriteParameters() {
        verify(mockLander).setPosition(any(SpriteVector.class));
        verify(mockLander).setVelocity(any(SpriteVector.class));
        verify(mockLander).setAcceleration(any(SpriteVector.class));
        verify(mockLander).setCollisionLoss(anyDouble());
    }

    @Test
    public void fireJetChangesAccelerationAndTogglesSpriteImage() {
        goToStateJetOff();
        controller.jetOn();
        verify(mockLander).showLanderWithFlame();
        verify(mockLander).changeAcceleration(any(SpriteVector.class));
    }

    @Test
    public void fireJetWhenJetIsAlreadyOnDoesNothing() {
        goToStateJetOn();
        controller.jetOn();
        verify(mockLander, never()).showLander();
        verify(mockLander, never()).changeAcceleration(any(SpriteVector.class));
    }

    @Test
    public void stopFiringJetChangesAccelerationAndTogglesSpriteImage() {
        goToStateJetOn();
        controller.jetOff();
        verify(mockLander).showLander();
        verify(mockLander).changeAcceleration(any(SpriteVector.class));
    }

    @Test
    public void stopFiringJetWhenJetIsOffDoesNothing() {
        goToStateJetOff();
        controller.jetOff();
        verify(mockLander, never()).showLander();
        verify(mockLander, never()).changeAcceleration(any(SpriteVector.class));
    }

    private void goToStateJetOn() {
        controller.jetOn();
        reset(mockLander);
    }

    private void goToStateJetOff() {
        reset(mockLander);
    }

    @Test
    public void passThroughRequestStop() {
        controller.requestStop();
        verify(mockLander).requestStop();
    }

    @Test
    public void showCrashSpriteImage() {
        controller.crash();
        verify(mockLander).showCrash();
    }

    @Test
    public void passThroughPaint() {
        Graphics g = mock(Graphics.class);
        ImageObserver imageObserver = mock(ImageObserver.class);
        controller.paint(g, imageObserver);
        verify(mockLander).paint(g, imageObserver);
    }

    @Test
    public void startingPositionForAstronaut() {
        when(mockLander.getPosition()).thenReturn(new SpriteVector(50, 50));
        when(mockLander.getSpriteSize()).thenReturn(20);
        SpriteVector expected = new SpriteVector(60, 50);
        assertEquals(expected, controller.startingPositionForAstronaut());
    }

    @Test
    public void resetStopsSprite() {
        controller.reset();
        verify(mockLander).requestStop();
    }

    @Test
    public void resetShowsCorrectSpriteImageAndStartsSprite() {
        goToStateJetOff();
        controller.reset();
        verify(mockLander).showLander();
        verify(mockLander).start();
    }

    @Test
    public void resetSetsSpriteParameters() {
        goToStateJetOff();
        controller.reset();
        verify(mockLander).setPosition(any(SpriteVector.class));
        verify(mockLander).setVelocity(any(SpriteVector.class));
        verify(mockLander).setAcceleration(any(SpriteVector.class));
        verify(mockLander).setCollisionLoss(anyDouble());
    }

    @Test
    public void resetTurnsJetOff() {
        goToStateJetOn();
        controller.reset();
        controller.jetOff();
        verify(mockLander, never()).changeAcceleration(any(SpriteVector.class));
    }

}
