package com.mayer.recognition.model.dao;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by dot on 13.01.2015.
 */
public class RecognitionItemModel implements Serializable {

    protected long id;

    protected Date date;

    protected boolean favourite;

    protected String url;

    protected double longitudeTaken;

    protected double latitudeTaken;

    protected String plainBody;

    protected RecognizedItemModel model;

    protected DiscountType discountType;

    public RecognitionItemModel(long id, Date date, boolean favourite, String url, double longitudeTaken, double latitudeTaken) {
        this.id = id;
        this.date = date;
        this.favourite = favourite;
        this.url = url;
        this.longitudeTaken = longitudeTaken;
        this.latitudeTaken = latitudeTaken;
    }

    public RecognitionItemModel() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isFavourite() {
        return favourite;
    }

    public void setFavourite(boolean favourite) {
        this.favourite = favourite;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public double getLongitudeTaken() {
        return longitudeTaken;
    }

    public void setLongitudeTaken(double longitudeTaken) {
        this.longitudeTaken = longitudeTaken;
    }

    public double getLatitudeTaken() {
        return latitudeTaken;
    }

    public void setLatitudeTaken(double latitudeTaken) {
        this.latitudeTaken = latitudeTaken;
    }
}
