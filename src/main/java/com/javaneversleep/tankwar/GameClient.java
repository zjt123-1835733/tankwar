package com.javaneversleep.tankwar;

import javax.swing.*;
import java.awt.*;

public class GameClient extends JComponent {
    //构造方法
    private GameClient() {
        this.setPreferredSize(new Dimension(800,600));
    }

    //alt + insert to override
    @Override
    protected void paintComponent(Graphics g) {
        //调用父类
        //super.paintComponent(g);
        //相对路径定位文件，正斜杠简化操作
        g.drawImage(new ImageIcon("assets/images/tankD.gif").getImage(),
            400, 100, null);
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
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
