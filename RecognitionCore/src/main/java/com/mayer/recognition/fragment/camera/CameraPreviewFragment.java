package com.mayer.recognition.fragment.camera;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.commonsware.cwac.camera.CameraFragment;
import com.commonsware.cwac.camera.CameraView;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import com.mayer.recognition.R;
import com.mayer.recognition.componenet.CameraPreView;

/**
 * Created by irikhmayer on 10.11.2014.
 */
@EFragment(R.layout.camera_preview_fragment)
public class CameraPreviewFragment extends CameraFragment {

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View content = inflater.inflate(R.layout.camera_preview_fragment, container, false);
        CameraView cameraView = (CameraView) content.findViewById(R.id.camera);

        setCameraView(cameraView);

        return (content);
    }

}