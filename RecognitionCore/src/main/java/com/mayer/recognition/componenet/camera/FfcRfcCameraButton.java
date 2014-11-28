package com.mayer.recognition.componenet.camera;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ToggleButton;

import com.mayer.recognition.util.CameraUtil;

import org.androidannotations.annotations.EView;

/**
 * Created by dot on 23.11.2014.
 */
@EView
public class FfcRfcCameraButton extends ToggleButton implements View.OnClickListener {

    private String mState;
    private CameraFacingListener flashListener;

    @Override
    public void onClick(View v) {
        if (flashListener == null) {
            return;
        }
        if (isChecked()) {
            flashListener.onFFC();
        } else {
            flashListener.onRFC();
        }
    }

    public interface CameraFacingListener {

        void onFFC();

        void onRFC();
    }


    public FfcRfcCameraButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        int count = CameraUtil.getCameraCount();
        if (count <= 1) {
            setEnabled(false);
            setClickable(false);
        } else {
            setOnClickListener(this);
        }
    }

    public CameraFacingListener getFlashListener() {
        return flashListener;
    }

    public void setRareFrontCameraChangeListener(CameraFacingListener flashListener) {
        this.flashListener = flashListener;
    }
}