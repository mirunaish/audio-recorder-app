package com.example.unicornrecorder;

import android.Manifest;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.widget.ListView;

import com.example.unicornrecorder.ui.main.FileListElement;
import com.example.unicornrecorder.ui.main.FileListElementAdapter;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import com.example.unicornrecorder.ui.main.SectionsPagerAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    public ArrayList<File> fileList;  // the list of files to be displayed in the list

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {  // called when the app activity is created
        super.onCreate(savedInstanceState);

        // we should look like activity_main.xml
        setContentView(R.layout.activity_main);

        // create the sectionspageradapter, which handles the tab fragments
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());

        ViewPager viewPager = findViewById(R.id.view_pager);  // find the viewPager under the tab section to fill with a fragment
        viewPager.setAdapter(sectionsPagerAdapter);  // you should use the pageradapter to decide what the page looks like
        TabLayout tabs = findViewById(R.id.tabs);  // find the tab section
        tabs.setupWithViewPager(viewPager);  // set up the tab titles

        fetchFiles();  // initialize files array
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void fetchFiles() {
        // need to be able to read the folder to list files in it
        requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 29);  // not sure what the requestCode is for

        File[] fileArray = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/audiocorns").listFiles();
        if (fileArray == null) { fileList = new ArrayList<>(); return; }  // there's no files
        fileList =  new ArrayList<>(Arrays.asList(fileArray));
    }

    public ArrayList<File> getFileList() {
        return fileList;
    }

    public void addFile(String path) {
        fileList.add(new File(path));
    }

    public void removeFile(String path) {
        fileList.remove(new File(path));
    }
}