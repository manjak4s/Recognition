package com.mayer.recognition.fragment.camera;

import android.app.Fragment;
import android.widget.ImageView;

import com.mayer.recognition.R;
import com.mayer.recognition.componenet.camera.CameraControlsView;
import com.mayer.recognition.componenet.camera.CameraSnapshotControlsView;
import com.mayer.recognition.fragment.BasicFragmentV4;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

/**
 * Created by irikhmayer on 15.01.2015.
 */
@EFragment(R.layout.camera_snapshot_fragment)
public class CameraSnapshotFragment extends BasicFragmentV4 {

    @ViewById
    protected ImageView snapshot;

    @ViewById(R.id.controls)
    protected CameraSnapshotControlsView controls;
}
