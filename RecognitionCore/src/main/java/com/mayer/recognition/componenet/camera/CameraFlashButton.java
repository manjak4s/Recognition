package com.mayer.recognition.componenet.camera;

import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;

import com.mayer.recognition.R;
import com.mayer.recognition.util.AnimatorUtil;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by dot on 23.11.2014.
 */
@EView
public class CameraFlashButton extends ImageButton implements View.OnClickListener {

    protected List<String>  states = new ArrayList<String>();
    protected static final List<String> statesSupported = new ArrayList<String>();

    static {
        statesSupported.add(Parameters.FLASH_MODE_AUTO);
        statesSupported.add(Parameters.FLASH_MODE_TORCH);
        statesSupported.add(Parameters.FLASH_MODE_OFF);
    }

    private String mState;
    private FlashListener mFlashListener;

    @Override
    public void onClick(View v) {
        if (states == null || states.size() == 0) {
            return;
        }
        AnimatorUtil.animate(v);
        int statePosition = ((states.indexOf(mState) + 1) % states.size());
        setState(states.get(statePosition));
        performFlashClick();
    }

    public interface FlashListener {
        void onAutomatic();

        void onOn();

        void onOff();
    }



    public CameraFlashButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnClickListener(this);
        setStates(statesSupported);
        setState(Camera.Parameters.FLASH_MODE_AUTO);
    }

    private void performFlashClick() {
        if (mFlashListener == null) {
            return;
        }
        if (Camera.Parameters.FLASH_MODE_AUTO.equals(mState)) {
            mFlashListener.onAutomatic();
        } else if (Camera.Parameters.FLASH_MODE_TORCH.equals(mState)) {
            mFlashListener.onOn();
        } else if (Camera.Parameters.FLASH_MODE_OFF.equals(mState)) {
            mFlashListener.onOff();
        }
    }

    private void createDrawableState() {
        if (Camera.Parameters.FLASH_MODE_AUTO.equals(mState)) {
            setImageResource(R.drawable.ic_flash_on);
        } else if (Camera.Parameters.FLASH_MODE_TORCH.equals(mState)) {
            setImageResource(R.drawable.ic_flash);
        } else if (Camera.Parameters.FLASH_MODE_OFF.equals(mState)) {
            setImageResource(R.drawable.ic_flash_disabled);
        }
    }


    public void setStates(List<String> states) {
        this.states.clear();
        if (states == null || states.size() == 0) {
            setVisibility(GONE);
            return;
        } else {
            setVisibility(VISIBLE);
        }
        for (String mode : states) {
            if (statesSupported.contains(mode)) {
                this.states.add(mode);
            }
        }
    }

    public void setState(String state) {
        if (state == null) {
            setVisibility(GONE);
            return;
        }
        this.mState = state;
        createDrawableState();

    }

    public FlashListener getFlashListener() {
        return mFlashListener;
    }

    public void setFlashListener(FlashListener flashListener) {
        this.mFlashListener = flashListener;
    }
}