package com.mayer.recognition.activity;

import android.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.commonsware.cwac.camera.CameraHost;
import com.commonsware.cwac.camera.CameraHostProvider;
import com.commonsware.cwac.camera.SimpleCameraHost;
import com.mayer.recognition.R;
import com.mayer.recognition.adapter.DrawerManuAdapter;
import com.mayer.recognition.fragment.camera.PreviewCameraFragment;
import com.mayer.recognition.fragment.camera.PreviewCameraFragment_;
import com.mayer.recognition.model.ui.TabViewModel;

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
                                                                          PreviewCameraFragment.Contract {

    @ViewById
    protected Toolbar toolbar;

    @ViewById
    protected DrawerLayout drawerLayout;

    @ViewById
    protected RecyclerView navigationDrawer;

    @InstanceState
    protected CharSequence navigatorTitle;

    protected DrawerManuAdapter drawerAdapter;

    private ActionBarDrawerToggle toggle;

    protected static final List<TabViewModel> TABS;

    static {
        TABS = new ArrayList<TabViewModel>();
        TABS.add(0, new TabViewModel() {
            @Override
            public Fragment getFragmentInstance() {
                return new PreviewCameraFragment_();
            }

            @Override
            public String getTitle() {
                return "Test";
            }
        });
    }

    @AfterViews
    protected void init() {
        setSupportActionBar(toolbar);

        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);

        toggle.setDrawerIndicatorEnabled(true);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        navigationDrawer.setLayoutManager(new LinearLayoutManager(this));

        navigationDrawer.setHasFixedSize(true);
        drawerAdapter = new DrawerManuAdapter(this);
        drawerAdapter.setList(TABS);
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
}