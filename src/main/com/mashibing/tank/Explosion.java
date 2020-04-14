package com.mashibing.tank;

import java.awt.*;

public class Explosion {
    public static final int WIDTH = ResourceManager.explosions[0].getWidth();
    public static final int HEIGHT = ResourceManager.explosions[0].getHeight();
    private int x, y;
    private TankFrame tankFrame;
    private int step = 0;

    public Explosion(int x, int y, TankFrame tankFrame) {
        this.x = x;
        this.y = y;
        this.tankFrame = tankFrame;
        new Thread(() -> new Audio("audio/explode.wav").play()).start();
    }

    public void paint(Graphics g) {
        g.drawImage(ResourceManager.explosions[step++], x, y, null);
        if (step >= ResourceManager.explosions.length) {
            tankFrame.explosions.remove(this);
        }
    }
}
