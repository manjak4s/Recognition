package com.mayer.recognition.componenet.camera;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;

import com.mayer.recognition.R;

import org.androidannotations.annotations.EView;

/**
 * Created by dot on 23.11.2014.
 */
@EView
public class FlashButton extends ImageButton implements View.OnClickListener {

    @Override
    public void onClick(View v) {
        int next = ((mState.ordinal() + 1) % FlashEnum.values().length);
        setState(FlashEnum.values()[next]);
        performFlashClick();
    }

    public enum FlashEnum {
        AUTOMATIC, ON, OFF
    }

    public interface FlashListener {
        void onAutomatic();
        void onOn();
        void onOff();
    }

    private FlashEnum mState;
    private FlashListener mFlashListener;

    public FlashButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnClickListener(this);
        setState(FlashEnum.AUTOMATIC);
    }

    private void performFlashClick() {
        if(mFlashListener == null)return;
        switch (mState) {
            case AUTOMATIC:
                mFlashListener.onAutomatic();
                break;
            case ON:
                mFlashListener.onOn();
                break;
            case OFF:
                mFlashListener.onOff();
                break;
        }
    }

    private void createDrawableState() {
        switch (mState) {
            case AUTOMATIC:
                setImageResource(R.drawable.ic_flash_on);
                break;
            case ON:
                setImageResource(R.drawable.ic_flash);
                break;
            case OFF:
                setImageResource(R.drawable.ic_flash_disabled);
                break;
        }
    }


    public FlashEnum getState() {
        return mState;
    }

    public void setState(FlashEnum state) {
        if(state == null)return;
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