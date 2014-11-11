package com.mayer.recognition.activity;

import android.support.v4.app.FragmentActivity;

import org.androidannotations.annotations.App;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Fullscreen;

import com.mayer.recognition.RecognitionApplication;

/**
 * Created by irikhmayer on 06.11.2014.
 */
@EActivity
@Fullscreen
public abstract class BasicActivity  extends FragmentActivity {

    @App
    protected RecognitionApplication app;

}
