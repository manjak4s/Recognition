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

    public static String PREF_NAME = "SharedPreferences";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Using your MyPrefs values
        this.getPreferenceManager().setSharedPreferencesName(PREF_NAME);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);
    }
}