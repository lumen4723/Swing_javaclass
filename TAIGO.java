package Swing_javaclass;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class TAIGO {
    JFrame frame = new JFrame("Taigo");

    int WIDTH = 1024;
    int HEIGHT = 720;
    int speed = 10;
    boolean outleft = false;
    boolean outright = false;
    boolean inleft = false;
    boolean inright = false;
    int combo = 0;
    int score = 0;

    ArrayList<circle> circles = new ArrayList<circle>();

    public TAIGO() {
        frame.setSize(WIDTH, HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.getContentPane().add(new Panel1());
        frame.setVisible(true);
        frame.addKeyListener(new GameKeyListner());
        frame.setVisible(true);
    }

    public void go() {
        for(int i = 0; i < 100; i++) {
            circles.add(new circle(i*300, 4 + i%2, i%4));
        }

        while(true) {
            Iterator<circle> it = circles.iterator();
            while(it.hasNext()) {
                circle c = it.next();
                c.x -= c.speed;
                double predict = c.x + (c.r - 100) / 2;
                if( 330 <= predict && predict <= 370 ) {
                    if(c.type % 2 == 1 && (inleft || inright)) {
                        it.remove();
                        score++;
                        combo++;
                        System.out.println("쿵" + score);
                    }
                    else if(c.type % 2 == 0 && (outleft || outright)) {
                        it.remove();
                        score++;
                        combo++;
                        System.out.println("딱" + score);
                    }
                    continue;
                }
                else if( 330 > predict ) {
                    it.remove();
                    score -= 20 - (combo > 95 ? combo : 95) / 5;
                    score = score < 0 ? 0 : score;
                    combo = 0;
                    System.out.println("실패");
                }
                
            }
            
            try {
                Thread.sleep(speed);
            } catch (InterruptedException e) {}
            frame.repaint();
        }
    }

    class Panel1 extends JPanel {
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            //태고 노트
            g.setColor(Color.darkGray);
            g.fillRect(200, 100, 1024, 200);

            //태고 게이지
            g.setColor(Color.gray);
            g.fillRect(200, 50, 800, 50);
            // combo에 따라 게이지 색깔 바꾸기

            if(score < 70) {
                g.setColor(Color.red);
            }
            else{
                g.setColor(Color.yellow);
            }
            g.fillRect(200, 50, 700 * (score > 100 ? 100 : score) / 100, 50);

            // 태고북 판정지점
            g.setColor(Color.black);
            // g.fillOval(320, 120, 160, 160);
            g.drawOval(320, 120, 160, 160);

            // 태고북 판정지점 안쪽
            g.setColor(Color.white);
            g.fillOval(350, 150, 100, 100);

            //태고북 테두리
            g.setColor(Color.BLACK);
            g.fillOval(100, 100, 200, 200);

            //태고북 바깥 왼쪽
            g.setColor(outleft ? Color.black : Color.blue);
            g.fillArc(105, 105, 190, 190, 90, 180);

            //태고북 바깥 오른쪽
            g.setColor(outright ? Color.black : Color.blue);
            g.fillArc(105, 105, 190, 190, 270, 180);

            //태고북 안쪽 왼쪽
            g.setColor(inleft ? Color.black : Color.red);
            g.fillArc(125, 125, 150, 150, 90, 180);

            //태고북 안쪽 오른쪽
            g.setColor(inright ? Color.black : Color.red);
            g.fillArc(125, 125, 150, 150, 270, 180);

            // // 태고 블럭
            // g.setColor(Color.black);
            // g.fillOval(500 - x, 150, 100, 100);

            // g.setColor(Color.black);
            // g.fillOval(800 - x, 150, 100, 100);

            // g.setColor(Color.black);
            // g.fillOval(1300 - x, 150, 100, 100);

            // 태고 블럭노트
            for(int i=0; i < circles.size(); i++){
                circle c = circles.get(i);
                g.setColor(c.color);
                g.fillOval(c.x, c.y, c.r, c.r);
            }
        }
    }

    class GameKeyListner implements KeyListener {
        public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();
            switch(keyCode) {
                case KeyEvent.VK_A:
                    outleft = true;
                    break;
                case KeyEvent.VK_S:
                    inleft = true;
                    break;
                case KeyEvent.VK_D:
                    inright = true;
                    break;
                case KeyEvent.VK_F:
                    outright = true;
                    break;
            }
        }

        public void keyReleased(KeyEvent e) {
            int keyCode = e.getKeyCode();
            switch(keyCode) {
                case KeyEvent.VK_A:
                    outleft = false;
                    break;
                case KeyEvent.VK_S:
                    inleft = false;
                    break;
                case KeyEvent.VK_D:
                    inright = false;
                    break;
                case KeyEvent.VK_F:
                    outright = false;
                    break;
            }
        }

        public void keyTyped(KeyEvent e) {
        }
    }

    public class circle {
        int x;
        int y;
        int r;
        int speed;
        int type;
        Color color;

        public circle(int x, int speed, int type) {
            this.x = x;         // x시작좌표
            this.speed = speed; // 속도
            this.type = type;   // 타입 0 = 태고딱소, 1. 태고쿵소, 2. 태고딱대, 3. 태고쿵대
            r = type < 2 ? 100 : 160; // 반지름
            y = type < 2 ? 150 : 120; // y좌표
            color = type % 2 == 1 ? Color.red : Color.blue; // 색깔
        }
    }
}
