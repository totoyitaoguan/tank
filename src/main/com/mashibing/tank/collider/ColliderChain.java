package com.mashibing.tank.collider;

import com.mashibing.tank.GameObject;

import java.util.LinkedList;
import java.util.List;

// Chain of responsibility
public class ColliderChain implements Collider{
    private List<Collider> colliders = new LinkedList<>();

    public ColliderChain() {
        add(new BulletTankCollider());
        add(new TankTankCollider());
    }

    public void add(Collider collider) {
        colliders.add(collider);
    }

    public boolean collide(GameObject o1, GameObject o2) {
        for (int i = 0; i < colliders.size(); i++) {
            // stop looping the chain when fatal collision already happened
            if (colliders.get(i).collide(o1, o2)) {
                return true;
            }
        }
        return false;
    }
}
