package Swing_javaclass;

import java.io.File;
import java.util.ArrayList;

public class noteTape {
    ArrayList<circle> blocks = new ArrayList<circle>();
    File song;

    // public noteTape(File song, File note) {
    //     this.song = song;
    //     readNotetoTape(note);
    // }
    public noteTape(File song) {
        this.song = song;
    }

    public noteTape(File song , ArrayList<circle> blocks) {
        this.song = song;
        this.blocks = blocks;
        // readNotetoTape(note);
    }

    public void maketutorialnote() { // circle(int x, int y, int type)  type : 0 = 태고딱소, 1. 태고쿵소, 2. 태고딱대, 3. 태고쿵대
        for(int i = 0; i < 10; i++) {
            blocks.add(new circle(i*1460 + 1680, 4, i%2 == 1 ? 1 : 0));
            blocks.add(new circle(i*1460 + 1760, 4, i%3 == 1 ? 0 : 1));
            blocks.add(new circle(i*1460 + 1840, 4, i%4 == 1 ? 1 : 0));
        }

        for(int i = 0; i < 40; i++) {
            blocks.add(new circle(i*160 + 15180, 4, i%3 == 1 || i%5 == 1 ? 2 : 3));
        }

        for(int i = 0; i < 100; i++) {
            if(i%7 == 0 || i%11 == 0 ) {
                continue;
            }
            blocks.add(new circle(i * 80 + 21580, 4, i%3 == 1 || i%4 == 1 ? 0 : 1));
        }
    }
}
