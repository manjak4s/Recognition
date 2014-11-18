package com.mayer.recognition.componenet.camera;

import android.content.Context;
import android.util.AttributeSet;
import android.view.SurfaceView;

import com.commonsware.cwac.camera.CameraView;

import org.androidannotations.annotations.EView;

/**
 * Created by irikhmayer on 10.11.2014.
 */
@EView
public class CameraPreView extends CameraView {

    public CameraPreView(Context context, SurfaceView sv) {
        super(context);
    }

    public CameraPreView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CameraPreView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
}