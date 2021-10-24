package com.example.unicornrecorder.ui.main;

import android.widget.ImageButton;

import java.io.File;

// contains the data the adapter needs to display a row in the list
public class FileListElement {

    private File file;
    private ImageButton playButton;  // need this to change the image in the button

    public FileListElement(File file) {  // sets the content layout
        this.file=file;
    }

    public void setButton(ImageButton button) {
        playButton = button;
    }

    public void delete() {
        file.delete();
    }

    public String getName() { return file.getName(); }

    public String getPath() { return file.getAbsolutePath(); }

    public void changeToStop() {  // change button icon
        playButton.setImageResource(android.R.drawable.checkbox_off_background);
    }

    public void changeToPlay() {
        playButton.setImageResource(android.R.drawable.ic_media_play);
    }
}
