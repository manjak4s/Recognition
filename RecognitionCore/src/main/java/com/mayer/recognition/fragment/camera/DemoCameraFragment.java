/***
 7  Copyright (c) 2013 CommonsWare, LLC

 Licensed under the Apache License, Version 2.0 (the "License"); you may
 not use this file except in compliance with the License. You may obtain
 a copy of the License at
 http://www.apache.org/licenses/LICENSE-2.0
 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */

package com.mayer.recognition.fragment.camera;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.hardware.Camera.Face;
import android.hardware.Camera.Parameters;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Toast;

import com.commonsware.cwac.camera.CameraFragment;
import com.commonsware.cwac.camera.CameraHost;
import com.commonsware.cwac.camera.CameraUtils;
import com.commonsware.cwac.camera.CameraView;
import com.commonsware.cwac.camera.SimpleCameraHost;
import com.commonsware.cwac.camera.PictureTransaction;
import com.mayer.recognition.R;
import com.mayer.recognition.util.Logger;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.OptionsMenuItem;
import org.androidannotations.annotations.SeekBarProgressChange;
import org.androidannotations.annotations.ViewById;

@EFragment(R.layout.fragment)
@OptionsMenu(R.menu.camera)
public class DemoCameraFragment extends CameraFragment {

    protected static final String KEY_USE_FFC = "com.commonsware.cwac.camera.demo.USE_FFC";

    @OptionsMenuItem(R.id.single_shot)
    protected MenuItem singleShotItem ;

    @OptionsMenuItem(R.id.autofocus)
    protected MenuItem autoFocusItem;

    @OptionsMenuItem(R.id.camera)
    protected MenuItem takePictureItem;

    @OptionsMenuItem(R.id.flash)
    protected MenuItem flashItem;

    @OptionsMenuItem(R.id.record)
    protected MenuItem recordItem;

    @OptionsMenuItem(R.id.stop)
    protected MenuItem stopRecordItem;

    @OptionsMenuItem(R.id.mirror_ffc)
    protected MenuItem mirrorFFC;

    @OptionsMenuItem(R.id.show_zoom)
    protected MenuItem showZoom;

    @ViewById(R.id.zoom)
    protected SeekBar zoom;

    @ViewById(R.id.camera)
    protected CameraView camera;

    protected boolean singleShotProcessing = false;
    protected long lastFaceToast = 0L;
    protected String flashMode = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return null;
    }

    public CameraHost getHost() {
        return new SimpleCameraHost.Builder(new DemoCameraHost(getActivity())).useFullBleedPreview(true).build();
    }

    @AfterViews
    protected void init() {
        setCameraView(camera);
        setRecordingItemVisibility();
        zoom.setKeepScreenOn(true);
        camera.setHost(getHost());
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().invalidateOptionsMenu();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        singleShotItem.setChecked(getContract().isSingleShotMode());
        if (isRecording()) {
            recordItem.setVisible(false);
            stopRecordItem.setVisible(true);
            takePictureItem.setVisible(false);
        }
        setRecordingItemVisibility();
    }

    @OptionsItem(R.id.camera)
    protected void actionCamera() {
        takeSimplePicture();
    }

    @OptionsItem(R.id.record)
    protected void actionRecord() {
        try {
            record();
            getActivity().invalidateOptionsMenu();
        } catch (Exception e) {
            Log.e(getClass().getSimpleName(), "Exception trying to record", e);
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @OptionsItem(R.id.stop)
    protected void actionStop() {
        try {
            stopRecording();
            getActivity().invalidateOptionsMenu();
        } catch (Exception e) {
            Log.e(getClass().getSimpleName(), "Exception trying to stop recording", e);
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @OptionsItem(R.id.autofocus)
    protected void actionAutoFocus() {
        takePictureItem.setEnabled(false);
        autoFocus();
    }

    @OptionsItem(R.id.single_shot)
    protected void actionSingleShot() {
        singleShotItem.setChecked(!singleShotItem.isChecked());
        getContract().setSingleShotMode(singleShotItem.isChecked());
    }

    @OptionsItem(R.id.show_zoom)
    protected void actionShowZoom() {
        showZoom.setChecked(!showZoom.isChecked());
        zoom.setVisibility(showZoom.isChecked() ? View.VISIBLE : View.GONE);
    }

    @OptionsItem(R.id.flash)
    protected void actionFlash() {
        flashItem.setChecked(!flashItem.isChecked());
    }

    @OptionsItem(R.id.mirror_ffc)
    protected void actionMirrorFfc() {
        mirrorFFC.setChecked(!mirrorFFC.isChecked());
    }

    @SeekBarProgressChange(R.id.zoom)
    void onProgressChangeOnSeekBar(SeekBar seekBar, int progress, boolean fromUser) {
        if (!fromUser) {
            return;
        }
        zoom.setEnabled(false);
        zoomTo(zoom.getProgress()).onComplete(new Runnable() {
            @Override
            public void run() {
                zoom.setEnabled(true);
            }
        }).go();
    }

    boolean isSingleShotProcessing() {
        return (singleShotProcessing);
    } // ignore


    void setRecordingItemVisibility() {
        if (zoom != null && recordItem != null) {
            if (getDisplayOrientation() != 0
                    && getDisplayOrientation() != 180) {
                recordItem.setVisible(false);
            }
        }
    }

    Contract getContract() {
        return ((Contract) getActivity());
    }

    public void takeSimplePicture() {
        if (singleShotItem != null && singleShotItem.isChecked()) {
            singleShotProcessing = true;
            takePictureItem.setEnabled(false);
        }

        PictureTransaction xact = new PictureTransaction(getHost());

        if (flashItem != null && flashItem.isChecked()) {
            xact.flashMode(flashMode);
        }

        takePicture(xact);
    }

    public interface Contract {
        boolean isSingleShotMode();

        void setSingleShotMode(boolean mode);
    }

    class DemoCameraHost extends SimpleCameraHost implements
            Camera.FaceDetectionListener {
        boolean supportsFaces = false;

        public DemoCameraHost(Context _ctxt) {
            super(_ctxt);
        }

        @Override
        public boolean useFrontFacingCamera() {
            if (getArguments() == null) {
                return (false);
            }

            return (getArguments().getBoolean(KEY_USE_FFC));
        }

        @Override
        public boolean useSingleShotMode() {
            if (singleShotItem == null) {
                return (false);
            }

            return (singleShotItem.isChecked());
        }

        @Override
        public void saveImage(PictureTransaction xact, byte[] image) {
            if (useSingleShotMode()) {
                singleShotProcessing = false;

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        takePictureItem.setEnabled(true);
                    }
                });

//        DisplayActivity.imageToShow=image;
//        startActivity(new Intent(getActivity(), DisplayActivity.class));
            } else {
                super.saveImage(xact, image);
            }
        }

        @Override
        public void autoFocusAvailable() {
            if (autoFocusItem != null) {
                autoFocusItem.setEnabled(true);

                if (supportsFaces)
                    startFaceDetection();
            }
        }

        @Override
        public void autoFocusUnavailable() {
            if (autoFocusItem != null) {
                stopFaceDetection();

                if (supportsFaces)
                    autoFocusItem.setEnabled(false);
            }
        }

        @Override
        public void onCameraFail(CameraHost.FailureReason reason) {
            super.onCameraFail(reason);

            Toast.makeText(getActivity(),
                    "Sorry, but you cannot use the camera now!",
                    Toast.LENGTH_LONG).show();
        }

        @Override
        public Parameters adjustPreviewParameters(Parameters parameters) {
            Logger.d("ping!");
            flashMode =
                    CameraUtils.findBestFlashModeMatch(parameters,
                            Camera.Parameters.FLASH_MODE_RED_EYE,
                            Camera.Parameters.FLASH_MODE_AUTO,
                            Camera.Parameters.FLASH_MODE_ON);

            if (doesZoomReallyWork() && parameters.getMaxZoom() > 0) {
                zoom.setMax(parameters.getMaxZoom());
//                zoom.setOnSeekBarChangeListener(DemoCameraFragment.this);
            } else {
                zoom.setEnabled(false);
            }

            if (parameters.getMaxNumDetectedFaces() > 0) {
                supportsFaces = true;
            } else {
                Toast.makeText(getActivity(),
                        "Face detection not available for this camera",
                        Toast.LENGTH_LONG).show();
            }

            return (super.adjustPreviewParameters(parameters));
        }

        @Override
        public void onFaceDetection(Face[] faces, Camera camera) {
            if (faces.length > 0) {
                long now = SystemClock.elapsedRealtime();

                if (now > lastFaceToast + 10000) {
                    Toast.makeText(getActivity(), "I see your face!",
                            Toast.LENGTH_LONG).show();
                    lastFaceToast = now;
                }
            }
        }

        @Override
        @TargetApi(16)
        public void onAutoFocus(boolean success, Camera camera) {
            super.onAutoFocus(success, camera);

            takePictureItem.setEnabled(true);
        }

        @Override
        public boolean mirrorFFC() {
            return (mirrorFFC.isChecked());
        }
    }
}