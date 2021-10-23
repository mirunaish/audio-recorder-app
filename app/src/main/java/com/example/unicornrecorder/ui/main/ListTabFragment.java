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

        AdapterFileList fileWrapper = ((MainActivity)getActivity()).getAdapterWrapper();  // get the adapter + file object from mainActivity
        fileWrapper.createAdapter(root.getContext(), this);  // create the adapter inside the object

        ListView listView = root.findViewById(R.id.file_list);  // this is where the files will be listed
        listView.setAdapter(fileWrapper.getAdapter());  // get adapter and pass it to listView

        return root;
    }

    public void removeElement(FileListElement element) {
        ((MainActivity)getActivity()).getAdapterWrapper().removeElement(element);
    }

}