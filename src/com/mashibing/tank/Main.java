package com.mashibing.tank;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        TankFrame tankFrame = new TankFrame();
        int initTankCount = PropertyManager.getInt("initTankCount");
        // Initialize opponent tank
        for (int i = 0; i < initTankCount; i++) {
            tankFrame.opponentTanks.add(new Tank(50 + i*80, 200, Direction.DOWN, Group.BAD, tankFrame));
        }

//        new Thread(() -> new Audio("audio/war1.wav").loop()).start();

        while(true) {
            Thread.sleep(50);
            tankFrame.repaint();
        }
    }
}
