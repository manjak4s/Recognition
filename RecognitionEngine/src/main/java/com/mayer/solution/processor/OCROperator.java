package com.mayer.solution.processor;

/**
 * Created by dot on 09.11.2014.
 */
public class OCROperator implements SnapshotOperator {


    private static final String OTG_LIB_NAME = "MediatorOTG";

    static {
        System.loadLibrary(OTG_LIB_NAME);
    }


    @Override public String getTestString() {
        return getContent();
    }

    public native String getContent();

}
