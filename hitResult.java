package Swing_javaclass;

import java.awt.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class hitResult {
    JFrame frame = new JFrame("Taigo");
    JPanel panel = new panel2();
    JButton next;

    int WIDTH = 1024;
    int HEIGHT = 720;
    
    int score;
    int gauge;
    String rank;

    public hitResult(int score, int gauge) {
        this.score = score;
        this.gauge = gauge;
        if(gauge >= 70){
            if(score >= 90000) rank = "SSS";
            else if(score >= 80000) rank = "SS";
            else if(score >= 70000) rank = "S";
            else if(score >= 60000) rank = "A";
            else if(score >= 50000) rank = "B";
            else if(score >= 40000) rank = "C";
            else if(score >= 30000) rank = "D";
            else rank = "E";
        }
        else{
            rank = "F";
        }
    }

    public void show() {
        frame.setSize(WIDTH, HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);

        frame.setVisible(true);

        next = new JButton("다음곡");
        panel.add(next);

        frame.setVisible(true);

        while(true) {
            next.addActionListener(e -> {frame.setVisible(false);}); //e는 ActionEvent
            if(!frame.isVisible()) {
                break;
            }
        }
    }

    class panel2 extends JPanel {
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            g.setColor(Color.black);
            g.setFont(new Font("맑은 고딕", 1, 30));
            g.drawString("점수 : " + score, 100, 100);

            g.setColor(Color.black);
            g.setFont(new Font("맑은 고딕", 1, 30));
            g.drawString("게이지 : " + gauge, 100, 200);

            g.setColor(Color.black);
            g.setFont(new Font("맑은 고딕", 1, 100));
            g.drawString(rank, 100, 300);

        }
    }
}
