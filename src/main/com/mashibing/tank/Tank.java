package com.mashibing.tank;

import java.awt.*;
import java.util.Random;

public class Tank {
    private static final int SPEED = PropertyManager.getInt("tankSpeed");
    public static final int WIDTH = ResourceManager.goodTankD.getWidth();
    public static final int HEIGHT = ResourceManager.goodTankD.getHeight();
    private int x, y;
    Rectangle rectangle = new Rectangle();
    private Direction direction;
    private Group group;
    private boolean isMoving = true;
    private boolean isLive = true;
    private GameModelFacade gameModelFacade;
    private Random random = new Random();
    private FireMode fireMode;

    public Tank(int x, int y, Direction direction, Group group, GameModelFacade gameModelFacade) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.group = group;
        this.gameModelFacade = gameModelFacade;

        rectangle.x = this.x;
        rectangle.y = this.y;
        rectangle.width = WIDTH;
        rectangle.height = HEIGHT;

        try {
            if (group == Group.GOOD) {
                String goodFireMode = PropertyManager.getString("goodFireMode");
                fireMode = (FireMode) Class.forName(goodFireMode).getDeclaredConstructor().newInstance();
            } else {
                String badFireMode = PropertyManager.getString("badFireMode");
                fireMode = (FireMode) Class.forName(badFireMode).getDeclaredConstructor().newInstance();
            }
        } catch (Exception e) {
            fireMode = new DefaultFireMode();
            System.out.println("Cannot load fire mode from config file, using default.\n" + e.getMessage());
        }
    }

    public void paint(Graphics g) {
        if (!isLive) {
            gameModelFacade.opponentTanks.remove(this);
        }
        Image tankImg;
        switch (direction) {
            case LEFT:
                tankImg = this.group == Group.GOOD ? ResourceManager.goodTankL : ResourceManager.badTankL;
                break;
            case UP:
                tankImg = this.group == Group.GOOD ? ResourceManager.goodTankU : ResourceManager.badTankU;
                break;
            case RIGHT:
                tankImg = this.group == Group.GOOD ? ResourceManager.goodTankR : ResourceManager.badTankR;
                break;
            case DOWN:
                tankImg = this.group == Group.GOOD ? ResourceManager.goodTankD : ResourceManager.badTankD;
                break;
            default:
                tankImg = null;
        }
        g.drawImage(tankImg, x, y, null);
        move();
    }

    private void move() {
        if (!isMoving)
            return;
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

        // randomize opponent tanks' movement and attack
        if (this.group == Group.BAD && random.nextInt(100) > 95) {
            this.fire(fireMode);
            randomizeDirection();
        }

        // detect bounds
        checkBounds();

        // update rectangle
        rectangle.x = this.x;
        rectangle.y = this.y;
    }

    private void checkBounds() {
        if (this.x < 0) x = 0;
        if (this.y < 30) y = 30;
        if (this.x > TankFrame.GAME_WIDTH - Tank.WIDTH) x = TankFrame.GAME_WIDTH - Tank.WIDTH;
        if (this.y > TankFrame.GAME_HEIGHT - Tank.HEIGHT) y = TankFrame.GAME_HEIGHT - Tank.HEIGHT;
    }

    private void randomizeDirection() {
        this.direction = Direction.values()[random.nextInt(4)];
    }

    // Strategy Design Pattern
    public void fire(FireMode fireMode) {
        fireMode.fire(this);
   }

    public void die() {
        isLive = false;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Direction getDirection() {
        return direction;
    }

    public boolean isMoving() {
        return isMoving;
    }

    public void setMoving(boolean moving) {
        this.isMoving = moving;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public GameModelFacade getGameModelFacade() {
        return gameModelFacade;
    }

    public FireMode getFireMode() {
        return fireMode;
    }
}
