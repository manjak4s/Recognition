package com.mayer.recognition.activity;

import android.os.Bundle;

import com.commonsware.cwac.camera.CameraHost;
import com.commonsware.cwac.camera.CameraHostProvider;
import com.commonsware.cwac.camera.SimpleCameraHost;
import com.mayer.recognition.R;
import com.mayer.recognition.fragment.camera.CameraPreviewFragment;
import com.mayer.recognition.model.pojo.CameraPictureResult;
import com.mayer.recognition.util.Logger;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsMenu;

import de.greenrobot.event.EventBus;

/**
 * Created by irikhmayer on 16.01.2015.
 */
@EActivity(R.layout.activity_main)
@OptionsMenu(R.menu.launcher)
public class MainActivity extends BasicNavigationActivity  implements CameraHostProvider, CameraPreviewFragment.Contract {

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    public void onEvent(CameraPictureResult result) {
        Logger.d("image receivec");
    }
}
