package com.mashibing.tank.factory;

import com.mashibing.tank.Audio;
import com.mashibing.tank.ResourceManager;
import com.mashibing.tank.TankFrame;

import java.awt.*;

public class RectExplosion extends BaseExplosion{
    public static final int WIDTH = ResourceManager.explosions[0].getWidth();
    public static final int HEIGHT = ResourceManager.explosions[0].getHeight();
    private int x, y;
    private TankFrame tankFrame;
    private int step = 0;

    public RectExplosion(int x, int y, TankFrame tankFrame) {
        this.x = x;
        this.y = y;
        this.tankFrame = tankFrame;
        new Thread(() -> new Audio("audio/explode.wav").play()).start();
    }

    @Override
    public void paint(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.RED);
        g.fillRect(x, y, 5* step, 5*step);
        step++;
        if (step >= 15) {
            tankFrame.explosions.remove(this);
        }
        g.setColor(c);
    }
}