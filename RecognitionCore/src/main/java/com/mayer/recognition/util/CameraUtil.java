package com.mayer.recognition.util;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Build;

/**
 * Created by irikhmayer on 10.11.2014.
 */
public class CameraUtil {

    public static boolean checkCameraHardware(Context context) {
        Logger.d("Number of cameras : " + getCameraCount());
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }

    public static int getCameraCount() {
        return Camera.getNumberOfCameras();
    }

    /** A safe way to get an instance of the Camera object. */
    public static Camera getCameraInstance(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            return  Camera.open(0); // This is the line the error occurs
        } else {
            return Camera.open();
        }
    }
}
