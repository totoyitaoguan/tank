package com.mashibing.tank.factory;

import com.mashibing.tank.*;

import java.awt.*;

public class RectBullet extends BaseBullet {
    private static final int SPEED = PropertyManager.getInt("bulletSpeed");
    public static final int WIDTH = ResourceManager.bulletD.getWidth();
    public static final int HEIGHT = ResourceManager.bulletD.getHeight();
    private int x, y;
    Rectangle rectangle = new Rectangle();
    private Direction direction;
    private Group group;
    private boolean isLive = true;
    private TankFrame tankFrame;

    public RectBullet(int x, int y, Direction direction, Group group, TankFrame tankFrame) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.group = group;
        this.tankFrame = tankFrame;

        rectangle.x = this.x;
        rectangle.y = this.y;
        rectangle.width = WIDTH;
        rectangle.height = HEIGHT;

        tankFrame.bullets.add(this);
    }

    @Override
    public void paint(Graphics g) {
        if (!isLive) {
            tankFrame.bullets.remove(this);
        }

        Color c = g.getColor();
        g.setColor(Color.YELLOW);
        g.fillRect(x, y, 10, 10);
        g.setColor(c);

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
            tankFrame.explosions.add(tankFrame.factory.createExplosion(eX, eY, tankFrame));
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