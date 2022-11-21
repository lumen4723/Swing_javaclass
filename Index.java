package Swing_javaclass;

import java.awt.*;

import java.io.File;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Index {
    JFrame frame;
    JPanel panel;
    JButton start1;
    JButton start2;

    int WIDTH = 1024;
    int HEIGHT = 720;
    
    public Index() {
        frame = new JFrame("Taigo");
        panel = new panel3();

        frame.setSize(WIDTH, HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);

        start1 = new JButton("ROKI 시작");
        panel.add(start1);
        frame.setVisible(true);

        start2 = new JButton("bad-elixir 시작");
        panel.add(start2);
        frame.setVisible(true);
    }

    public noteTape start() {
        File song = null;
        File note = null;
        ArrayList<circle> blocks = new ArrayList<circle>();

        while(true) {
            start1.addActionListener(e -> {start1.setVisible(false); frame.setVisible(false);}); //e는 ActionEvent

            if(!start1.isVisible()) {
                song = new File("./src/Swing_javaclass/music/ROKI.wav");
                for(int i = 0; i < 100; i++) {
                    blocks.add(new circle(i*300, 4 + i%2, i%4));
                }
                break;
            }

            start2.addActionListener(e -> {start2.setVisible(false); frame.setVisible(false);});

            if(!start2.isVisible()) {
                song = new File("./src/Swing_javaclass/music/bad-elixir.wav");
                for(int i = 0; i < 100; i++) {
                    blocks.add(new circle(i*300, 4 + i%2, i%4));
                }
                break;
            }
        }
        
        return new noteTape(song, blocks);
    }

    class panel3 extends JPanel {
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            g.setColor(Color.black);
            g.setFont(new Font("맑은 고딕", 1, 30));
            g.drawString("Taigo", 100, 100);
        }
    }
}
