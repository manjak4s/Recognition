package com.mayer.recognition.activity;

import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;

import com.mayer.recognition.R;
import com.mayer.recognition.adapter.DrawerManuAdapter;
import com.mayer.recognition.adapter.TabsPagerAdapter;
import com.mayer.recognition.componenet.drawer.SmartDrawerRecyclerView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.InstanceState;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;


/**
 * Created by dot on 16.11.2014.
 */
@EActivity
@OptionsMenu(R.menu.launcher)
public abstract class BasicNavigationActivity extends ActionBarActivity implements ViewPager.OnPageChangeListener, AdapterView.OnItemClickListener {

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

    protected ActionBarDrawerToggle toggle;

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

        drawerAdapter = new DrawerManuAdapter(this);

        drawerAdapter.setOnListItemClickListener(this);
        drawerAdapter.setList(TabsPagerAdapter.getTabs(), drawerPisition);

        navigationDrawer.setAdapter(drawerAdapter);
    }

    @Override
    public void onBackPressed() {
        if (drawerPisition != 0) {
            drawerPisition = 0;
            onItemClick(null, null, drawerPisition, 0);
        }
    }

    @Override
    public void onPageScrolled(int position, float ignore1, int ignore2) {
    }

    @Override
    public void onPageSelected(int position) {
        drawerPisition = position;
        drawerAdapter.setSelectedItem(drawerPisition);
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