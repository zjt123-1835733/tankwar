package com.javaneversleep.tankwar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class Tank {

    private int x;
    private int y;
    private boolean enemy;
    private Direction direction;

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Tank(int x, int y, Direction direction) {
        this(x, y, false, direction);
    }

    public Tank(int x, int y, boolean enemy, Direction direction) {
        this.x = x;
        this.y = y;
        this.enemy = enemy;
        this.direction = direction;
    }

    void move() {
        // ctrl + alt + L 代码格式化
        if (this.stopped) return;
        switch (direction) {
            case UP:
                y -= 5;
                break;
            case UPLEFT:
                y -= 5;
                x -= 5;
                break;
            case UPRIGHT:
                y -= 5;
                x += 5;
                break;
            case DOWN:
                y += 5;
                break;
            case DOWNLEFT:
                y += 5;
                x -= 5;
                break;
            case DOWNRIGHT:
                y += 5;
                x += 5;
                break;
            case LEFT:
                x -= 5;
                break;
            case RIGHT:
                x += 5;
                break;
        }
    }

    Image getImage() {
        // ctrl + shift + R 进行替换
        String prefix = enemy ? "e" : "";
        switch (direction) {
            case UP:
                return Tools.getImage(prefix + "tankU.gif");
            case UPLEFT:
                return Tools.getImage(prefix + "tankLU.gif");
            case UPRIGHT:
                return Tools.getImage(prefix + "tankRU.gif");
            case DOWN:
                return Tools.getImage(prefix + "tankD.gif");
            case DOWNLEFT:
                return Tools.getImage(prefix + "tankLD.gif");
            case DOWNRIGHT:
                return Tools.getImage(prefix + "tankRD.gif");
            case LEFT:
                return Tools.getImage(prefix + "tankL.gif");
            case RIGHT:
                return Tools.getImage(prefix + "tankR.gif");
        }
        return null;
    }

    void draw(Graphics g) {
        //调用父类
        //super.paintComponent(g);
        //相对路径定位文件，正斜杠简化操作
        this.determineDirection();
        this.move();
        g.drawImage(this.getImage(), this.x, this.y, null);
    }

    private boolean up, down, left, right;

    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                up = true;
                break;
            case KeyEvent.VK_DOWN:
                down = true;
                break;
            case KeyEvent.VK_LEFT:
                left = true;
                break;
            case KeyEvent.VK_RIGHT:
                right = true;
                break;
        }
    }

    private boolean stopped;

    private void determineDirection() {
        if (!up && !left && !down && !right) {
            this.stopped = true;
        } else {
            if (up && left && !down && !right)
                this.direction = Direction.UPLEFT;
            else if (up && !left && !down && right)
                this.direction = Direction.UPRIGHT;
            else if (!up && left && down && !right)
                this.direction = Direction.DOWNLEFT;
            else if (!up && !left && down && right)
                this.direction = Direction.DOWNRIGHT;
            else if (up && !left && !down && !right)
                this.direction = Direction.UP;
            else if (!up && !left && down && !right)
                this.direction = Direction.DOWN;
            else if (!up && left && !down && !right)
                this.direction = Direction.LEFT;
            else if (!up && !left && !down && right)
                this.direction = Direction.RIGHT;

            this.stopped = false;
        }
    }

    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                up = false;
                break;
            case KeyEvent.VK_DOWN:
                down = false;
                break;
            case KeyEvent.VK_LEFT:
                left = false;
                break;
            case KeyEvent.VK_RIGHT:
                right = false;
                break;
        }
        this.determineDirection();

    }
}
