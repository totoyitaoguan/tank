package com.mashibing.tank;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

public class TankFrame extends Frame {
    static final int GAME_WIDTH = PropertyManager.getInt("gameWidth");
    static final int GAME_HEIGHT = PropertyManager.getInt("gameHeight");
    Tank myTank = new Tank(200, 400, Direction.DOWN, Group.GOOD, this);
    List<Bullet> bullets = new ArrayList<>();
    List<Tank> opponentTanks = new ArrayList<>();
    List<Explosion> explosions = new ArrayList<>();

    public TankFrame() {
        setSize(GAME_WIDTH, GAME_HEIGHT);
        setResizable(false);
        setTitle("Tank War");
        setVisible(true);

        this.addKeyListener(new MyKeyboardListener());

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    // double buffering to eliminate flickering
    Image offScreenImage = null;
    @Override
    public void update(Graphics g) {
        if (offScreenImage == null) {
            offScreenImage = this.createImage(GAME_WIDTH, GAME_HEIGHT);
        }
        Graphics gOffScreen = offScreenImage.getGraphics();
        Color c = gOffScreen.getColor();
        gOffScreen.setColor(Color.BLACK);
        gOffScreen.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
        gOffScreen.setColor(c);
        paint(gOffScreen);
        g.drawImage(offScreenImage, 0, 0, null);
    }

    @Override
    public void paint(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.WHITE);
        g.drawString("Bullets: " + bullets.size(), 10, 60);
        g.drawString("Opponent Tanks: " + opponentTanks.size(), 10, 80);
        g.drawString("Explosions: " + explosions.size(), 10, 100);
        g.setColor(c);

        myTank.paint(g);
        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).paint(g);
        }
        for (int i = 0; i < opponentTanks.size(); i++) {
            opponentTanks.get(i).paint(g);
        }
        for (int i = 0; i < explosions.size(); i++) {
            explosions.get(i).paint(g);
        }

        // check if bullet and opponent tanks collide
        for (int i = 0; i < bullets.size(); i++) {
            for (int j = 0; j < opponentTanks.size(); j++) {
                bullets.get(i).collideWith(opponentTanks.get(j));
            }
        }
    }

    class MyKeyboardListener extends KeyAdapter {
        boolean isLeft = false;
        boolean isUp = false;
        boolean isRight = false;
        boolean isDown = false;

        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            switch (key) {
                case KeyEvent.VK_LEFT:
                    isLeft = true;
                    break;
                case KeyEvent.VK_UP:
                    isUp = true;
                    break;
                case KeyEvent.VK_RIGHT:
                    isRight = true;
                    break;
                case KeyEvent.VK_DOWN:
                    isDown = true;
                    break;
                case KeyEvent.VK_SPACE:
                    myTank.fire(myTank.getFireMode());
                    break;
                default:
                    break;
            }
            setMyTankDirection();
        }

        @Override
        public void keyReleased(KeyEvent e) {
            int key = e.getKeyCode();
            switch (key) {
                case KeyEvent.VK_LEFT:
                    isLeft = false;
                    break;
                case KeyEvent.VK_UP:
                    isUp = false;
                    break;
                case KeyEvent.VK_RIGHT:
                    isRight = false;
                    break;
                case KeyEvent.VK_DOWN:
                    isDown = false;
                    break;
                default:
                    break;
            }
            setMyTankDirection();
        }

        private void setMyTankDirection() {
            myTank.setMoving(isLeft || isUp || isRight || isDown);
            if (isLeft) myTank.setDirection(Direction.LEFT);
            if (isUp) myTank.setDirection(Direction.UP);
            if (isRight) myTank.setDirection(Direction.RIGHT);
            if (isDown) myTank.setDirection(Direction.DOWN);
        }
    }
}
