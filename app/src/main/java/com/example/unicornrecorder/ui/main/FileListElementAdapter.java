package com.example.unicornrecorder.ui.main;

import android.content.Context;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.unicornrecorder.R;

import java.io.IOException;
import java.util.ArrayList;


// adapter handles the list of recordings for the listview
// a dali lab member suggested that i use a listview and an adapter during an app workshop
// used this https://www.journaldev.com/10416/android-listview-with-custom-adapter-example-tutorial
public class FileListElementAdapter extends ArrayAdapter<FileListElement> implements View.OnClickListener {

    Context mContext;
    MediaPlayer mp;  // plays the audio
    FileListElement playingElement;  // the element that is currently playing audio

    ListTabFragment parentFragment;  // will need this to call delete method

    public FileListElementAdapter(@NonNull Context context, ArrayList<FileListElement> data, ListTabFragment parent) {
        super(context, R.layout.file_list_element, data);
        mContext=context;
        parentFragment=parent;

        mp = new MediaPlayer();

        // when done playing, change the icon back to play
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                playingElement.changeToPlay();
                playingElement=null;
            }
        });
    }

    // called when one of the list elements was clicked
    @Override
    public void onClick(View view) {  // view is the thing that was clicked (eg a button on the list element)
        int index = (int) view.getTag();
        FileListElement listElement = getItem(index);
        if (view.getId() == R.id.playButton) play(listElement);
        if (view.getId() == R.id.deleteButton) delete(listElement);
    }

    private static class ViewHolder {  // holds the filename textview and the buttons
        TextView fileName;  // the text field to put the file name in
        ImageButton playButton;
        ImageButton deleteButton;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        FileListElement fileListElement = getItem(position);
        ViewHolder views = new ViewHolder();

        if (convertView == null) {  // first time seeing this FileListElement
            LayoutInflater inflater = LayoutInflater.from(mContext);  // get a layoutInflater to inflate the view
            convertView = inflater.inflate(R.layout.file_list_element, parent, false);  // inflate view

            views.fileName = convertView.findViewById(R.id.fileName);  // find the filename field
            views.playButton = convertView.findViewById(R.id.playButton);
            views.deleteButton = convertView.findViewById(R.id.deleteButton);
            convertView.setTag(views);  // for later retrieval
        }
        else {  // has been previously created
            views = (ViewHolder) convertView.getTag();  // the later retrieval i was talking about
        }

        views.fileName.setText(fileListElement.getName());  // name of file
        views.playButton.setTag(position); views.playButton.setOnClickListener(this);  // for onClick method
        views.deleteButton.setTag(position); views.deleteButton.setOnClickListener(this);

        fileListElement.setButton(views.playButton);  // the fileListElement needs a reference to its play button

        return convertView;
    }

    public void play(final FileListElement element) {
        if (mp.isPlaying() && element==playingElement) {  // the play button is actually a stop button
            mp.stop();
            element.changeToPlay();  // change the icon back to play
            playingElement=null;  // no longer playing
        }
        else {  // either mp is not playing or another element was clicked while mp was playing
            if (mp.isPlaying()) {  // another element's play button was clicked
                mp.stop();
                playingElement.changeToPlay();  // the playing element stops playing and can be played again
            }

            try {
                mp.reset();
                mp.setDataSource(element.getPath());  // the audio of the list element clicked
                mp.prepare();

                mp.start();  // start playing audio
                playingElement=element;  // have to know who's playing
                element.changeToStop();  // change icon to stop icon

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void delete(FileListElement listElement) {
        listElement.delete();
        parentFragment.removeElement(listElement);
    }
}
