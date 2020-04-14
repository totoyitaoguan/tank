package com.mashibing.tank.factory;

import com.mashibing.tank.*;

public class DefaultFactory extends GameFactory{
    @Override
    public BaseTank createTank(int x, int y, Direction direction, Group group, TankFrame tankFrame) {
        return new Tank(x ,y, direction, group, tankFrame);
    }

    @Override
    public BaseExplosion createExplosion(int x, int y, TankFrame tankFrame) {
        return new Explosion(x, y, tankFrame);
    }

    @Override
    public BaseBullet createBullet(int x, int y, Direction direction, Group group, TankFrame tankFrame) {
        return new Bullet(x, y, direction, group, tankFrame);
    }
}
