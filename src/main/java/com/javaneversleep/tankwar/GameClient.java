package com.javaneversleep.tankwar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class GameClient extends JComponent {

    private static final GameClient INSTANCE = new GameClient();
    // 单例模式，为了能够在Tank类中访问Wall
    static GameClient getInstance() {
        return INSTANCE; // 返回实例静态常量
    }

    //构造方法
    private Tank playerTank;

    private List<Tank> enemyTanks;

    private List<Wall> walls;

    private List<Missile> missiles;

    List<Tank> getEnemyTanks() {
        return enemyTanks;
    }

    List<Missile> getMissiles() {
        return missiles;
    }

    List<Wall> getWalls() {
        return walls;
    }

    private GameClient() {
        this.playerTank = new Tank(400,100, false, Direction.DOWN);
        this.enemyTanks = new ArrayList<>(12);
        this.missiles = new ArrayList<>();
        this.walls = Arrays.asList(
            new Wall(200, 140, true, 15),
            new Wall(200, 540, true, 15),
            new Wall(100, 80, false, 15),
            new Wall(700, 80, false, 15)
        );
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                this.enemyTanks.add(new Tank(200 + j * 120, 400 + 40 * i, true, Direction.UP));
            }
        }
        this.setPreferredSize(new Dimension(800,600));
    }

    //alt + insert to override
    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 800, 600);
        playerTank.draw(g);
        for (Tank tank: enemyTanks) {
            tank.draw(g);
        }
        for (Wall wall: walls) {
            wall.draw(g);
        }
        for (Missile missile: missiles) {
            missile.draw(g);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setTitle("Tank War");
        frame.setIconImage(new ImageIcon("assets/images/icon.png").getImage());
        // 发射炮弹时，一个对象出现了两个实例，导致没有炮弹显示
        final GameClient client = GameClient.getInstance(); //单例，不要new GameClient
        client.repaint(); //不知道干嘛
        frame.add(client);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack(); //自适应
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                client.playerTank.keyPressed(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                client.playerTank.keyReleased(e);
            }
        });
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        //另起一个线程
        //noinspection InfiniteLoopStatement
        while (true) {
            client.repaint();
            try {
                //noinspection BusyWait
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
