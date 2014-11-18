package com.mayer.recognition.util;

import com.mayer.recognition.RecognitionApplication;

/**
 * Created by dot on 19.11.2014.
 */
public class ResourceHelper {

    public static String getString(int resId) {
        return RecognitionApplication.get().getString(resId);
    }

}
