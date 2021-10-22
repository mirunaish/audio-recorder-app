package com.example.unicornrecorder.ui.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.unicornrecorder.R;

import java.io.File;
import java.util.ArrayList;

public class FileListElementAdapter extends ArrayAdapter<FileListElement> implements View.OnClickListener {

    Context mContext;

    public FileListElementAdapter(@NonNull Context context, ArrayList<FileListElement> data) {
        super(context, R.layout.file_list_element, data);
        mContext=context;
    }

    @Override
    public void onClick(View view) {
        int index = (int) view.getTag();
        FileListElement listElement = (FileListElement) getItem(index);
        if (view.getId() == R.id.playButton) listElement.play();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        FileListElement fileListElement = getItem(position);
        TextView fileName;  // the field to put the file name in

        if (convertView == null) {  // first time seeing this FileListElement
            LayoutInflater inflater = LayoutInflater.from(mContext);  // get a layoutInflater to inflate the view
            convertView = inflater.inflate(R.layout.file_list_element, parent, false);  // inflate view

            fileName = convertView.findViewById(R.id.fileName);  // find the filename field
            convertView.setTag(fileName);  // for later retrieval
        }
        else {  // has been previously created
            fileName = (TextView) convertView.getTag();
        }

        fileName.setText(fileListElement.getName());  // name of file
        return convertView;
    }

    public void setFiles(ArrayList<File> newFiles) {
        ;
    }
}
