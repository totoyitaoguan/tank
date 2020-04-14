package com.mashibing.tank;

public class FourDirectionsFireMode implements FireMode {
    @Override
    public void fire(Tank tank) {
        int bulletX = tank.getX() + Tank.WIDTH / 2 - Bullet.WIDTH / 2;
        int bulletY = tank.getY() + Tank.HEIGHT / 2 - Bullet.HEIGHT / 2;

        Direction[] directions = Direction.values();
        for (Direction dir : directions) {
            new Bullet(bulletX, bulletY, dir, tank.getGroup(), tank.getTankFrame());
        }

        if (tank.getGroup() == Group.GOOD) {
            new Thread(() -> new Audio("audio/tank_fire.wav").play()).start();
        }
    }
}
