package com.mayer.recognition.adapter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;

import com.mayer.recognition.R;
import com.mayer.recognition.fragment.camera.CameraPreviewFragment_;
import com.mayer.recognition.fragment.history.HistoryOrderItemListFragment_;
import com.mayer.recognition.model.ui.TabViewModel;
import com.mayer.recognition.util.Logger;
import com.mayer.recognition.util.ResourceHelper;

import java.util.ArrayList;
import java.util.List;

public class TabsPagerAdapter extends FragmentPagerAdapter {

    protected static final List<TabViewModel> TABS;

    static {
        TABS = new ArrayList<TabViewModel>();
        TABS.add(0, new TabViewModel() {
            @Override
            public Fragment getFragmentInstance() {
                return new CameraPreviewFragment_();
            }

            @Override
            public String getTitle() {
                return ResourceHelper.getString(R.string.title_scan);
            }
        });
        TABS.add(1, new TabViewModel() {
            @Override
            public Fragment getFragmentInstance() {
                return new HistoryOrderItemListFragment_();
            }

            @Override
            public String getTitle() {
                return ResourceHelper.getString(R.string.title_calculate);
            }
        });
        TABS.add(2, new TabViewModel() {
            @Override
            public Fragment getFragmentInstance() {
                return new HistoryOrderItemListFragment_();
            }

            @Override
            public String getTitle() {
                return ResourceHelper.getString(R.string.title_history);
            }
        });
    }

    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public static List<TabViewModel> getTabs() {
        Logger.d("size is " + TABS.size());
        return TABS;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return super.getPageTitle(position);
    }


    @Override
    public Fragment getItem(int position) {
        return TABS.get(position).getFragmentInstance();
    }

    @Override
    public int getCount() {
        return TABS.size();
    }

}
