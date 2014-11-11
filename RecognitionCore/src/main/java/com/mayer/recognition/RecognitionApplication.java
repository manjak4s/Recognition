package com.mayer.recognition;

import android.app.Application;

import com.mayer.recognition.util.CameraUtil;
import com.mayer.recognition.util.Logger;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EApplication;

/**
 * Created by irikhmayer on 06.11.2014.
 */
@EApplication
public class RecognitionApplication extends Application {

    private static RecognitionApplication self;

    public static final RecognitionApplication get() {
        return self;
    }

    @Override public void onCreate() {
        super.onCreate();
        self = this;
        if (!CameraUtil.checkCameraHardware(this)) {
            Logger.d("Device has no cams");
        } else {
            Logger.d("Device has a cam");
        }
    }

    @Background
    public void initPref() {

    }
}
