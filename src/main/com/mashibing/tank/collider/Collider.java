package com.mashibing.tank.collider;

import com.mashibing.tank.GameObject;

public interface Collider {
    // return true when fatal collision happens
    boolean collide(GameObject o1, GameObject o2);
}
