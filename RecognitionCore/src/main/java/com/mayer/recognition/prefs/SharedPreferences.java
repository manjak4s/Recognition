package com.mayer.recognition.prefs;

import org.androidannotations.annotations.sharedpreferences.DefaultInt;
import org.androidannotations.annotations.sharedpreferences.DefaultString;
import org.androidannotations.annotations.sharedpreferences.SharedPref;

/**
 * Created by dot on 19.11.2014.
 */
@SharedPref
public interface SharedPreferences {

    // The field name will have default value "John"
    @DefaultString("John")
    String name();

    // The field age will have default value 42
    @DefaultInt(42)
    int age();

    // The field lastUpdated will have default value 0
    long lastUpdated();

}