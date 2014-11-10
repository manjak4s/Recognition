package support.morkva.recognition.util;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;

/**
 * Created by irikhmayer on 10.11.2014.
 */
public class CameraUtil {

    public static boolean checkCameraHardware(Context context) {
        Logger.d("Number of cameras : " + Camera.getNumberOfCameras());
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }

    /** A safe way to get an instance of the Camera object. */
    public static Camera getCameraInstance(){
        Camera c = null;
        try {
            c = Camera.open(); // attempt to get a Camera instance
        }
        catch (Exception e){
            // Camera is not available (in use or does not exist)
        }
        return c; // returns null if camera is unavailable
    }
}
