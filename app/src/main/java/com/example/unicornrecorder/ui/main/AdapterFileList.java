package com.example.unicornrecorder.ui.main;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.util.ArrayList;

// holds an instance of adapter and the list of FileListElements. a wrapper of sorts around the adapter
public class AdapterFileList {
    private ArrayList<FileListElement> listElements;
    private FileListElementAdapter adapter;

    public AdapterFileList() {
        buildFromMemory();
    }

    // create the list of FileListElements from the files in memory
    // if i wanted to implement a refresh button it would call this
    public void buildFromMemory() {
        File[] fileArray = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/audiocorns").listFiles();
        listElements = new ArrayList<>();  // reinitialize arrayList
        if (fileArray == null) return;  // there's no files / directory; listElements is empty list

        // build list of elements from file array
        for (File file : fileArray)
            listElements.add(new FileListElement(file));
    }

    public void createAdapter(Context context, ListTabFragment parent) {
        adapter = new FileListElementAdapter(context, listElements, parent);
    }

    public FileListElementAdapter getAdapter() {
        return adapter;
    }

    public void removeElement(FileListElement element) {
        listElements.remove(element);
        adapter.notifyDataSetChanged();
        // https://stackoverflow.com/questions/4698386/android-how-to-remove-an-item-from-a-listview-and-arrayadapter
    }

    public void addFile(String path) {
        listElements.add(new FileListElement(new File(path)));
        adapter.notifyDataSetChanged();
    }
}
