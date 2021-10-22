package com.example.unicornrecorder.ui.main;

import android.media.MediaPlayer;

import java.io.File;
import java.io.IOException;

public class FileListElement {

    private File file;
    private MediaPlayer mp;

    public FileListElement(File file) {  // sets the content layout
        this.file=file;
    }

    public void initMediaPlayer() {
        mp = new MediaPlayer();
        try {
            mp.setDataSource(file.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setFile(String path) {
        file = new File(path);
        initMediaPlayer();
    }

    public void play() {
        try {
            mp.prepare();
            mp.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void delete() { file.delete(); }

    public String getName() { return file.getName(); }
}
