package com.mayer.recognition.model.dao;

import android.content.ContentValues;

/**
 * Created by dot on 14.01.2015.
 */
public interface IValueModel {

    long getGuid();
    ContentValues toValues();
}
