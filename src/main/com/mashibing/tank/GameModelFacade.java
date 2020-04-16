package com.mashibing.tank;

import com.mashibing.tank.collider.BulletTankCollider;
import com.mashibing.tank.collider.Collider;
import com.mashibing.tank.collider.ColliderChain;
import com.mashibing.tank.collider.TankTankCollider;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GameModelFacade {
    Tank myTank = new Tank(200, 400, Direction.DOWN, Group.GOOD, this);
//    List<Bullet> bullets = new ArrayList<>();
//    List<Tank> opponentTanks = new ArrayList<>();
//    List<Explosion> explosions = new ArrayList<>();
    ColliderChain colliderChain = new ColliderChain();
    private List<GameObject> gameObjects = new ArrayList<>();

    public GameModelFacade() {
        int initTankCount = PropertyManager.getInt("initTankCount");
        // Initialize opponent tank
        for (int i = 0; i < initTankCount; i++) {
            add(new Tank(50 + i*100, 200, Direction.DOWN, Group.BAD, this));
        }
    }

    public void add(GameObject gameObject) {
        this.gameObjects.add(gameObject);
    }

    public void remove(GameObject gameObject) {
        this.gameObjects.remove(gameObject);
    }

    public void paint(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.WHITE);
//        g.drawString("Bullets: " + bullets.size(), 10, 60);
//        g.drawString("Opponent Tanks: " + opponentTanks.size(), 10, 80);
//        g.drawString("Explosions: " + explosions.size(), 10, 100);
        g.setColor(c);

        myTank.paint(g);
        for (int i = 0; i < gameObjects.size(); i++) {
            gameObjects.get(i).paint(g);
        }

        // check if bullet and opponent tanks collide
        for (int i = 0; i < gameObjects.size(); i++) {
            for (int j = 0; j < gameObjects.size(); j++) {
                GameObject o1 = gameObjects.get(i);
                GameObject o2 = gameObjects.get(j);
                colliderChain.collide(o1 ,o2);
            }
        }

//        for (int i = 0; i < bullets.size(); i++) {
//            for (int j = 0; j < opponentTanks.size(); j++) {
//                bullets.get(i).collideWith(opponentTanks.get(j));
//            }
//        }
    }

    public Tank getMainTank() {
        return myTank;
    }
}
