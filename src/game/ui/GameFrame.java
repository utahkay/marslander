package game.ui;

import game.ui.GameComponent;
import game.ui.GameStatsUI;
import game.GameManager;
import game.BasicGameManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Copyright (c) 2008 Kay Johansen
 */
public class GameFrame extends JFrame implements GameStatsUI {
    private final JLabel gamesWon;
    private final JLabel gamesLost;

    public GameFrame() {
        setLayout(new FlowLayout());

        GameManager manager = new BasicGameManager(this);
        final GameComponent game = new GameComponent(450, 450, manager);
        add(game);

        final JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));

        JButton startOverButton = new JButton("Play Again");
        controlPanel.add(startOverButton);

        gamesWon = new JLabel();
        controlPanel.add(gamesWon);

        gamesLost = new JLabel();
        controlPanel.add(gamesLost);

        add(controlPanel);

        getRootPane().setDefaultButton(startOverButton);
        startOverButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                game.startNewGame();
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
    }

    public void updateGameStats(final int numberGamesWon, final int numberGamesLost) {
        gamesWon.setText("Games Won: " + numberGamesWon);
        gamesLost.setText("Games Lost: " + numberGamesLost);
    }
}
