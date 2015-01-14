package com.mayer.recognition.model.dao.order;

import android.content.ContentValues;
import android.database.Cursor;

import com.mayer.recognition.database.Storage;
import com.mayer.recognition.database.Storage.RecognitionOrderTable;
import com.mayer.recognition.model.dao.IValueModel;

import java.io.Serializable;
import java.util.Date;

import static com.mayer.recognition.util.ContentValueUtil.date;
import static com.mayer.recognition.util.ContentValueUtil.decimal;
import static com.mayer.recognition.util.ContentValueUtil.discountType;
import static com.mayer.recognition.util.ContentValueUtil.nullableDate;

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

    public OrderModel(Date date,
                      boolean favourite,
                      String url,
                      double longitudeTaken,
                      double latitudeTaken) {
        this.date = date;
        this.favourite = favourite;
        this.url = url;
        this.longitudeTaken = longitudeTaken;
        this.latitudeTaken = latitudeTaken;
    }

    public OrderModel(long id,
                      Date date,
                      boolean favourite,
                      String url,
                      double longitudeTaken,
                      double latitudeTaken,
                      String plainBody,
                      OrderMathModel model) {
        this.id = id;
        this.date = date;
        this.favourite = favourite;
        this.url = url;
        this.longitudeTaken = longitudeTaken;
        this.latitudeTaken = latitudeTaken;
        this.plainBody = plainBody;
        this.model = model;
    }

    public OrderModel(Cursor c) {
        this(c.getLong(c.getColumnIndex(RecognitionOrderTable.ID)),
             nullableDate(c, c.getColumnIndex(RecognitionOrderTable.CREATE_TIME)),
             c.getInt(c.getColumnIndex(RecognitionOrderTable.FAVOURITE)) == 1,
             c.getString(c.getColumnIndex(RecognitionOrderTable.URL)),
             c.getDouble(c.getColumnIndex(RecognitionOrderTable.LONGITUDE_TAKEN)),
             c.getDouble(c.getColumnIndex(RecognitionOrderTable.LATITUDE_TAKEN)),
             c.getString(c.getColumnIndex(RecognitionOrderTable.PLAIN_BODY)),
             new OrderMathModel(decimal(c, c.getColumnIndex(RecognitionOrderTable.DISCOUNT_VALUE)),
                                decimal(c, c.getColumnIndex(RecognitionOrderTable.TIPS_VALUE)),
                                decimal(c, c.getColumnIndex(RecognitionOrderTable.TAX_VALUE)),
                                discountType(c, c.getColumnIndex(RecognitionOrderTable.DISCOUNT_TYPE))
             )
        );
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
