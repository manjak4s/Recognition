package com.mayer.recognition.fragment.settings;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.mayer.recognition.R;

import org.androidannotations.annotations.EFragment;

/**
 * Created by dot on 13.11.2014.
 */
@EFragment
public class SettingsFrament extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);
    }
}