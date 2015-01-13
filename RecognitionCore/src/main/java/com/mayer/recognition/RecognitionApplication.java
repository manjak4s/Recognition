package com.mayer.recognition;

import android.app.Application;

import com.mayer.recognition.database.StorageHelper;
import com.mayer.recognition.prefs.SharedPreferences_;
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

    private SharedPreferences_ shopPref;

    private StorageHelper openHelper;

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
        initLazyPref();
        initPref();
    }

    public SharedPreferences_ getShopPref() {
        initLazyPref();
        return shopPref;
    }

    @Background
    public void initPref() {

    }

    private synchronized void initLazyDB() {
        if (openHelper == null) {
            openHelper = new StorageHelper(this);
            openHelper.recompileDatabase();
            openHelper.close();
        }
    }

    private synchronized void initLazyPref() {
        if (shopPref == null) {
            shopPref = new SharedPreferences_(RecognitionApplication.this);
        }
    }
}
