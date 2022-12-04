package Swing_javaclass;

import javax.swing.JFrame;

public class MainDriver {
    public static JFrame frame;
    public static void main(String[] args) {
        frame = new JFrame("TAIGO");
        int WIDTH = 1280;
        int HEIGHT = 720;
        frame.setSize(WIDTH, HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        helloTaigo hello = new helloTaigo();
        hello.start();

        while(true) {
            Index index = new Index();
            noteTape tape = index.show();
            TAIGO taigo = new TAIGO(tape);
            hitResult result = taigo.go();
            result.show();
        }
    }
}
