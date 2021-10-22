package com.example.unicornrecorder.ui.main;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModelProviders;

import com.example.unicornrecorder.MainActivity;
import com.example.unicornrecorder.R;

import java.io.File;
import java.util.ArrayList;


public class ListTabFragment extends TabFragment {

    public static ListTabFragment newInstance(int index) {  // static constructor-like method
        ListTabFragment fragment = new ListTabFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.setIndex(index);

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.listfragment, container, false); //we should look like listfragment.xml

        // create an adaptor that handles the list of recordings for the listview
        // a dali lab member suggested that i use a listview and an adapter during an app workshop
        // used this https://www.journaldev.com/10416/android-listview-with-custom-adapter-example-tutorial

        ArrayList<FileListElement> fileListElements = new ArrayList<>();  // the array of FileListElements the adapter will handle
        for (File file : ((MainActivity)getActivity()).getFileList()) {   // the audio files saved by the recordTabFragment, retrieved from storage
            fileListElements.add(new FileListElement(file));
        }

        ListView listView = root.findViewById(R.id.file_list);
        listView.setAdapter(new FileListElementAdapter(root.getContext(), fileListElements));  // create adapter for listView

        return root;
    }

}