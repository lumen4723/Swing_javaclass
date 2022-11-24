package Swing_javaclass;

import java.awt.*;
import java.awt.event.*;

import java.io.File;
import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Index extends MainDriver{
    JPanel panel;
    int WIDTH = MainDriver.frame.getWidth();
    int HEIGHT = MainDriver.frame.getHeight();

    String level = "loading";
    boolean ischanged = false;
    int playindex = -1;
    int nowindex = 0;
    int previndex = 0;
    int nextindex = 0;
    String now = "loading";
    String prev = "loading";
    String next = "loading";

    int songlength = 0;
    int songnowtime = 0;
    File sounddung;
    File soundddack;
    File song;
    AudioInputStream dung;
    AudioInputStream ddack;
    AudioInputStream mainsong;
    Clip hitclip;
    Clip songclip;
    
    KeyListener keylistener = new GameKeyListner();
    noteTape playtape;
    ArrayList<String[]> list = new ArrayList<>();
    Image indexBG = new ImageIcon(Index.class.getResource("./img/indexbg.png")).getImage();
    
    public Index() {
        try{
            sounddung = new File("./src/Swing_javaclass/music/dung.wav");
            soundddack = new File("./src/Swing_javaclass/music/ddack.wav");
        }
        catch(Exception e){
            e.printStackTrace();
        }

        list.add(new String[]{"tutorial", "1"});
        list.add(new String[]{"ROKI", "4"});
        list.add(new String[]{"bad-elixir", "7"});

        playindex = -1;
        songnowtime = 0;

        level = list.get(nowindex)[1];
        previndex = nowindex - 1 < 0 ? list.size() - 1 : nowindex - 1;
        nextindex = nowindex + 1 > list.size() - 1 ? 0 : nowindex + 1;

        panel = new panel3();
        frame.getContentPane().add(panel);
        frame.setVisible(true);
        frame.addKeyListener(keylistener);
        frame.setVisible(true);
    }

    public noteTape start() {
        ArrayList<circle> blocks = new ArrayList<circle>();
        songnowtime = 0;

        while(true) {
            if(playindex == 0) {
                song = new File("./src/Swing_javaclass/music/tutorial.wav");
                playtape = new noteTape(song);
                break;
            }

            if(playindex == 1) {
                song = new File("./src/Swing_javaclass/music/ROKI.wav");
                for(int i = 0; i < 100; i++) {
                    blocks.add(new circle(i*300 + 300, 4 + i%2, i%4));
                }
                playtape = new noteTape(song, blocks);
                break;
            }

            if(playindex == 2) {
                song = new File("./src/Swing_javaclass/music/bad-elixir.wav");
                for(int i = 0; i < 100; i++) {
                    blocks.add(new circle(i*300 + 300, 4 + (i * i)%3, (i * i)%4));
                }
                playtape = new noteTape(song, blocks);
                break;
            }

            previndex = nowindex - 1 < 0 ? list.size() - 1 : nowindex - 1;
            nextindex = nowindex + 1 > list.size() - 1 ? 0 : nowindex + 1;
            now = list.get(nowindex)[0];
            prev = list.get(previndex)[0];
            next = list.get(nextindex)[0];

            if(ischanged){
                songclip.stop();
            }
            if(songnowtime == songlength || ischanged) {
                ischanged = false;

                try{
                    song = new File("./src/Swing_javaclass/music/" + now + ".wav");
                    mainsong = AudioSystem.getAudioInputStream(song);
                    songclip = AudioSystem.getClip();
                    songclip.stop();
                    songclip.open(mainsong);
                    songclip.start();

                    songlength = (int)songclip.getMicrosecondLength();
                    songnowtime = (int)songclip.getMicrosecondPosition();
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
            else{
                songnowtime = (int)songclip.getMicrosecondPosition();
            }

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            frame.repaint();
        }
        
        songclip.stop();

        frame.getContentPane().removeAll();
        frame.removeKeyListener(keylistener);
        return playtape;
    }

    class panel3 extends JPanel {
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            int WIDTH = MainDriver.frame.getWidth();
            int HEIGHT = MainDriver.frame.getHeight();

            Color nowindexColor;
            Color previndexColor;
            Color nextindexColor;

            if(Integer.parseInt(list.get(nowindex)[1]) < 3) {
                nowindexColor = new Color(0, 162, 232);
            }
            else if(Integer.parseInt(list.get(nowindex)[1]) < 6) {
                nowindexColor = new Color(255, 211, 6);
            }
            else {
                nowindexColor = new Color(254, 113, 71);
            }

            if(Integer.parseInt(list.get(previndex)[1]) < 3) {
                previndexColor = new Color(0, 162, 232);
            }
            else if(Integer.parseInt(list.get(previndex)[1]) < 6) {
                previndexColor = new Color(255, 211, 6);
            }
            else {
                previndexColor = new Color(254, 113, 71);
            }

            if(Integer.parseInt(list.get(nextindex)[1]) < 3) {
                nextindexColor = new Color(0, 162, 232);
            }
            else if(Integer.parseInt(list.get(nextindex)[1]) < 6) {
                nextindexColor = new Color(255, 211, 6);
            }
            else {
                nextindexColor = new Color(254, 113, 71);
            }

            // 배경
            g.drawImage(indexBG, 0, 0, WIDTH, HEIGHT, null);

            g.setColor(Color.black);
            g.fillRect((int)(WIDTH / 2) - 300, (int)(HEIGHT / 2) - 100, 600, 200);
            g.setColor(nowindexColor);  //현재곡색
            g.fillRect((int)(WIDTH / 2) - 295, (int)(HEIGHT / 2) - 95, 590, 190);

            g.setColor(Color.black);
            g.setFont(new Font("맑은 고딕", 1, 100));
            g.drawString(now, (int)(WIDTH / 2) - 200, (int)(HEIGHT / 2));

            g.setColor(Color.black);
            g.setFont(new Font("맑은 고딕", 1, 40));
            g.drawString("레벨 : " + level, (int)(WIDTH / 2) - 150, (int)(HEIGHT / 2) + 80);

            g.setColor(Color.BLACK); //이전곡색
            g.fillRect((int)(WIDTH / 2) - 200, (int)(HEIGHT / 2) - 250, 400, 100);
            g.setColor(previndexColor); //이전곡색
            g.fillRect((int)(WIDTH / 2) - 195, (int)(HEIGHT / 2) - 245, 390, 90);

            g.setColor(Color.black);
            g.setFont(new Font("맑은 고딕", 1, 60));
            g.drawString(prev, (int)(WIDTH / 2) - 100, (int)(HEIGHT / 2) - 175);

            g.setColor(Color.BLACK);
            g.fillRect((int)(WIDTH / 2) - 200, (int)(HEIGHT / 2) + 150, 400, 100);
            g.setColor(nextindexColor); //다음곡색
            g.fillRect((int)(WIDTH / 2) - 195, (int)(HEIGHT / 2) + 155, 390, 90);

            g.setColor(Color.black);
            g.setFont(new Font("맑은 고딕", 1, 60));
            g.drawString(next, (int)(WIDTH / 2) - 100, (int)(HEIGHT / 2) + 225);

            g.setColor(Color.black);
            g.setFont(new Font("맑은 고딕", 1, 30));
            g.drawString("ESC : 종료", 10, HEIGHT / 2 );
            g.setColor(Color.black);
            g.setFont(new Font("맑은 고딕", 1, 30));
            g.drawString("A : 이전곡", 10, HEIGHT / 2 + 50);
            g.setColor(Color.black);
            g.setFont(new Font("맑은 고딕", 1, 30));
            g.drawString("F : 다음곡", 10, HEIGHT / 2 + 100);
            g.setColor(Color.black);
            g.setFont(new Font("맑은 고딕", 1, 30));
            g.drawString("S, D : 선택", 10, HEIGHT / 2 + 150);
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
                    nowindex = (nowindex - 1) < 0 ? list.size() - 1 : nowindex - 1;
                    level = list.get(nowindex)[1];
                    ischanged = true;
                    playsound_ddack();
                    break;
                case KeyEvent.VK_S:
                    playindex = nowindex;
                    playsound_dung();
                    break;
                case KeyEvent.VK_D:
                    playindex = nowindex;
                    playsound_dung();
                    break;
                case KeyEvent.VK_F:
                    nowindex = (nowindex + 1) % list.size();
                    level = list.get(nowindex)[1];
                    ischanged = true;
                    playsound_ddack();
                    break;
                case KeyEvent.VK_ESCAPE:
                    System.exit(0);
                    break;
            }
        }

        public void keyReleased(KeyEvent e) {
        }

        public void keyTyped(KeyEvent e) {
        }
    }
}
