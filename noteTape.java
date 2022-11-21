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

    public noteTape(File song , ArrayList<circle> blocks) {
        this.song = song;
        this.blocks = blocks;
        // readNotetoTape(note);
    }

    public void readNotetoTape(File note) {
    }
}
