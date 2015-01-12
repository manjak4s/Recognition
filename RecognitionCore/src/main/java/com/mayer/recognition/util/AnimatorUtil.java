package com.mayer.recognition.util;

import android.view.View;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

/**
 * Created by dot on 13.01.2015.
 */
public class AnimatorUtil {

    public static final void animate(View view) {
        YoYo.with(Techniques.Tada).duration(700).playOn(view);
    }
}
