package com.mayer.recognition.model.pojo;

import java.util.Date;

/**
 * Created by irikhmayer on 16.01.2015.
 */
public class CameraPictureResult {

    protected byte[] content;
    protected double latitude;
    protected double longitude;
    protected Date date;
    protected String description;

    public CameraPictureResult(byte[] content, double longitude, double latitude, Date date, String description) {
        this.content = content;
        this.latitude = latitude;
        this.longitude = longitude;
        this.date = date;
        this.description = description;
    }

    public CameraPictureResult() { }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
