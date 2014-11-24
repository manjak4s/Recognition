package com.mayer.recognition.componenet.camera;

import android.content.Context;
import android.hardware.Camera.Parameters;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ToggleButton;

import com.mayer.recognition.R;
import com.mayer.recognition.util.CameraUtil;

import org.androidannotations.annotations.EView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dot on 23.11.2014.
 */
@EView
public class FfcRfcCameraButton extends ToggleButton implements View.OnClickListener {

    private String mState;
    private CameraFacingListener mFlashListener;

    @Override
    public void onClick(View v) {
        if (mFlashListener == null) {
            return;
        }
        if (isChecked()) {
            mFlashListener.onFFC();
        } else {
            mFlashListener.onRFC();
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
        return mFlashListener;
    }

    public void setFlashListener(CameraFacingListener flashListener) {
        this.mFlashListener = flashListener;
    }
}