package com.mayer.recognition.componenet.camera;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridLayout;

import com.gc.materialdesign.views.ButtonFlat;
import com.mayer.recognition.R;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * Created by dot on 14.11.2014.
 */
@EViewGroup(R.layout.camera_snapshot_controls)
public class CameraSnapshotControlsView extends GridLayout {

    @ViewById(R.id.cancel_button)
    public ButtonFlat cancelBtn;

    @ViewById(R.id.ok_button)
    public CameraFlashButton okButton;

    public CameraSnapshotControlsView(Context context) {
        super(context);
    }

    public CameraSnapshotControlsView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CameraSnapshotControlsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setEnabled(boolean on) {
        cancelBtn.setEnabled(on);
        okButton.setEnabled(on);
    }
}
