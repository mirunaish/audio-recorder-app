package com.example.unicornrecorder.ui.main;

import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;

import java.io.File;
import java.io.IOException;

import com.example.unicornrecorder.MainActivity;
import com.example.unicornrecorder.R;


public class RecordTabFragment extends TabFragment {

    private Button recordButton, stopButton;
    private TextView infoTextBox;  // a textbox for "printing" info to the screen
    private MediaRecorder uRecorder;
    private String currentPath;  // the path currently being recorded to

    public static RecordTabFragment newInstance(int index) {  // static constructor-like method
        RecordTabFragment fragment = new RecordTabFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);  // the arguments include the number of this tab (0 or 1)
        return fragment;
    }

    // this is where the behavior is defined
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.recordfragment, container,false);  // defines the look of the tab

        recordButton = root.findViewById(R.id.record); recordButton.setEnabled(false);
        stopButton = root.findViewById(R.id.stop); stopButton.setEnabled(false);
        infoTextBox = root.findViewById(R.id.text0); infoTextBox.setText(R.string.start);

        uRecorder = new MediaRecorder();

        recordButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View root) {

                prepareRecorder();  // set audio input, file output path, etc

                recordButton.setEnabled(false);

                try {
                    uRecorder.prepare();
                    uRecorder.start();
                    infoTextBox.setText(R.string.recording);
                    stopButton.setEnabled(true);
                } catch (IllegalStateException e) {
                    infoTextBox.setText(R.string.error);
                    e.printStackTrace();
                } catch (IOException e) {
                    infoTextBox.setText(R.string.error);
                    e.printStackTrace();
                }
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                stopButton.setEnabled(false);
                uRecorder.stop();
                uRecorder.reset();

                ((MainActivity)getActivity()).getAdapterWrapper().addFile(currentPath);  // add recorded file to the file list

                infoTextBox.setText(R.string.done);
                recordButton.setEnabled(true);
            }
        });

        recordButton.setEnabled(true);

        return root;
    }

    private void prepareRecorder() {  // get ready to record a new file
        uRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        uRecorder.setOutputFormat(MediaRecorder.OutputFormat.AAC_ADTS);
        uRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);

        String folder = Environment.getExternalStorageDirectory().getAbsolutePath() + "/audiocorns";
        new File(folder).mkdirs();  // make sure folder exists, create it if not

        // find the first unused filename
        int i = 1;
        while (new File(folder + "/audiocorn#" + i + ".aac").exists()) i++;

        currentPath = folder + "/audiocorn#" + i + ".aac";
        uRecorder.setOutputFile(currentPath);
    }

}
