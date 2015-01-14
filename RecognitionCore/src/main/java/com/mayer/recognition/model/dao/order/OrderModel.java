package com.mayer.recognition.model.dao.order;

import android.content.ContentValues;

import com.mayer.recognition.database.Storage;
import com.mayer.recognition.model.dao.IValueModel;

import java.io.Serializable;
import java.util.Date;

import static com.mayer.recognition.util.ContentValueUtil.date;
import static com.mayer.recognition.util.ContentValueUtil.decimal;

/**
 * Created by dot on 13.01.2015.
 */
public class OrderModel implements Serializable, IValueModel {

    protected long id;

    protected Date date;

    protected boolean favourite;

    protected String url;

    protected double longitudeTaken;

    protected double latitudeTaken;

    protected String plainBody;

    protected OrderMathModel model;

    protected DiscountType discountType;

    public OrderModel(Date date, boolean favourite, String url, double longitudeTaken, double latitudeTaken) {
        this.date = date;
        this.favourite = favourite;
        this.url = url;
        this.longitudeTaken = longitudeTaken;
        this.latitudeTaken = latitudeTaken;
    }

    public OrderModel() {
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

    @Override
    public long getGuid() {
        return id;
    }

    @Override
    public ContentValues toValues() {
        ContentValues v = new ContentValues();
        v.put(Storage.RecognitionOrderTable.CREATE_TIME, date(date));
        v.put(Storage.RecognitionOrderTable.FAVOURITE, favourite);
        v.put(Storage.RecognitionOrderTable.URL, url);
        v.put(Storage.RecognitionOrderTable.LONGITUDE_TAKEN, longitudeTaken);
        v.put(Storage.RecognitionOrderTable.LATITUDE_TAKEN, latitudeTaken);
        v.put(Storage.RecognitionOrderTable.DISCOUNT_TYPE, model.discountType.ordinal());
        v.put(Storage.RecognitionOrderTable.DISCOUNT_VALUE, decimal(model.discount));
        v.put(Storage.RecognitionOrderTable.TIPS_VALUE, decimal(model.tips));
        return v;
    }
}
