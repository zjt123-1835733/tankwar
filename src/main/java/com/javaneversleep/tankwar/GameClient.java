package com.javaneversleep.tankwar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class GameClient extends JComponent {
    //构造方法
    private Tank playerTank;

    private List<Tank> enemyTanks;

    private List<Wall> walls;

    private GameClient() {
        this.playerTank = new Tank(400,100, false, Direction.DOWN);
        this.enemyTanks = new ArrayList<>(12);
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
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setTitle("Tank War");
        frame.setIconImage(new ImageIcon("assets/images/icon.png").getImage());
        GameClient client = new GameClient();
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
        while (true) {
            client.repaint();
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
