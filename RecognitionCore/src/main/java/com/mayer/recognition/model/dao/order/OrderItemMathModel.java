package com.mayer.recognition.model.dao.order;

import android.content.ContentValues;

import com.mayer.recognition.database.Storage;
import com.mayer.recognition.model.dao.IValueModel;

import java.io.Serializable;
import java.math.BigDecimal;

import static com.mayer.recognition.util.ContentValueUtil.date;
import static com.mayer.recognition.util.ContentValueUtil.decimal;
import static com.mayer.recognition.util.ContentValueUtil.decimalQty;

/**
 * Created by dot on 14.01.2015.
 */
public class OrderItemMathModel implements Serializable, IValueModel {

    protected long id;

    protected String name;

    protected String url;

    protected BigDecimal qty;

    protected BigDecimal price;

    protected BigDecimal discount;

    protected DiscountType discountType;

    protected boolean selected;

    public OrderItemMathModel(String name, BigDecimal qty, BigDecimal price, BigDecimal discount, DiscountType discountType) {
        this.name = name;
        this.qty = qty;
        this.price = price;
        this.discount = discount;
        this.discountType = discountType;
    }

    public OrderItemMathModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getQty() {
        return qty;
    }

    public void setQty(BigDecimal qty) {
        this.qty = qty;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public DiscountType getDiscountType() {
        return discountType;
    }

    public void setDiscountType(DiscountType discountType) {
        this.discountType = discountType;
    }

    @Override
    public long getGuid() {
        return id;
    }

    @Override
    public ContentValues toValues() {
        ContentValues v = new ContentValues();
        v.put(Storage.RecognitionSubitemTable.SELECTER, selected);
        v.put(Storage.RecognitionSubitemTable.URL, url);
        v.put(Storage.RecognitionSubitemTable.DISCOUNT_TYPE, discountType.ordinal());
        v.put(Storage.RecognitionSubitemTable.DISCOUNT_VALUE, decimal(discount));
        v.put(Storage.RecognitionSubitemTable.PRICE, decimal(price));
        v.put(Storage.RecognitionSubitemTable.QUANTITY, decimalQty(qty));
        return v;
    }
}
