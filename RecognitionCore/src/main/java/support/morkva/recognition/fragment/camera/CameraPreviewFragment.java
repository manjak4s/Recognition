package support.morkva.recognition.fragment.camera;

import android.hardware.Camera;
import android.os.Handler;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import support.morkva.recognition.R;
import support.morkva.recognition.componenet.CameraPreView;
import support.morkva.recognition.fragment.BasicFragment;
import support.morkva.recognition.util.CameraUtil;
import support.morkva.recognition.util.Logger;

/**
 * Created by irikhmayer on 10.11.2014.
 */
@EFragment(R.layout.camera_preview_fragment)
public class CameraPreviewFragment extends BasicFragment {

    @ViewById
    protected CameraPreView surfaceCameraComponent;

    protected Camera camera;

    @AfterViews
    protected void init() {
//        surfaceCameraComponent.setCamera(CameraUtil.getCameraInstance());
    }

    @Override
    public void onResume() {
        Logger.d("onResume");
        super.onResume();
        int numCams = Camera.getNumberOfCameras();
        if (numCams > 0) {
            try {
                camera = Camera.open(0);
                camera.startPreview();
                surfaceCameraComponent.setCamera(camera);
            } catch (RuntimeException ex) {
                Logger.e("failed", ex);
            }
        }
    }

    @Override
    public void onPause() {
        Logger.d("onPause");
        if (camera != null) {
            camera.stopPreview();
            surfaceCameraComponent.setCamera(null);
            camera.release();
            camera = null;
        }
        super.onPause();
    }
}