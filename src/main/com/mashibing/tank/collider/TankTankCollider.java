package com.mashibing.tank.collider;

import com.mashibing.tank.GameObject;
import com.mashibing.tank.Tank;

public class TankTankCollider implements Collider {
    @Override
    public boolean collide(GameObject o1, GameObject o2) {
        if (o1 instanceof Tank && o2 instanceof Tank && o1 != o2) {
            Tank tank1 = (Tank) o1;
            Tank tank2 = (Tank) o2;
            if (tank1.getRectangle().intersects(tank2.getRectangle())) {
                tank1.stop();
//                tank2.stop();
            }
        }
        // not fatal collision, returning true so that collision continues
        return true;
    }
}
