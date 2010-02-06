package game;

import game.sprites.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MarsLanderGame extends JComponent implements SpriteContainer {
    private MarsLander lander;
    private Astronaut astronaut;
    private boolean isJetFiring;
    private static final double JET_ACCELERATION = 0.005;
    private static final double CRASH_VELOCITY = 0.2;

    public MarsLanderGame(int width, int height) {
        setPreferredSize(new Dimension(width, height));
        setBounds(0,0,width, height);
        startGame();
    }

    private void startGame() {
        astronaut = null;
        lander = createLanderSprite();
        setInitalParameters();
        lander.start();
        requestFocus();
    }

    private void setInitalParameters() {
        isJetFiring = false;
        lander.showLander();
        lander.setPosition(new SpriteVector(100.0, 100.0));
        lander.setVelocity(new SpriteVector(0.095, 0.0));
        lander.setAcceleration(new SpriteVector(0.0, 0.003)); // Gravity
        lander.setCollisionLoss(0.95);
    }

    @Override
    public void paint(Graphics g) {
        lander.paint(g, this);
        if (astronaut != null) {
            astronaut.paint(g, this);
        }
    }

    public void restart() {
        stopSprites();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e1) {
            Thread.currentThread().interrupt();
        }
        startGame();
    }

    public void keyPressed() {
        if (!isJetFiring && astronaut == null) {
            jetOn();
        }
    }

    public void keyReleased() {
        if (isJetFiring) {
            jetOff();
        }
    }

    private void jetOn() {
        isJetFiring = true;
        lander.showLanderWithFlame();
        SpriteVector acc = lander.getAcceleration();
        acc.y -= JET_ACCELERATION;
        lander.setAcceleration(acc);
    }

    private void jetOff() {
        isJetFiring = false;
        lander.showLander();
        SpriteVector acc = lander.getAcceleration();
        acc.y += JET_ACCELERATION;
        lander.setAcceleration(acc);
    }

    private MarsLander createLanderSprite() {
        MarsLander sprite = new MarsLander(new SpriteImageManager(), this);
        sprite.setBounds(getBounds());
        return sprite;
    }

    private Astronaut createAstronautSprite() {
        SpriteVector position = lander.getPosition();
        position.x += lander.getSpriteSize()/2;
        Astronaut sprite = new Astronaut(new SpriteImageManager(), this);
        sprite.setBounds(getBounds());
        sprite.setPosition(position);
        sprite.setVelocity(new SpriteVector(0.15, 0.0));
        return sprite;
    }

    private void stopSprites() {
        lander.requestStop();
        if (astronaut != null) {
            astronaut.requestStop();
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setLayout(new FlowLayout());

        final MarsLanderGame component = new MarsLanderGame(450,450);
        frame.add(component);

        final JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));

        final JButton button = new JButton("Start Over");
        controlPanel.add(button);

        frame.add(controlPanel);

        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                component.restart();
            }
        });

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        frame.addWindowFocusListener(new WindowAdapter() {
            public void windowGainedFocus(WindowEvent e) {
                component.requestFocusInWindow();
            }
        });

        component.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                component.keyPressed();
            }
            @Override
            public void keyReleased(KeyEvent e) {
                component.keyReleased();
            }
        });

        frame.setSize(600,450);
        frame.setVisible(true);
    }


    public void hitBottom(final double velocity) {
        if (isJetFiring) {
            jetOff();
        }
        if (velocity >= CRASH_VELOCITY) {
            showLoss();
        } else {
            showWin();
        }
        lander.requestStop();
    }

    private void showLoss() {
        lander.showCrash();
    }

    private void showWin() {
        astronaut = createAstronautSprite();
        astronaut.start();
    }

}
