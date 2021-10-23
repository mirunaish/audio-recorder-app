package com.example.unicornrecorder.ui.main;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.unicornrecorder.R;  // resources

public class TabAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2};
    private final Context mContext;

    public TabAdapter(Context context, FragmentManager fm) {
        super(fm);  // i'm pretty sure it wasn't deprecated when i coded this
        mContext = context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        //returns a fragment based on the tab number
        if (position==0) return RecordTabFragment.newInstance(position+1);  // calls static method that creates the requested tab fragment
        else return ListTabFragment.newInstance(position+1);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // 2 pages in total, recordtab and listtab
        return 2;
    }
}