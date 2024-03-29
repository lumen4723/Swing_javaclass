package Swing_javaclass;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class TAIGO extends MainDriver{
    int hitNotice = -1; // -1: none, 0: 얼쑤, 1: 좋다, 2: 에구
    int speed = 10;
    boolean outleft = false;
    boolean outright = false;
    boolean inleft = false;
    boolean inright = false;
    int combo = 0;
    int gauge = 0;
    int score = 0;
    int[] judgecount = {0, 0, 0}; // 0: 얼쑤, 1: 좋다, 2: 에구
    ArrayList<circle> blocks;

    int songlength;
    int songnowtime = 0;
    File sounddung;
    File soundddack;
    File soundsong;
    AudioInputStream dung;
    AudioInputStream ddack;
    AudioInputStream song;
    Clip songclip;
    Clip hitclip;

    KeyListener keylistener = new GameKeyListner();
    Image imageUPBG = new ImageIcon(TAIGO.class.getResource("./img/uppage.png")).getImage();
    Image imageDOWNBG = new ImageIcon(TAIGO.class.getResource("./img/downpage.png")).getImage();
    
    public TAIGO(noteTape tape) {
        blocks = tape.getBlocks();
        try{
            sounddung = new File("./src/Swing_javaclass/music/dung.wav");
            soundddack = new File("./src/Swing_javaclass/music/ddack.wav");
            soundsong = tape.getSong();
        }
        catch(Exception e){
            e.printStackTrace();
        }

        JPanel panel = new Panel1();
        frame.getContentPane().add(panel);
        frame.setVisible(true);
        frame.addKeyListener(keylistener);
        frame.setVisible(true);
    }

    public hitResult go() {
        int timecounter = -1; // -1: none, 0: reset, other: count

        try{
            song = AudioSystem.getAudioInputStream(soundsong);
            songclip = AudioSystem.getClip();
            songclip.open(song);
            songclip.start();
            songlength = (int) (songclip.getMicrosecondLength() / 1000);
        }catch(Exception ex){
            ex.printStackTrace();
        }

        while(true) {
            Iterator<circle> it = blocks.iterator();
            while(it.hasNext()) {
                circle c = it.next();

                c.setX(c.getX() - c.getSpeed());
                double predict = c.getX() + (c.getR() - 100) / 2;

                if( 330 <= predict && predict <= 370 ) {
                    if(c.getType() % 2 == 1 && (inleft || inright)) {
                        it.remove();                      
                    }
                    else if(c.getType() % 2 == 0 && (outleft || outright)) {
                        it.remove();
                    }
                    else {
                        continue;
                    }
                    gauge = ++gauge > 100 ? 100 : gauge;
                    combo++;
                    hitNotice = 340 <= predict && predict <= 360 ? 0 : 1;
                    score += hitNotice == 0 ? 500 : 250;
                    judgecount[hitNotice]++; // 0: 얼쑤, 1: 좋다
                    timecounter = 100;
                }
                else if( 330 > predict ) {
                    it.remove();
                    gauge -= 2;
                    gauge = gauge < 0 ? 0 : gauge;
                    combo = 0;
                    hitNotice = 2;
                    judgecount[hitNotice]++; // 2: 에구
                    timecounter = 100;
                }
            }
            
            try {
                Thread.sleep(speed);

                if(timecounter != -1) {
                    if(timecounter > 0) {
                        timecounter--;
                    }
                    else if(timecounter == 0) {
                        hitNotice = -1;
                        timecounter = -1;
                    }
                }

                if(songnowtime < songlength) {
                    songnowtime = (int) (songclip.getMicrosecondPosition() / 1000);
                }
                else {
                    songclip.stop();
                    break;
                }

            } catch (InterruptedException e) {}
            frame.repaint();
        }

        frame.getContentPane().removeAll();
        frame.removeKeyListener(keylistener);
        return new hitResult(score, gauge, judgecount);
    }

    class Panel1 extends JPanel {
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            int WIDTH = MainDriver.frame.getWidth();
            int HEIGHT = MainDriver.frame.getHeight();

            // 배경
            g.drawImage(imageUPBG, 0, 0, WIDTH + 4, 205, null);
            g.drawImage(imageDOWNBG, 0, 400, WIDTH + 4, HEIGHT - 400, null);

            //태고 노트
            g.setColor(new Color(44, 44, 44));
            g.fillRect(300, 200, WIDTH-310, 160);
            g.setColor(Color.black);
            g.drawRect(300, 200, WIDTH-310, 160);

            g.setColor(Color.gray);
            g.fillRect(300, 360, WIDTH-310, 40);
            g.setColor(Color.black);
            g.drawRect(300, 360, WIDTH-310, 40);

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
            g.setColor(Color.black);
            g.fillRect(650, 150, WIDTH-660, 50);
            g.setColor(Color.gray);
            g.fillRect(655, 155, WIDTH-720, 40);

            // combo에 따라 게이지 색깔 바꾸기
            if( gauge < 70 ){
                g.setColor(Color.red);
                g.fillRect(655, 155, (WIDTH-720) * (gauge > 70 ? 70 : gauge) / 100, 40);
            }
            else if( gauge < 95 ){
                g.setColor(Color.green);
                g.fillRect(655, 155, (WIDTH-720) * (gauge > 95 ? 95 : gauge) / 100, 40);
            }
            else{
                g.setColor(Color.MAGENTA);
                g.fillRect(655, 155, (WIDTH-720) * (gauge > 100 ? 100 : gauge) / 100, 40);
            }

            // 태고북 판정지점
            g.setColor(Color.gray);
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
            g.setColor(outleft ? new Color(104, 192, 193) : new Color(255, 246, 219));  //파란색 아니면 베이지색
            g.fillArc(125, 215, 160, 160, 90, 180);

            //태고북 바깥 오른쪽
            g.setColor(outright ? new Color(104, 192, 193) : new Color(255, 246, 219)); //파란색 아니면 베이지색
            g.fillArc(125, 215, 160, 160, 270, 180);

            g.setColor(Color.BLACK);
            g.fillRect(205, 215, 2, 160);

            //태고북 안쪽 왼쪽
            g.setColor(inleft ? new Color(249, 72, 41) : new Color(255, 246, 219));     // 빨간색 아니면 베이지색
            g.fillArc(145, 235, 120, 120, 90, 180);

            //태고북 안쪽 오른쪽
            g.setColor(inright ? new Color(249, 72, 41) : new Color(255, 246, 219));    // 빨간색 아니면 베이지색
            g.fillArc(145, 235, 120, 120, 270, 180);

            g.setColor(Color.BLACK);
            g.drawOval(145, 235, 120, 120);

            // 태고 블럭노트
            for(int i=0; i < blocks.size(); i++){
                circle c = blocks.get(i);
                g.drawImage(c.getShape(), c.getX(), c.getY(), c.getR(), c.getR(), null);

                g.setColor(c.color);
                g.setFont(new Font("맑은 고딕", 1, 30));
                g.drawString((c.getType() % 2 == 1 ? "쿵" : "딱"), c.getX() + (c.getR() / 2), 390);
            }

            g.setColor(Color.white);
            g.setFont(new Font("맑은 고딕", 1, 20));
            g.drawString("" + score, 30, 230);

            if(combo > 0) {
                g.setColor(Color.orange);
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

            if(hitNotice == 0) {
                g.setColor(Color.orange);
                g.fillOval(355, 145, 70, 50);
                g.setColor(Color.darkGray);
                g.setFont(new Font("맑은 고딕", 1, 30));
                g.drawString("얼쑤", 360, 180);
            }
            else if(hitNotice == 1) {
                g.setColor(Color.gray);
                g.fillOval(355, 145, 70, 50);
                g.setColor(Color.white);
                g.setFont(new Font("맑은 고딕", 1, 30));
                g.drawString("좋다", 360, 180);
            }
            else if(hitNotice == 2) {
                g.setColor(new Color(85, 119 ,255));
                g.fillOval(355, 145, 70, 50);
                g.setColor(Color.black);
                g.setFont(new Font("맑은 고딕", 1, 30));
                g.drawString("에구", 360, 180);
            }

            g.setColor(Color.black);
            g.setFont(new Font("맑은 고딕", 1, 15));
            g.drawString("ESC : 강제 중단", 10, 320);
            g.setColor(Color.black);
            g.setFont(new Font("맑은 고딕", 1, 15));
            g.drawString("A, F : 딱 (파란색)", 10, 350);
            g.setColor(Color.black);
            g.setFont(new Font("맑은 고딕", 1, 15));
            g.drawString("S, D : 쿵 (빨간색)", 10, 380);
        }
    }

    class GameKeyListner implements KeyListener {
        void playsound_dung(){
            try{
                dung = AudioSystem.getAudioInputStream(sounddung);
                hitclip = AudioSystem.getClip();
                hitclip.stop();
                hitclip.open(dung);
                hitclip.start();
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
        void playsound_ddack(){
            try{
                ddack = AudioSystem.getAudioInputStream(soundddack);
                hitclip = AudioSystem.getClip();
                hitclip.stop();
                hitclip.open(ddack);
                hitclip.start();
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
                case KeyEvent.VK_ESCAPE:
                    songnowtime = songlength;
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
