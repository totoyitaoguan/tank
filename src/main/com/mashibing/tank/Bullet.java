package com.mashibing.tank;

import java.awt.*;

public class Bullet {
    private static final int SPEED = PropertyManager.getInt("bulletSpeed");
    public static final int WIDTH = ResourceManager.bulletD.getWidth();
    public static final int HEIGHT = ResourceManager.bulletD.getHeight();
    private int x, y;
    Rectangle rectangle = new Rectangle();
    private Direction direction;
    private Group group;
    private boolean isLive = true;
    private GameModelFacade gameModelFacade;

    public Bullet(int x, int y, Direction direction, Group group, GameModelFacade gameModelFacade) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.group = group;
        this.gameModelFacade = gameModelFacade;

        rectangle.x = this.x;
        rectangle.y = this.y;
        rectangle.width = WIDTH;
        rectangle.height = HEIGHT;

        gameModelFacade.bullets.add(this);
    }

    public void paint(Graphics g) {
        if (!isLive) {
            gameModelFacade.bullets.remove(this);
        }

        Image bulletImg;
        switch (direction) {
            case LEFT:
                bulletImg = ResourceManager.bulletL;
                break;
            case UP:
                bulletImg = ResourceManager.bulletU;
                break;
            case RIGHT:
                bulletImg = ResourceManager.bulletR;
                break;
            case DOWN:
                bulletImg = ResourceManager.bulletD;
                break;
            default:
                bulletImg = null;
        }
        g.drawImage(bulletImg, x, y, null);
        move();
    }

    private void move() {
        switch (direction) {
            case LEFT:
                x -= SPEED;
                break;
            case UP:
                y -= SPEED;
                break;
            case RIGHT:
                x += SPEED;
                break;
            case DOWN:
                y += SPEED;
                break;
            default:
                break;
        }

        // update rectangle
        rectangle.x = this.x;
        rectangle.y = this.y;

        if (x < 0 || y < 0 || x > TankFrame.GAME_WIDTH || y > TankFrame.GAME_HEIGHT) {
            isLive = false;
        }
    }

    public void collideWith(Tank tank) {
        // bullets will do no harm to the tanks in the same group
        if (this.group == tank.getGroup())
            return;

        if (this.rectangle.intersects(tank.getRectangle())) {
            tank.die();
            this.die();
            int eX = tank.getX() + Tank.WIDTH/2 - Explosion.WIDTH/2;
            int eY = tank.getY() + Tank.HEIGHT/2 - Explosion.HEIGHT/2;
            gameModelFacade.explosions.add(new Explosion(eX, eY, gameModelFacade));
        }

    }

    private void die() {
        isLive = false;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
}