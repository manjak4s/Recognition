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
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.SeekBar;
import android.widget.Toast;

import com.commonsware.cwac.camera.CameraFragment;
import com.commonsware.cwac.camera.CameraHost;
import com.commonsware.cwac.camera.CameraUtils;
import com.commonsware.cwac.camera.CameraView;
import com.commonsware.cwac.camera.SimpleCameraHost;
import com.commonsware.cwac.camera.PictureTransaction;
import com.mayer.recognition.R;
import com.mayer.recognition.componenet.VerticalSeekBar;
import com.mayer.recognition.componenet.camera.CameraPreviewControlsView;
import com.mayer.recognition.componenet.camera.CameraFfcRfcButton;
import com.mayer.recognition.componenet.camera.CameraFlashButton;
import com.mayer.recognition.model.pojo.CameraPictureResult;
import com.mayer.recognition.util.AnimatorUtil;
import com.mayer.recognition.util.LoactionUtil;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.InstanceState;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.OptionsMenuItem;
import org.androidannotations.annotations.SeekBarProgressChange;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.Date;

import de.greenrobot.event.EventBus;

@EFragment(R.layout.camera_preview_fragment)
@OptionsMenu(R.menu.camera)
public class CameraPreviewFragment extends CameraFragment {

    @OptionsMenuItem(R.id.single_shot)
    protected MenuItem singleShotItem;

    @OptionsMenuItem(R.id.mirror_ffc)
    protected MenuItem mirrorFFC;

    @OptionsMenuItem(R.id.show_zoom)
    protected MenuItem showZoom;

    @ViewById(R.id.controls)
    protected CameraPreviewControlsView controls;

    @ViewById(R.id.camera)
    protected CameraView camera;

    @ViewById(R.id.progress)
    protected FrameLayout progressFrame;

    protected boolean singleShotProcessing;

    protected CameraHost hostInstance;

    @InstanceState
    protected int zoomLevel;

    @InstanceState
    protected int cameraId = Camera.CameraInfo.CAMERA_FACING_BACK;

    @InstanceState
    protected String flashLevel;

    @InstanceState
    protected boolean takingAShot;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return null;
    }

    public CameraHost getHost() {
        if (hostInstance == null) {
            hostInstance = new SimpleCameraHost.Builder(new MayerCameraHost(getActivity())).useFullBleedPreview(true).build();
        }
        return hostInstance;
    }

    @UiThread
    protected void setProgress(boolean on) {
        controls.setEnabled(!on);
        camera.setClickable(!on);
        if (on) {
            progressFrame.setVisibility(View.VISIBLE);
        } else {
            progressFrame.setVisibility(View.GONE);
        }
    }

    @AfterViews
    protected void init() {
        setCameraView(camera);
        controls.zoom.setKeepScreenOn(true);
        camera.setHost(getHost());
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().invalidateOptionsMenu();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if (getActivity() == null) {
            return;
        }
        singleShotItem.setChecked(getContract().isSingleShotMode());

        if (isRecording()) {
            controls.shoot.setVisibility(View.GONE);
        }
    }

    @Click(R.id.take_picture)
    protected void actionCamera() {
        AnimatorUtil.animate(controls.shoot);
        takeSimplePicture();
    }

    @Click(R.id.camera)
    protected void actionAutoFocus() {
        enableShutter(false);
        cancelAutoFocus();
        setProgress(true);
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
        controls.zoom.setVisibility(showZoom.isChecked() ? View.VISIBLE : View.GONE);
    }

    @OptionsItem(R.id.mirror_ffc)
    protected void actionMirrorFfc() {
        mirrorFFC.setChecked(!mirrorFFC.isChecked());
    }

    @SeekBarProgressChange(R.id.zoom)
    void onProgressChangeOnSeekBar(final SeekBar seekBar, final int progress, boolean fromUser) {
        if (!fromUser) {
            return;
        }
        seekBar.setEnabled(false);
        zoomTo(progress).onComplete(new Runnable() {
            @Override
            public void run() {
                zoomLevel = progress;
                seekBar.setEnabled(true);
            }
        }).go();
    }

    protected Contract getContract() {
        return (Contract) getActivity();
    }

    public void takeSimplePicture() {
        if (singleShotItem != null && singleShotItem.isChecked()) {
            singleShotProcessing = true;
            enableShutter(false);
        }

        setProgress(true);
        PictureTransaction xact = new PictureTransaction(getHost());
        xact.flashMode(flashLevel);
        takePictureShedule(xact);
    }

    @Background
    protected void takePictureShedule(PictureTransaction xact) {
        takingAShot = true;
        takePicture(xact);
    }

    protected void zoom() {
        if (zoomLevel > 0) {
            if (controls.zoom instanceof VerticalSeekBar) {
                ((VerticalSeekBar) controls.zoom).setProgressAndThumb(zoomLevel);
            } else {
                controls.zoom.setProgress(zoomLevel);
                onProgressChangeOnSeekBar(controls.zoom, zoomLevel, true);
            }
        }
    }

    @UiThread
    protected void enableShutter(boolean enable) {
        controls.shoot.setEnabled(enable);
    }

    public interface Contract {
        boolean isSingleShotMode();

        void setSingleShotMode(boolean mode);
    }

    protected class MayerCameraHost extends SimpleCameraHost {

        public MayerCameraHost(Context ctxt) {
            super(ctxt);
        }

        @Override
        public int getCameraId() {
            return cameraId;
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
                enableShutter(true);

//        DisplayActivity.imageToShow=image;
//        startActivity(new Intent(getActivity(), DisplayActivity.class));
            } else {
//                super.saveImage(xact, image);
            }
            Location location = LoactionUtil.getBestLocation(getActivity());
            double longitude;
            double latitude;
            if (location != null) {
                longitude = location.getLongitude();
                latitude = location.getLongitude();
            } else {
                longitude = 0d;
                latitude = 0d;
            }
            CameraPictureResult result = new CameraPictureResult(image, longitude, latitude, new Date(), "Pic taken recently.");
            EventBus.getDefault().post(result);
            setProgress(false);
        }

        @Override
        public void autoFocusAvailable() {
            zoom();
        }

        @Override
        public void autoFocusUnavailable() { }

        @Override
        public void onCameraFail(CameraHost.FailureReason reason) {
            super.onCameraFail(reason);
            Toast.makeText(getActivity(), "Sorry, but you cannot use the camera now!", Toast.LENGTH_LONG).show();
        }

        @Override
        public Parameters adjustPreviewParameters(Parameters parameters) {
            if (flashLevel == null) {
                flashLevel = CameraUtils.findBestFlashModeMatch(parameters,
                        Parameters.FLASH_MODE_TORCH,
                        Parameters.FLASH_MODE_AUTO,
                        Parameters.FLASH_MODE_OFF);
            }
            controls.flash.setStates(camera.getCamera().getParameters().getSupportedFlashModes());

            controls.flash.setFlashListener(new CameraFlashButton.FlashListener() {
                @Override
                public void onAutomatic() {
                    camera.setFlashMode(flashLevel = Parameters.FLASH_MODE_AUTO);
                }

                @Override
                public void onOn() {
                    camera.setFlashMode(flashLevel = Parameters.FLASH_MODE_TORCH);
                }

                @Override
                public void onOff() {
                    camera.setFlashMode(flashLevel = Parameters.FLASH_MODE_OFF);
                }
            });

            controls.rotate.setRareFrontCameraChangeListener(new CameraFfcRfcButton.CameraFacingListener() {
                @Override
                public void onFFC() {
                    cameraId = Camera.CameraInfo.CAMERA_FACING_FRONT;
                    camera.resetCamera();
                }

                @Override
                public void onRFC() {
                    cameraId = Camera.CameraInfo.CAMERA_FACING_BACK;
                    camera.resetCamera();
                }
            });
            if (cameraId == Camera.CameraInfo.CAMERA_FACING_BACK) {
                camera.setFlashMode(flashLevel);
            }

            if (doesZoomReallyWork() && parameters.getMaxZoom() > 0) {
                controls.zoom.setMax(parameters.getMaxZoom());
                controls.zoom.setEnabled(true);
            } else {
                controls.zoom.setEnabled(false);
            }
            Parameters params = super.adjustPreviewParameters(parameters);
            return params;
        }

        @Override
        @TargetApi(16)
        public void onAutoFocus(boolean success, Camera camera) {
            super.onAutoFocus(success, camera);
            enableShutter(true);
            setProgress(false);
        }

        @Override
        public boolean mirrorFFC() {
            return mirrorFFC.isChecked();
        }
    }

}