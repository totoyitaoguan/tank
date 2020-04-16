package com.mashibing.tank.firemode;

import com.mashibing.tank.*;

public class DefaultFireMode implements FireMode {
    @Override
    public void fire(Tank tank) {
        int bulletX = tank.getX() + Tank.WIDTH / 2 - Bullet.WIDTH / 2;
        int bulletY = tank.getY() + Tank.HEIGHT / 2 - Bullet.HEIGHT / 2;
        new Bullet(bulletX, bulletY, tank.getDirection(), tank.getGroup(), tank.getGameModelFacade());

        if (tank.getGroup() == Group.GOOD) {
            new Thread(() -> new Audio("audio/tank_fire.wav").play()).start();
        }
    }
}
