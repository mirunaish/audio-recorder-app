package com.example.unicornrecorder;

import android.Manifest;
import android.os.Build;
import android.os.Bundle;

import com.example.unicornrecorder.ui.main.AdapterFileList;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.RequiresApi;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import com.example.unicornrecorder.ui.main.TabAdapter;


public class MainActivity extends AppCompatActivity {

    public AdapterFileList adapterWrapper;  // not sure what exactly a wrapper is but i think the name fits here

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {  // called when the app activity is created
        super.onCreate(savedInstanceState);

        // we should look like activity_main.xml
        setContentView(R.layout.activity_main);

        requestPermissions(new String[] {
            Manifest.permission.READ_EXTERNAL_STORAGE,  // read audio files already recorded
            Manifest.permission.WRITE_EXTERNAL_STORAGE,  // store audio files in storage
            Manifest.permission.RECORD_AUDIO
        }, 29); // i think the request code is for checking whether permission was granted or not later

        // tab adapter handles the tab fragments
        TabAdapter tabAdapter = new TabAdapter(this, getSupportFragmentManager());

        ViewPager viewPager = findViewById(R.id.view_pager);  // find the viewPager under the tab section to fill with a fragment
        viewPager.setAdapter(tabAdapter);  // will use the tab adapter to decide what the page looks like
        TabLayout tabs = findViewById(R.id.tabs);  // find the tab section
        tabs.setupWithViewPager(viewPager);  // set up the tab titles

        adapterWrapper = new AdapterFileList();  // will add an adapter to this later, for now get the audio files
    }

    public AdapterFileList getAdapterWrapper() {
        return adapterWrapper;
    }
}