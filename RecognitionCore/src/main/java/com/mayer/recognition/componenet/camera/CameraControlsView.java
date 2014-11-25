package com.mayer.recognition.componenet.camera;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.ToggleButton;

import com.mayer.recognition.R;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * Created by dot on 14.11.2014.
 */
@EViewGroup(R.layout.camera_controls)
public class CameraControlsView extends GridLayout {

    @ViewById(R.id.take_picture)
    public ImageButton shoot;


    @ViewById(R.id.flashIco)
    public FlashButton flash;

    @ViewById(R.id.rotateIco)
    public FfcRfcCameraButton rotate;

    @ViewById(R.id.zoom)
    public SeekBar zoom;

    public CameraControlsView(Context context) {
        super(context);
    }

    public CameraControlsView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CameraControlsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
