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
    int actscore;
    int actgauge;
    int score;
    int gauge;
    String rank;
    int[] judgecount = new int[3];

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

    public hitResult(int score, int gauge, int[] judgecount) {
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
        this.judgecount = judgecount;
        rank = "F";
        actgauge = 0;
        actscore = score > 0 ? 10 : 0;
    }

    public void show() {
        panel = new panel2();
        frame.getContentPane().add(panel);
        frame.setVisible(true);
        frame.addKeyListener(keylistener);
        frame.setVisible(true);

        try{
            soundsong = new File("./src/Swing_javaclass/music/hitResultsong.wav");
            song = AudioSystem.getAudioInputStream(soundsong);
            songclip = AudioSystem.getClip();
            songclip.open(song);
            songclip.start();
        }catch(Exception ex){
            ex.printStackTrace();
        }

        while(!donext) {
            actgauge = actgauge + 1 < gauge ? actgauge + 1 : gauge;
            actscore = actscore * 1.2 < score ? (int)(actscore * 1.2) : score;
            if(actgauge >= 70){
                if(actscore >= 50000) rank = "SS";
                else if(actscore >= 45000) rank = "S";
                else if(actscore >= 40000) rank = "A";
                else if(actscore >= 35000) rank = "B";
                else if(actscore >= 30000) rank = "C";
                else if(actscore >= 25000) rank = "D";
                else rank = "E";
            }
            else{
                rank = "F";
            }

            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            frame.repaint();
        }

        songclip.stop();
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
            g.drawString("점수 : " + actscore, 445, 200);

            g.setColor(Color.gray);
            g.fillRect(450, 255, WIDTH-700, 50);
            g.setColor(Color.black);
            g.fillRect(455, 260, WIDTH-710, 40);

            if( actgauge < 70 ){
                g.setColor(Color.red);
                g.fillRect(455, 260, (WIDTH-710) * (actgauge > 70 ? 70 : actgauge) / 100, 40);
            }
            else if( actgauge < 95 ){
                g.setColor(Color.green);
                g.fillRect(455, 260, (WIDTH-710) * (actgauge > 95 ? 95 : actgauge) / 100, 40);
            }
            else{
                g.setColor(Color.MAGENTA);
                g.fillRect(455, 260, (WIDTH-710) * (actgauge > 100 ? 100 : actgauge) / 100, 40);
            }

            g.setColor(Color.black);
            g.setFont(new Font("맑은 고딕", 1, 200));
            g.drawString(rank, 150, 300);

            g.setColor(Color.black);
            g.fillOval(202, 442, 76, 56);
            g.setColor(Color.orange);
            g.fillOval(205, 445, 70, 50);
            g.setColor(Color.darkGray);
            g.setFont(new Font("맑은 고딕", 1, 30));
            g.drawString("얼쑤  : " + judgecount[0], 210, 480);

            g.setColor(Color.black);
            g.fillOval(452, 442, 76, 56);
            g.setColor(Color.gray);
            g.fillOval(455, 445, 70, 50);
            g.setColor(Color.white);
            g.setFont(new Font("맑은 고딕", 1, 30));
            g.drawString("좋다  : " + judgecount[1], 460, 480);

            g.setColor(Color.black);
            g.fillOval(702, 442, 76, 56);
            g.setColor(new Color(85, 119 ,255));
            g.fillOval(705, 445, 70, 50);
            g.setColor(Color.black);
            g.setFont(new Font("맑은 고딕", 1, 30));
            g.drawString("에구  : " + judgecount[2], 710, 480);

            g.setColor(Color.black);
            g.setFont(new Font("맑은 고딕", 1, 30));
            g.drawString("S또는 D나 ESC를 눌러 곡선택 창으로 이동하세요.", 215, 600);
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
