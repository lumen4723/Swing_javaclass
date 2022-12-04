package Swing_javaclass;

import java.awt.*;
import java.awt.event.*;
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class helloTaigo extends MainDriver{
    JPanel panel;
    int WIDTH = MainDriver.frame.getWidth();
    int HEIGHT = MainDriver.frame.getHeight();

    boolean donext;

    File sounddung;
    File soundsong;
    AudioInputStream dung;
    AudioInputStream song;
    Clip songclip;
    Clip hitclip;

    Image startPage = new ImageIcon(helloTaigo.class.getResource("./img/startpage.gif")).getImage();
    Image startBG = new ImageIcon(helloTaigo.class.getResource("./img/startpage.jpg")).getImage();
    int timer = 0;

    KeyListener keylistener = new GameKeyListner();

    public helloTaigo() {
        donext = false;

        try{
            sounddung = new File("./src/Swing_javaclass/music/dung.wav");
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public void start() {
        panel = new panel4();
        frame.getContentPane().add(panel);
        frame.setVisible(true);
        frame.addKeyListener(keylistener);
        frame.setVisible(true);

        try{
            Thread.sleep(1000);
            soundsong = new File("./src/Swing_javaclass/music/startsong.wav");
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
                timer += timer < 110 ? 1 : 0;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        songclip.stop();
        frame.getContentPane().removeAll();
        frame.removeKeyListener(keylistener);
    }

    class panel4 extends JPanel {
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            g.drawImage((timer < 110 ? startPage : startBG), 0, 0, this);

            if(timer >= 110) {
                g.setColor(Color.black);
                g.setFont(new Font("맑은 고딕", 1, 25));
                g.drawString("아무키를 눌러 곡선택 창으로 이동하세요.", 400, 500);
            }
        }
    }

    class GameKeyListner implements KeyListener {
        public void keyPressed(KeyEvent e) {
            if(timer < 110) {
                timer = 110;
            }
            else {
                donext = true;
            }

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

        public void keyReleased(KeyEvent e) {
        }

        public void keyTyped(KeyEvent e) {
        }
    }
}
