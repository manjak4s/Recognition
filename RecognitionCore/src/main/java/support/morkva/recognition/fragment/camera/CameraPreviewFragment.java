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
        super.onResume();
        Logger.d("visible is " + isVisible());
        if (getUserVisibleHint() || true) {
            Logger.d("onResume ok");
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
        } else {
            Logger.d("onResume no");
        }
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        // Make sure that we are currently visible
        if (this.isVisible()) {
            // If we are becoming invisible, then...
            if (!isVisibleToUser) {
                Logger.d("Not visible anymore.  Stopping audio.");
                // TODO stop audio playback
            }
        } else {

            Logger.d("visible!");
        }
    }
    @Override
    public void onPause() {
        if (!getUserVisibleHint() || true) {
            Logger.d("onPause");
            if (camera != null) {
                camera.stopPreview();
                surfaceCameraComponent.setCamera(null);
                camera.release();
                camera = null;
            }
        } else {
            Logger.d("onResume no");

        }
        super.onPause();
    }
}