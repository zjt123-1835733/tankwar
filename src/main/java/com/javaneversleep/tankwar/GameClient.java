package com.javaneversleep.tankwar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GameClient extends JComponent {
    //构造方法
    private Tank playerTank;

    private GameClient() {
        this.playerTank = new Tank(400,100, Direction.DOWN);
        this.setPreferredSize(new Dimension(800,600));
    }

    //alt + insert to override
    @Override
    protected void paintComponent(Graphics g) {
        playerTank.draw(g);
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
//                switch (e.getKeyCode()) {
//                    case KeyEvent.VK_UP:
//                        client.playerTank.setY(client.playerTank.getY() - 5);
//                        break;
//                    case KeyEvent.VK_DOWN:
//                        client.playerTank.setY(client.playerTank.getY() + 5);
//                        break;
//                    case KeyEvent.VK_LEFT:
//                        client.playerTank.setX(client.playerTank.getX() - 5);
//                        break;
//                    case KeyEvent.VK_RIGHT:
//                        client.playerTank.setX(client.playerTank.getX() + 5);
//                        break;
//                }
//                client.playerTank.move();
//            }

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
