package com.mayer.recognition.activity;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;

import com.commonsware.cwac.camera.CameraHost;
import com.commonsware.cwac.camera.CameraHostProvider;
import com.commonsware.cwac.camera.SimpleCameraHost;
import com.mayer.recognition.R;
import com.mayer.recognition.adapter.DrawerManuAdapter;
import com.mayer.recognition.adapter.MenuAdapter;
import com.mayer.recognition.adapter.TabsPagerAdapter;
import com.mayer.recognition.fragment.camera.PreviewCameraFragment;
import com.mayer.recognition.componenet.drawer.SmartDrawerRecyclerView;
import com.mayer.recognition.util.Logger;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.InstanceState;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;


/**
 * Created by dot on 16.11.2014.
 */
@EActivity(R.layout.activity_main)
@OptionsMenu(R.menu.launcher)
public class BasicNavigationActivity extends ActionBarActivity implements CameraHostProvider,
        PreviewCameraFragment.Contract,
        ViewPager.OnPageChangeListener,
        AdapterView.OnItemClickListener {

    @ViewById
    protected Toolbar toolbar;

    @ViewById
    protected DrawerLayout drawerLayout;

    @ViewById
    protected SmartDrawerRecyclerView navigationDrawer;

    @ViewById
    protected ViewPager pager;

    @InstanceState
    protected CharSequence navigatorTitle;

    @InstanceState
    protected int drawerPisition;

    protected DrawerManuAdapter drawerAdapter;

    private ActionBarDrawerToggle toggle;

    protected TabsPagerAdapter fragmentAdapter;

    private enum DRAWER_STATE {
        CLOSED, OPENING, OPENED, CLOSING;
    }

    @AfterViews
    protected void init() {
        setSupportActionBar(toolbar);

        fragmentAdapter = new TabsPagerAdapter(getFragmentManager());
        pager.setAdapter(fragmentAdapter);
        pager.setOnPageChangeListener(this);

        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close) {

            private DRAWER_STATE toggleMode;

            public void onDrawerStateChanged(int newState) {
                if (newState == DrawerLayout.STATE_SETTLING || newState == DrawerLayout.STATE_DRAGGING) {
                    toggleMode = drawerLayout.isDrawerOpen(Gravity.LEFT) ? DRAWER_STATE.CLOSING : DRAWER_STATE.OPENING;
                } else if (newState == DrawerLayout.STATE_IDLE) {
                    toggleMode = toggleMode == DRAWER_STATE.OPENING ? DRAWER_STATE.OPENED : DRAWER_STATE.CLOSED;
                }
                if (toggleMode == DRAWER_STATE.OPENED || toggleMode == DRAWER_STATE.OPENING) {
                    navigationDrawer.navigateTo(drawerPisition);
                }
            }
        };

        toggle.setDrawerIndicatorEnabled(true);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        drawerAdapter = new DrawerManuAdapter(this);

        drawerAdapter.setOnListItemClickListener(this);
        drawerAdapter.setList(TabsPagerAdapter.getTabs());

        navigationDrawer.setAdapter(drawerAdapter);
        Logger.d("sup " + drawerPisition);
        navigationDrawer.navigateTo(drawerPisition);
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
        //do nothing
    }

    @Override
    public void onPageScrolled(int position, float ignore1, int ignore2) {
    }

    @Override
    public void onPageSelected(int position) {
        Logger.d("onPageSelected " + position);
        drawerPisition = position;
        navigationDrawer.navigateTo(drawerPisition);
    }

    @Override
    public void onPageScrollStateChanged(int i) {
        //do nothing
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        drawerPisition = position;
        pager.setCurrentItem(drawerPisition);
    }
}