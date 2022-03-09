package com.javaneversleep.tankwar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;


public class GameClient extends JComponent {

    private static final GameClient INSTANCE = new GameClient();
    // 单例模式，为了能够在Tank类中访问Wall
    static GameClient getInstance() {
        return INSTANCE; // 返回实例静态常量
    }

    //构造方法
    private Tank playerTank;

    private List<Tank> enemyTanks;

    private final AtomicInteger enemyKilled = new AtomicInteger(0);

    private List<Wall> walls;

    private List<Missile> missiles;

    private List<Explosion> explosions;

    void addExplosion(Explosion explosion) {
        explosions.add(explosion);
    }

    void add(Missile missile) {
        missiles.add(missile);
    }

    void removeMissile(Missile missile) {
        missiles.remove(missile);
    }

    Tank getPlayerTank() {
        return playerTank;
    }

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
        this.missiles = new CopyOnWriteArrayList<>();
        this.explosions = new ArrayList<>();
        this.walls = Arrays.asList(
            new Wall(200, 140, true, 15),
            new Wall(200, 540, true, 15),
            new Wall(100, 160, false, 12),
            new Wall(700, 160, false, 12)
        );
        this.initEnemyTanks();
        this.setPreferredSize(new Dimension(800,600));
    }

    private void initEnemyTanks() {
        this.enemyTanks = new CopyOnWriteArrayList<>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                this.enemyTanks.add(new Tank(200 + j * 120, 400 + 40 * i, true, Direction.UP));
            }
        }
    }

    //alt + insert to override
    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 800, 600);
        if (!playerTank.isAlive()) {
            g.setColor(Color.RED);
            g.setFont(new Font(null, Font.BOLD, 100));
            g.drawString("GAME OVER", 100, 200);
            g.setFont(new Font(null, Font.BOLD, 60));
            g.drawString("PRESS F2 TO RESTART", 60, 360);
        } else {
            g.setColor(Color.WHITE);
            g.setFont(new Font(null, Font.BOLD, 16));
            g.drawString("Missiles: " + missiles.size(), 10, 50);
            g.drawString("Explosions: " + explosions.size(), 10, 70);
            g.drawString("Player Tank HP: " + playerTank.getHp(), 10, 90);
            g.drawString("Enemy left: " + enemyTanks.size(), 10, 110);
            g.drawString("Enemy Killed: " + enemyKilled.get(), 10, 130);

            playerTank.draw(g);

            int count = enemyTanks.size();
            enemyTanks.removeIf(t -> !t.isAlive());
            enemyKilled.addAndGet(count - enemyTanks.size());
            if (enemyTanks.isEmpty()) {
                this.initEnemyTanks();
            }
            for (Tank tank : enemyTanks) {
                tank.draw(g);
            }
            for (Wall wall : walls) {
                wall.draw(g);
            }

            // 打印线程名字测试是否是线程安全问题
            // System.out.println(Thread.currentThread().getName());
            missiles.removeIf(m -> !m.isAlive());
            for (Missile missile : missiles) {
                missile.draw(g);
            }

            explosions.removeIf(e -> !e.isAlive());
            for (Explosion explosion : explosions) {
                explosion.draw(g);
            }
        }
    }

    public static void main(String[] args) {
        com.sun.javafx.application.PlatformImpl.startup(()->{});
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
        // 每50毫秒repaint一次
        while (true) {
            // 打印线程名字测试是否是线程安全问题
            // System.out.println(Thread.currentThread().getName());
            try {
                client.repaint();
                if (client.playerTank.isAlive()) {
                    for (Tank tank : client.enemyTanks) {
                        tank.actRandomly();
                    }
                }
                //noinspection BusyWait
                Thread.sleep(50);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    void restart() {
        if (!playerTank.isAlive()) {
            playerTank = new Tank(400,100, false, Direction.DOWN);
        }
        this.initEnemyTanks();
    }
}
