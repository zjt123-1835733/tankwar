package com.javaneversleep.tankwar;

import java.awt.*;

class Explosion {

    private int x, y;

    Explosion(int x, int y) {
        this.x = x;
        this.y = y;
    }

    private int step = 0;
    private boolean alive = true;

    boolean isAlive() {
        return alive;
    }

    void setAlive(boolean alive) {
        this.alive = alive;
    }

    void draw(Graphics g) {
        if (step > 10) {
            this.setAlive(false);
            return;
        }
        g.drawImage(Tools.getImage(step++ + ".gif"), x, y, null);
    }

}
