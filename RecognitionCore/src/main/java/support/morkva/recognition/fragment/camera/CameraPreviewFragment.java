package support.morkva.recognition.fragment.camera;

import android.hardware.Camera;

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
        surfaceCameraComponent.setCamera(CameraUtil.getCameraInstance());
    }

    @Override
    public void onResume() {
        super.onResume();
        int numCams = Camera.getNumberOfCameras();
        if(numCams > 0){
            try{
                Logger.d("camera under init");
                camera = Camera.open(0);
                camera.startPreview();
                surfaceCameraComponent.setCamera(camera);
            } catch (RuntimeException ex){
                Logger.d("Sorry, no cameras");
            }
        }
    }

    @Override
    public void onPause() {
        if(camera != null) {
            camera.stopPreview();
            surfaceCameraComponent.setCamera(null);
            camera.release();
            camera = null;
        }
        super.onPause();
    }

    private void resetCam() {
        camera.startPreview();
        surfaceCameraComponent.setCamera(camera);
    }

}
