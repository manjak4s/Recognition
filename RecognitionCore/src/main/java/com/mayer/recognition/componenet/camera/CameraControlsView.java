package com.mayer.recognition.componenet.camera;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ToggleButton;

import com.mayer.recognition.R;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * Created by dot on 14.11.2014.
 */
@EViewGroup(R.layout.camera_controls)
public class CameraControlsView extends GridLayout {

    @ViewById(R.id.flashIco)
    protected ToggleButton flash;

    @ViewById(R.id.rotateIco)
    protected ToggleButton rotate;

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
