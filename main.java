package Swing_javaclass;

public class main {
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
