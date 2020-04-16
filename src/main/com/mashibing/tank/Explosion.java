package com.mashibing.tank;

import java.awt.*;

public class Explosion extends GameObject{
    public static final int WIDTH = ResourceManager.explosions[0].getWidth();
    public static final int HEIGHT = ResourceManager.explosions[0].getHeight();
    private int x, y;
    private GameModelFacade gameModelFacade;
    private int step = 0;

    public Explosion(int x, int y, GameModelFacade gameModelFacade) {
        this.x = x;
        this.y = y;
        this.gameModelFacade = gameModelFacade;
        new Thread(() -> new Audio("audio/explode.wav").play()).start();
    }

    public void paint(Graphics g) {
        g.drawImage(ResourceManager.explosions[step++], x, y, null);
        if (step >= ResourceManager.explosions.length) {
            gameModelFacade.remove(this);
        }
    }
}
