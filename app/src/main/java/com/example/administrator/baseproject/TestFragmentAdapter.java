package com.example.administrator.baseproject;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.administrator.baseproject.fragment.Test1Fragment;
import com.example.administrator.baseproject.fragment.Test2Fragment;

/**
 * Created by YoKeyword on 16/2/5.
 */
public class TestFragmentAdapter extends FragmentPagerAdapter {
    String[] mTitles;

    public TestFragmentAdapter(FragmentManager fm, String... titles) {
        super(fm);
        mTitles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new Test1Fragment();
        } else  {
            return new Test2Fragment();
        }
    }

    @Override
    public int getCount() {
        return mTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}
