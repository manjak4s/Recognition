package com.mayer.recognition.activity;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.commonsware.cwac.camera.CameraHost;
import com.commonsware.cwac.camera.CameraHostProvider;
import com.commonsware.cwac.camera.SimpleCameraHost;
import com.mayer.recognition.R;
import com.mayer.recognition.adapter.DrawerManuAdapter;
import com.mayer.recognition.adapter.MenuAdapter;
import com.mayer.recognition.adapter.TabsPagerAdapter;
import com.mayer.recognition.fragment.camera.PreviewCameraFragment;
import com.mayer.recognition.fragment.camera.PreviewCameraFragment_;
import com.mayer.recognition.model.ui.TabViewModel;
import com.mayer.recognition.util.Logger;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.InstanceState;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by dot on 16.11.2014.
 */
@EActivity(R.layout.activity_main)
@OptionsMenu(R.menu.launcher)
public class BasicNavigationActivity extends ActionBarActivity implements CameraHostProvider,
                                                                          PreviewCameraFragment.Contract, MenuAdapter.IListClickListener, ViewPager.OnPageChangeListener {

    @ViewById
    protected Toolbar toolbar;

    @ViewById
    protected DrawerLayout drawerLayout;

    @ViewById
    protected RecyclerView navigationDrawer;

    @ViewById
    protected ViewPager pager;

    @InstanceState
    protected CharSequence navigatorTitle;

    protected DrawerManuAdapter drawerAdapter;

    private ActionBarDrawerToggle toggle;

    protected TabsPagerAdapter fragmentAdapter;

    @AfterViews
    protected void init() {
        setSupportActionBar(toolbar);

        fragmentAdapter = new TabsPagerAdapter(getFragmentManager());
        pager.setAdapter(fragmentAdapter);
        pager.setOnPageChangeListener(this);

        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);

        toggle.setDrawerIndicatorEnabled(true);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        navigationDrawer.setLayoutManager(new LinearLayoutManager(this));
        navigationDrawer.setItemAnimator(new DefaultItemAnimator());
        navigationDrawer.setHasFixedSize(true);
        drawerAdapter = new DrawerManuAdapter(this);
        drawerAdapter.setOnListItemClickListener(this).setList(TabsPagerAdapter.getTabs());
        navigationDrawer.setAdapter(drawerAdapter);

    }

    @Override
    public CameraHost getCameraHost() {
        return(new SimpleCameraHost(this));
    }

    @Override
    public boolean isSingleShotMode() {
        return false;
    }

    @Override
    public void setSingleShotMode(boolean mode) {

    }

    @Override
    public void onClick(View caller, int pos) {
        pager.setCurrentItem(pos);
    }

    @Override
    public void onLongClick(View caller, int pos) {

    }

    @Override
    public void onPageScrolled(int i, float v, int i2) {

    }

    @Override
    public void onPageSelected(int i) {

    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }
}