package Swing_javaclass;

import java.io.File;
import java.util.ArrayList;

public class tape {
    ArrayList<circle> blocks = new ArrayList<circle>();
    int length;
    File song;

    public tape(File song, int length, ArrayList blocks) {
        this.song = song;
        this.blocks = blocks;
        this.length = length;
    }
}
