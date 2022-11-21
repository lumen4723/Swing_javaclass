package Swing_javaclass;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TAIGO {
    JFrame frame = new JFrame("Taigo");
    int hitNotice = 0; // 0: none, 1: 얼쑤, 2: 좋다, 3: 에구

    int WIDTH = 1024;
    int HEIGHT = 720;
    int speed = 10;
    boolean outleft = false;
    boolean outright = false;
    boolean inleft = false;
    boolean inright = false;
    int combo = 0;
    int gauge = 0;
    int score = 0;
    ArrayList<circle> blocks;
    int songlength;
    File sounddung;
    File soundddack;
    File soundsong;
    AudioInputStream dung;
    AudioInputStream ddack;
    AudioInputStream song;
    Clip clip;
    
    public TAIGO(noteTape tape) {
        // blocks = new ArrayList<circle>(); //여기에 tape.block들어감
        blocks = tape.blocks;

        try{
            sounddung = new File("./src/Swing_javaclass/music/dung.wav");
            soundddack = new File("./src/Swing_javaclass/music/ddack.wav");
            // soundsong = new File("./src/Swing_javaclass/music/ROKI.wav"); // 여기에 tape.song들어감
            soundsong = tape.song;
        }
        catch(Exception e){
            e.printStackTrace();
        }

        frame.setSize(WIDTH, HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new Panel1();

        frame.getContentPane().add(panel);
        frame.setVisible(true);
        frame.addKeyListener(new GameKeyListner());
        frame.setVisible(true);

    }

    public hitResult go() {
        int timecounter = -1; // -1: none, 0: reset, other: count

        try{
            song = AudioSystem.getAudioInputStream(soundsong);
            clip = AudioSystem.getClip();
            clip.open(song);
            clip.start();
            songlength = (int) (clip.getMicrosecondLength() / 1000);
        }catch(Exception ex){
            ex.printStackTrace();
        }

        while(true) {
            Iterator<circle> it = blocks.iterator();
            while(it.hasNext()) {
                circle c = it.next();

                c.x -= c.speed;
                double predict = c.x + (c.r - 100) / 2;

                if( 330 <= predict && predict <= 370 ) {
                    if(c.type % 2 == 1 && (inleft || inright)) {
                        it.remove();
                        gauge++;
                        combo++;
                        hitNotice = 340 <= predict && predict <= 360 ? 1 : 2;
                        score += hitNotice == 1 ? 500 : 250;
                        timecounter = 100;
                        // System.out.println("쿵" + gauge);                        
                    }
                    else if(c.type % 2 == 0 && (outleft || outright)) {
                        it.remove();
                        gauge++;
                        combo++;
                        hitNotice = 340 <= predict && predict <= 360 ? 1 : 2;
                        score += hitNotice == 1 ? 500 : 250;
                        timecounter = 100;
                        //System.out.println("딱" + gauge);
                    }
                    continue;
                }
                else if( 330 > predict ) {
                    it.remove();
                    gauge -= 5 - (combo < 60 ? combo : 60) / 15;
                    gauge = gauge < 0 ? 0 : gauge;
                    combo = 0;
                    hitNotice = 3;
                    timecounter = 100;
                    //System.out.println("실패");
                }
                
            }
            
            try {
                Thread.sleep(speed);

                if(timecounter > 0) {
                    timecounter--;
                }
                else if (timecounter == 0) {
                    hitNotice = 0;
                    timecounter = -1;
                }

                if(songlength > 0) {
                    songlength -= 16;
                    System.out.println(songlength);
                }
                else {
                    break;
                }

            } catch (InterruptedException e) {}
            frame.repaint();
        }

        frame.setVisible(false);
        return new hitResult(score, gauge);
    }

    class Panel1 extends JPanel {
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            int WIDTH = 1010;
            // int HEIGHT = 720;

            //태고 노트
            g.setColor(Color.darkGray);
            g.fillRect(300, 200, WIDTH-300, 160);
            g.setColor(Color.black);
            g.drawRect(300, 200, WIDTH-300, 160);

            g.setColor(Color.gray);
            g.fillRect(300, 360, WIDTH-300, 40);
            g.setColor(Color.black);
            g.drawRect(300, 360, WIDTH-300, 40);

            //태고 북 라인
            g.setColor(Color.orange);
            g.fillRect(0, 200, 300, 200);
            g.setColor(Color.black);
            g.drawRect(0, 200, 300, 200);

            //태고 스코어 라인
            g.setColor(Color.gray);
            g.fillRect(0, 200, 120, 50);
            g.setColor(Color.black);
            g.drawRect(0, 200, 120, 50);

            //태고 게이지
            g.setColor(Color.gray);
            g.fillRect(450, 150, WIDTH-450, 50);
            g.setColor(Color.black);
            g.fillRect(455, 155, WIDTH-510, 40);
            // combo에 따라 게이지 색깔 바꾸기

            if( gauge < 70 ){
                g.setColor(Color.red);
                g.fillRect(455, 155, (WIDTH-510) * (gauge > 70 ? 70 : gauge) / 100, 40);
            }
            else if( gauge < 95 ){
                g.setColor(Color.yellow);
                g.fillRect(455, 155, (WIDTH-510) * (gauge > 95 ? 95 : gauge) / 100, 40);
            }
            else{
                g.setColor(Color.MAGENTA);
                g.fillRect(455, 155, (WIDTH-510) * (gauge > 100 ? 100 : gauge) / 100, 40);
            }

            // 태고북 판정지점
            g.setColor(Color.gray);
            // g.fillOval(320, 120, 160, 160);
            g.fillOval(320, 210, 140, 140);

            g.setColor(Color.darkGray);
            g.fillOval(325, 215, 130, 130);

            // 태고북 판정지점 안쪽
            g.setColor(Color.gray);
            g.fillOval(350, 240, 80, 80);

            //태고북 테두리
            g.setColor(Color.BLACK);
            g.fillOval(120, 210, 170, 170);

            //태고북 바깥 왼쪽
            g.setColor(outleft ? Color.black : Color.blue);
            g.fillArc(125, 215, 160, 160, 90, 180);

            //태고북 바깥 오른쪽
            g.setColor(outright ? Color.black : Color.blue);
            g.fillArc(125, 215, 160, 160, 270, 180);

            //태고북 안쪽 왼쪽
            g.setColor(inleft ? Color.black : Color.red);
            g.fillArc(145, 235, 120, 120, 90, 180);

            //태고북 안쪽 오른쪽
            g.setColor(inright ? Color.black : Color.red);
            g.fillArc(145, 235, 120, 120, 270, 180);

            // 태고 블럭노트
            for(int i=0; i < blocks.size(); i++){
                circle c = blocks.get(i);
                g.setColor(c.color);
                g.fillOval(c.x, c.y, c.r, c.r);

                g.setColor(c.color);
                g.setFont(new Font("맑은 고딕", 1, 30));
                g.drawString((c.type % 2 == 1 ? "쿵" : "딱"), c.x + (c.r / 2), 390);
                // g.fillRect(c.x + (c.r) / 2, 280, 20, 20);
            }

            g.setColor(Color.white);
            g.setFont(new Font("맑은 고딕", 1, 20));
            g.drawString("" + score, 30, 230);

            if(combo > 0) {
                g.setColor(Color.yellow);
                g.setFont(new Font("맑은 고딕", 1, 80));
                if(combo >= 100){
                    g.drawString("" + combo, 135, 320);
                }
                else if (combo >= 10){
                    g.drawString("" + combo, 160, 320);
                }
                else{
                    g.drawString("" + combo, 180, 320);
                }
            }

            if(hitNotice == 1) {
                g.setColor(Color.yellow);
                g.setFont(new Font("맑은 고딕", 1, 30));
                g.drawString("얼쑤", 360, 190);
            }
            else if(hitNotice == 2) {
                g.setColor(Color.gray);
                g.setFont(new Font("맑은 고딕", 1, 30));
                g.drawString("좋다", 360, 190);
            }
            else if(hitNotice == 3) {
                g.setColor(Color.darkGray);
                g.setFont(new Font("맑은 고딕", 1, 30));
                g.drawString("에구", 360, 190);
            }
        }
    }

    class GameKeyListner implements KeyListener {
        void playsound_dung(){
            try{
                dung = AudioSystem.getAudioInputStream(sounddung);
                clip = AudioSystem.getClip();
                clip.stop();
                clip.open(dung);
                clip.start();
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
        void playsound_ddack(){
            try{
                ddack = AudioSystem.getAudioInputStream(soundddack);
                clip = AudioSystem.getClip();
                clip.stop();
                clip.open(ddack);
                clip.start();
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }

        public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();
            switch(keyCode) {
                case KeyEvent.VK_A:
                    outleft = true;
                    playsound_ddack();
                    break;
                case KeyEvent.VK_S:
                    inleft = true;
                    playsound_dung();
                    break;
                case KeyEvent.VK_D:
                    inright = true;
                    playsound_dung();
                    break;
                case KeyEvent.VK_F:
                    outright = true;
                    playsound_ddack();
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
}
