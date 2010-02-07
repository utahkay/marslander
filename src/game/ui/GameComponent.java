package game.ui;

import game.MarsLanderGame;
import game.sprites.Astronaut;
import game.sprites.MarsLander;
import game.controllers.WinLossStatsController;
import game.controllers.AstronautController;
import game.controllers.LanderController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import sprites.SpriteImageManager;
import sprites.SpriteContainer;

/**
 * Copyright (c) 2008 Kay Johansen
 */
public class GameComponent extends JComponent implements SpriteContainer {
    private final MarsLanderGame game;

    public GameComponent(int width, int height, WinLossStatsController winLossStats) {
        setPreferredSize(new Dimension(width, height));
        Rectangle bounds = new Rectangle(0, 0, width, height);
        setBounds(bounds);

        MarsLander marsLanderSprite = new MarsLander(new SpriteImageManager(), this, bounds);
        Astronaut astronautSprite = new Astronaut(new SpriteImageManager(), this, bounds);

        LanderController landerController = new LanderController(marsLanderSprite);
        AstronautController astronautController = new AstronautController(astronautSprite);

        game = new MarsLanderGame(landerController, astronautController, winLossStats);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                game.keyPressed();
            }
            @Override
            public void keyReleased(KeyEvent e) {
                game.keyReleased();
            }
        });

        requestFocus();
    }

    public void startNewGame() {
        game.startNewGame();
    }

    @Override
    protected void paintComponent(Graphics g) {
        game.paintSprites(g, this);
    }

    // SpriteContainer method
    public void hitBottom(double velocity) {
        game.gameOver(velocity);
    }

}
