package Swing_javaclass;

import java.awt.*;
import java.awt.event.*;
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class hitResult extends MainDriver{
    JPanel panel;
    int WIDTH = MainDriver.frame.getWidth();
    int HEIGHT = MainDriver.frame.getHeight();

    boolean donext;
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

    KeyListener keylistener = new GameKeyListner();
    Image resultBG = new ImageIcon(Index.class.getResource("./img/resultbg.png")).getImage();

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
            if(score >= 50000) rank = "SS";
            else if(score >= 45000) rank = "S";
            else if(score >= 40000) rank = "A";
            else if(score >= 35000) rank = "B";
            else if(score >= 30000) rank = "C";
            else if(score >= 25000) rank = "D";
            else rank = "E";
        }
        else{
            rank = "F";
        }
    }

    public void show() {
        panel = new panel2();
        frame.getContentPane().add(panel);
        frame.setVisible(true);
        frame.addKeyListener(keylistener);
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

        while(!donext) {

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        frame.getContentPane().removeAll();
        frame.removeKeyListener(keylistener);
    }

    class panel2 extends JPanel {
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            int WIDTH = MainDriver.frame.getWidth();
            int HEIGHT = MainDriver.frame.getHeight();
            
            g.drawImage(resultBG, 0, 0, WIDTH, HEIGHT, null);

            g.setColor(Color.black);
            g.setFont(new Font("맑은 고딕", 1, 40));
            g.drawString("점수 : " + score, 445, 200);


            g.setColor(Color.gray);
            g.fillRect(450, 255, WIDTH-600, 50);
            g.setColor(Color.black);
            g.fillRect(455, 260, WIDTH-610, 40);


            if( gauge < 70 ){
                g.setColor(Color.red);
                g.fillRect(455, 260, (WIDTH-610) * (gauge > 70 ? 70 : gauge) / 100, 40);
            }
            else if( gauge < 95 ){
                g.setColor(Color.green);
                g.fillRect(455, 260, (WIDTH-610) * (gauge > 95 ? 95 : gauge) / 100, 40);
            }
            else{
                g.setColor(Color.MAGENTA);
                g.fillRect(455, 260, (WIDTH-610) * (gauge > 100 ? 100 : gauge) / 100, 40);
            }

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
