package Swing_javaclass;

public class MainDriver {
    public static void main(String[] args) {
        while(true) {
            Index index = new Index();
            noteTape tape = index.start();
            TAIGO taigo = new TAIGO(tape);
            hitResult result = taigo.go();
            result.show();
        }
    }
}
