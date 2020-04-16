package com.mashibing.tank;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TankFrame extends Frame {
    static final int GAME_WIDTH = PropertyManager.getInt("gameWidth");
    static final int GAME_HEIGHT = PropertyManager.getInt("gameHeight");
    GameModelFacade gameModelFacade = new GameModelFacade();

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
        gameModelFacade.paint(g);
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
                    gameModelFacade.getMainTank().fire(gameModelFacade.getMainTank().getFireMode());
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
            Tank myTank = gameModelFacade.getMainTank();
            myTank.setMoving(isLeft || isUp || isRight || isDown);
            if (isLeft) myTank.setDirection(Direction.LEFT);
            if (isUp) myTank.setDirection(Direction.UP);
            if (isRight) myTank.setDirection(Direction.RIGHT);
            if (isDown) myTank.setDirection(Direction.DOWN);
        }
    }
}
