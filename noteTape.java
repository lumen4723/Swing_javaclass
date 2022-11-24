package Swing_javaclass;

import java.io.File;
import java.util.ArrayList;

public class noteTape {
    ArrayList<circle> blocks = new ArrayList<circle>();
    File song;

    public noteTape(File song) {
        this.song = song;
        maketutorialnote();
    }

    public noteTape(File song , ArrayList<circle> blocks) {
        this.song = song;
        this.blocks = blocks;
    }

    public void maketutorialnote() { // circle(int x, int y, int type)  type : 0 = 태고딱소, 1. 태고쿵소, 2. 태고딱대, 3. 태고쿵대
        for(int i = 0; i < 85; i++) {
            blocks.add(new circle(i*160 + 1080, 4, i%4 == 1 || i%3 == 1 ? (i%7 == 1 ? 2 : 0) : (i%7 == 1 ? 3 : 1)));
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

    public ArrayList<circle> getBlocks() {
        return blocks;
    }

    public File getSong() {
        return song;
    }
}
