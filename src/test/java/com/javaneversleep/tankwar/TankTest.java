package com.javaneversleep.tankwar;

import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class TankTest {

    @Test

    void getImage() {
        for (Direction direction : Direction.values()) {
            Tank tank = new Tank(0, 0, false, direction);
            // assertTrue 可以加报错message 替代try catch结构
            assertTrue(tank.getImage().getWidth(null) > 0, direction + "cannot get valid image!");

            Tank enemyTank = new Tank(0, 0, true, direction);
            Image image = enemyTank.getImage(); //目前只能测试有没有，不能测试对不对
            assertTrue(image.getWidth(null) > 0, direction + "cannot get valid image!");
        }
    }
}