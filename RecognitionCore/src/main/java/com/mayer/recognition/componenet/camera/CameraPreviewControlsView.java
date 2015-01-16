package com.mayer.recognition.componenet.camera;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.SeekBar;

import com.mayer.recognition.R;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * Created by dot on 14.11.2014.
 */
@EViewGroup(R.layout.camera_preview_controls)
public class CameraPreviewControlsView extends GridLayout {

    @ViewById(R.id.take_picture)
    public ImageButton shoot;

    @ViewById(R.id.flashIco)
    public CameraFlashButton flash;

    @ViewById(R.id.rotateIco)
    public CameraFfcRfcButton rotate;

    @ViewById(R.id.zoom)
    public SeekBar zoom;

    public CameraPreviewControlsView(Context context) {
        super(context);
    }

    public CameraPreviewControlsView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CameraPreviewControlsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setEnabled(boolean on) {
        shoot.setEnabled(on);
        zoom.setEnabled(on);
        flash.setEnabled(on);
        rotate.setEnabled(on);
    }
}
