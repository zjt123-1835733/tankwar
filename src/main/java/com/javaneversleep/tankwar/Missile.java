package com.javaneversleep.tankwar;

import java.awt.*;

class Missile {

    private static final int SPEED = 12;
    private int x;
    private int y;
    private final boolean enemy;
    private final Direction direction;

    boolean isAlive() {
        return alive;
    }

    void setAlive(boolean alive) {
        this.alive = alive;
    }

    private boolean alive = true;

    public Missile(int x, int y, boolean enemy, Direction direction) {
        this.x = x;
        this.y = y;
        this.enemy = enemy;
        this.direction = direction;
    }

    // 约定优于配置
    private Image getImage() {
        return direction.getImage("missile");
    }

    void move() {
        x += direction.xFactor * SPEED;
        y += direction.yFactor * SPEED;
    }

    void draw(Graphics g) {
        move();
        if ( x < 0 || x > 800 || y < 0 || y > 600) {
            this.alive = false;
            return;
        }

        Rectangle rectangle = this.getRectangle();
        for (Wall wall : GameClient.getInstance().getWalls()) {
            if (rectangle.intersects(wall.getRectangle())) {
                this.setAlive(false);
                return;
            }
        }

        if (enemy) {
            Tank playerTank = GameClient.getInstance().getPlayerTank();
            if (rectangle.intersects(playerTank.getRectangle())) {
                playerTank.setHp(playerTank.getHp() - 20);
                if (playerTank.getHp() <= 0) {
                    playerTank.setAlive(false);
                }
                this.setAlive(false);
            }
        } else {
            for (Tank tank : GameClient.getInstance().getEnemyTanks()) {
                if (rectangle.intersects(tank.getRectangle())) {
                    tank.setAlive(false);
                    this.setAlive(false);
                    break;
                }
            }
        }

        g.drawImage(getImage(), x, y, null);
    }

    Rectangle getRectangle() {
        return new Rectangle(x, y, getImage().getWidth(null), getImage().getHeight(null));
    }
}
