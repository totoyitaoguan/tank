package com.mashibing.tank.collider;

import com.mashibing.tank.*;

public class BulletTankCollider implements Collider {
    @Override
    public boolean collide(GameObject o1, GameObject o2) {
        if (o1 instanceof Bullet && o2 instanceof Tank) {
            Bullet bullet = (Bullet) o1;
            Tank tank = (Tank) o2;

            // bullets will do no harm to the tanks in the same group
            if (bullet.getGroup() == tank.getGroup())
                return false;

            if (bullet.getRectangle().intersects(tank.getRectangle())) {
                tank.die();
                bullet.die();
                int eX = tank.getX() + Tank.WIDTH/2 - Explosion.WIDTH/2;
                int eY = tank.getY() + Tank.HEIGHT/2 - Explosion.HEIGHT/2;
                GameModelFacade gameModelFacade = bullet.getGameModelFacade();
                gameModelFacade.add(new Explosion(eX, eY, gameModelFacade));
                return true;
            } else {
                return false;
            }
        } else if (o1 instanceof Tank && o2 instanceof Bullet) {
            return collide(o2, o1);
        } else {
            return false;
        }
    }
}
