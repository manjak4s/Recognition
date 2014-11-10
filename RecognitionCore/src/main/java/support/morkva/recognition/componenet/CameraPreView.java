package support.morkva.recognition.componenet;

import android.content.Context;
import android.hardware.Camera;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EView;

import java.io.IOException;
import java.util.List;

import support.morkva.recognition.util.Logger;

/**
 * Created by irikhmayer on 10.11.2014.
 */
@EView
public class CameraPreView extends SurfaceView implements SurfaceHolder.Callback {

    private final String TAG = "Preview";

    protected SurfaceHolder holderInstance;
    protected Camera.Size previewSize;
    protected List<Camera.Size> mSupportedPreviewSizes;
    protected Camera cameraInstance;
    protected boolean isPreviewRunning;

    public CameraPreView(Context context, SurfaceView sv) {
        super(context);
    }

    public CameraPreView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CameraPreView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @AfterViews
    protected void initUI() {
        Logger.d("initUI");
        holderInstance = getHolder();
        holderInstance.addCallback(this);
        holderInstance.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    public void setCamera(Camera camera) {
        Logger.d("setCamera");
        cameraInstance = camera;
        if (cameraInstance != null) {
            Logger.d("setCamera ok");
            mSupportedPreviewSizes = cameraInstance.getParameters().getSupportedPreviewSizes();
            requestLayout();

            // get Camera parameters
            Camera.Parameters params = cameraInstance.getParameters();

            List<String> focusModes = params.getSupportedFocusModes();
            if (focusModes.contains(Camera.Parameters.FOCUS_MODE_AUTO)) {
                // set the focus mode
                params.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
                // set Camera parameters
                cameraInstance.setParameters(params);
            }
        } else {
            Logger.d("setCamera null");
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Logger.d("onMeasure");
        // We purposely disregard child measurements because act as a
        // wrapper to a SurfaceView that centers the camera preview instead
        // of stretching it.
        final int width = resolveSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        final int height = resolveSize(getSuggestedMinimumHeight(), heightMeasureSpec);
        setMeasuredDimension(width, height);

        if (mSupportedPreviewSizes != null) {
            previewSize = getOptimalPreviewSize(mSupportedPreviewSizes, width, height);
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // The Surface has been created, acquire the camera and tell it where
        // to draw.
        try {
            if (cameraInstance != null) {
                cameraInstance.setPreviewDisplay(holder);
                Logger.d("surfaceCreated ok");
            } else {
                Logger.d("surfaceCreated null");
            }
        } catch (IOException exception) {
            Log.e(TAG, "IOException caused by setPreviewDisplay()", exception);
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Logger.d("surfaceDestroyed");
        // Surface will be destroyed when we return, so stop the preview.
        if (cameraInstance != null) {
            cameraInstance.stopPreview();
            Logger.d("surfaceDestroyed ok ");
        } else {
            Logger.d("surfaceDestroyed null");
        }
    }


    private Camera.Size getOptimalPreviewSize(List<Camera.Size> sizes, int w, int h) {
        final double ASPECT_TOLERANCE = 0.1;
        double targetRatio = (double) w / h;
        if (sizes == null) return null;

        Camera.Size optimalSize = null;
        double minDiff = Double.MAX_VALUE;

        int targetHeight = h;

        // Try to find an size match aspect ratio and size
        for (Camera.Size size : sizes) {
            double ratio = (double) size.width / size.height;
            if (Math.abs(ratio - targetRatio) > ASPECT_TOLERANCE) continue;
            if (Math.abs(size.height - targetHeight) < minDiff) {
                optimalSize = size;
                minDiff = Math.abs(size.height - targetHeight);
            }
        }

        // Cannot find the one match the aspect ratio, ignore the requirement
        if (optimalSize == null) {
            minDiff = Double.MAX_VALUE;
            for (Camera.Size size : sizes) {
                if (Math.abs(size.height - targetHeight) < minDiff) {
                    optimalSize = size;
                    minDiff = Math.abs(size.height - targetHeight);
                }
            }
        }
        return optimalSize;
    }

    public void previewCamera() {
        try {
            cameraInstance.setPreviewDisplay(getHolder());
            cameraInstance.startPreview();
            isPreviewRunning = true;
        } catch (Exception e) {
            Logger.d("Cannot start preview", e);
        }
    }
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Logger.d("surfaceChanged");
        try {
            if (isPreviewRunning) {
                cameraInstance.stopPreview();
            }

            Camera.Parameters parameters = cameraInstance.getParameters();
            Display display = ((WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();

            if (display.getRotation() == Surface.ROTATION_0) {
                parameters.setPreviewSize(height, width);
                cameraInstance.setDisplayOrientation(90);
            }

            if (display.getRotation() == Surface.ROTATION_90) {
                parameters.setPreviewSize(width, height);
            }

            if (display.getRotation() == Surface.ROTATION_180) {
                parameters.setPreviewSize(height, width);
            }

            if (display.getRotation() == Surface.ROTATION_270) {
                parameters.setPreviewSize(width, height);
                cameraInstance.setDisplayOrientation(180);
            }

            cameraInstance.setParameters(parameters);
            previewCamera();
        } catch (Exception e) {
            Logger.e("failked", e);
        }
    }



}