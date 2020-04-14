package com.mashibing.tank.factory;

import com.mashibing.tank.Direction;
import com.mashibing.tank.Group;
import com.mashibing.tank.TankFrame;

public abstract class GameFactory {
    public abstract BaseTank createTank(int x, int y, Direction direction, Group group, TankFrame tankFrame);
    public abstract BaseExplosion createExplosion(int x, int y, TankFrame tankFrame);
    public abstract BaseBullet createBullet(int x, int y, Direction direction, Group group, TankFrame tankFrame);
}
