package Swing_javaclass;

import java.awt.*;
import java.awt.event.*;
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class hitResult {
    JFrame frame = new JFrame("Taigo");
    JPanel panel = new panel2();

    int WIDTH = 1024;
    int HEIGHT = 720;

    boolean donext = false;
    
    int score;
    int gauge;
    String rank;

    File sounddung;
    File soundddack;
    File soundsong;
    AudioInputStream dung;
    AudioInputStream ddack;
    AudioInputStream song;
    Clip songclip;
    Clip hitclip;

    public hitResult(int score, int gauge) {
        donext = false;

        try{
            sounddung = new File("./src/Swing_javaclass/music/dung.wav");
            soundddack = new File("./src/Swing_javaclass/music/ddack.wav");
        }
        catch(Exception e){
            e.printStackTrace();
        }

        this.score = score;
        this.gauge = gauge;
        if(gauge >= 70){
            if(score >= 50000) rank = "SSS";
            else if(score >= 45000) rank = "SS";
            else if(score >= 40000) rank = "S";
            else if(score >= 35000) rank = "A";
            else if(score >= 30000) rank = "B";
            else if(score >= 25000) rank = "C";
            else if(score >= 20000) rank = "D";
            else rank = "E";
        }
        else{
            rank = "F";
        }
    }

    public void show() {
        frame.setSize(WIDTH, HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.getContentPane().add(panel);
        frame.setVisible(true);
        frame.addKeyListener(new GameKeyListner());
        frame.setVisible(true);

        try{
            soundsong = new File("./src/Swing_javaclass/music/Applause.wav");
            song = AudioSystem.getAudioInputStream(soundsong);
            songclip = AudioSystem.getClip();
            songclip.open(song);
            songclip.start();
        }catch(Exception ex){
            ex.printStackTrace();
        }

        while(true) {
            if(donext) {
                frame.setVisible(false);
                break;
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    class panel2 extends JPanel {
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            g.setColor(Color.black);
            g.setFont(new Font("맑은 고딕", 1, 40));
            g.drawString("점수 : " + score, 450, 200);

            g.setColor(Color.black);
            g.setFont(new Font("맑은 고딕", 1, 40));
            g.drawString("게이지 : " + gauge, 450, 300);

            g.setColor(Color.black);
            g.setFont(new Font("맑은 고딕", 1, 200));
            g.drawString(rank, 150, 300);

            g.setColor(Color.black);
            g.setFont(new Font("맑은 고딕", 1, 30));
            g.drawString("S또는 D나 ESC를 눌러 곡선택 창으로 이동하세요.", 150, 600);
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
                    playsound_ddack();
                    break;
                case KeyEvent.VK_S:
                    donext = true;
                    playsound_dung();
                    break;
                case KeyEvent.VK_D:
                    donext = true;
                    playsound_dung();
                    break;
                case KeyEvent.VK_F:
                    playsound_ddack();
                    break;
                case KeyEvent.VK_ESCAPE:
                    donext = true;
                    break;
            }
        }

        public void keyReleased(KeyEvent e) {
        }

        public void keyTyped(KeyEvent e) {
        }
    }
}
