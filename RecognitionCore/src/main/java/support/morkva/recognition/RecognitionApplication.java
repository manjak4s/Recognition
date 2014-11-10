package support.morkva.recognition;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EApplication;

import support.morkva.recognition.util.CameraUtil;
import support.morkva.recognition.util.Logger;

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
