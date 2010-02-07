package game.ui;

import game.Game;
import game.WinLossStatsController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Copyright (c) 2008 Kay Johansen
 */
public class GameComponent extends JComponent implements GameUI {
    private final Game game;

    public GameComponent(int width, int height, WinLossStatsController winLossStats) {
        setPreferredSize(new Dimension(width, height));
        setBounds(0,0,width, height);
        game = new Game(width, height, this, winLossStats);

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
    }

    public void startNewGame() {
        game.startNewGame();
    }

    @Override
    protected void paintComponent(Graphics g) {
        game.paintSprites(g, this);
    }
}
