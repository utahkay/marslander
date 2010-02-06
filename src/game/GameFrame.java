package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Copyright (c) 2008 Kay Johansen
 */
class GameFrame extends JFrame implements GameStatsUI {
    private final JLabel gamesWon;
    private final JLabel gamesLost;

    public GameFrame() {
        setLayout(new FlowLayout());

        GameManager manager = new BasicGameManager(this);
        final Game game = new Game(450, 450, manager);
        add(game);

        final JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));

        JButton startOverButton = new JButton("Start Over");
        gamesWon = new JLabel();
        gamesLost = new JLabel();
        controlPanel.add(gamesWon);
        controlPanel.add(gamesLost);
        controlPanel.add(startOverButton);

        getRootPane().setDefaultButton(startOverButton);

        add(controlPanel);

        startOverButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                game.restart();
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        addWindowFocusListener(new WindowAdapter() {
            @Override
            public void windowGainedFocus(WindowEvent e) {
                game.requestFocusInWindow();
            }
        });

        game.addKeyListener(new KeyAdapter() {
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

    public void updateGameStats(final int numberGamesWon, final int numberGamesLost) {
        gamesWon.setText("Games Won: " + numberGamesWon);
        gamesLost.setText("Games Lost: " + numberGamesLost);
    }
}
