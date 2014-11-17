package com.mayer.recognition.model.ui;

import android.app.Fragment;

/**
 * Created by irikhmayer on 17.11.2014.
 */
public abstract class TabViewModel {

    public abstract Fragment getFragmentInstance();

    public abstract String getTitle();
}
