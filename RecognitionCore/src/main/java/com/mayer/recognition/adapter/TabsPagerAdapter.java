package com.mayer.recognition.adapter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;

import com.mayer.recognition.fragment.TestFragment;
import com.mayer.recognition.fragment.camera.DemoCameraFragment;
import com.mayer.recognition.fragment.camera.DemoCameraFragment_;

/**
 * Created by asuprun on 23.07.2014.
 */
public class TabsPagerAdapter extends FragmentPagerAdapter {
    private static final String[] CONTENT = new String[] { "Scan!", "Calculate", "History" };

    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return super.getPageTitle(position);
    }


    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new DemoCameraFragment_();//.newInstance(false);
            case 1:
                return  TestFragment.newInstance(CONTENT[position % CONTENT.length]);
            case 2:
                return  TestFragment.newInstance(CONTENT[position % CONTENT.length]);
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
