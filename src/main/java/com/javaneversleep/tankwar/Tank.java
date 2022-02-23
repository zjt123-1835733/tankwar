package com.javaneversleep.tankwar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class Tank {

    private int x;
    private int y;
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
        this.x = x;
        this.y = y;
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
        switch (direction) {
            case UP:
                return new ImageIcon("assets/images/tankU.gif").getImage();
            case UPLEFT:
                return new ImageIcon("assets/images/tankLU.gif").getImage();
            case UPRIGHT:
                return new ImageIcon("assets/images/tankRU.gif").getImage();
            case DOWN:
                return new ImageIcon("assets/images/tankD.gif").getImage();
            case DOWNLEFT:
                return new ImageIcon("assets/images/tankLD.gif").getImage();
            case DOWNRIGHT:
                return new ImageIcon("assets/images/tankRD.gif").getImage();
            case LEFT:
                return new ImageIcon("assets/images/tankL.gif").getImage();
            case RIGHT:
                return new ImageIcon("assets/images/tankR.gif").getImage();
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
