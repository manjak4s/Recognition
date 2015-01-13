package com.mayer.recognition.model.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by irikhmayer on 13.01.2015.
 */
public class ItemModel implements Serializable {

    protected List<RecognizedLineModel> items;

    protected BigDecimal discount;

    protected BigDecimal tips;

    protected BigDecimal tax;

    protected DiscountType discountType;

    public ItemModel(List<RecognizedLineModel> items, BigDecimal discount, BigDecimal tips, BigDecimal tax, DiscountType discountType) {
        this.items = items;
        this.discount = discount;
        this.tips = tips;
        this.tax = tax;
        this.discountType = discountType;
    }

    public ItemModel() {
    }

    public List<RecognizedLineModel> getItems() {
        return items;
    }

    public void setItems(List<RecognizedLineModel> items) {
        this.items = items;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public BigDecimal getTips() {
        return tips;
    }

    public void setTips(BigDecimal tips) {
        this.tips = tips;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public DiscountType getDiscountType() {
        return discountType;
    }

    public void setDiscountType(DiscountType discountType) {
        this.discountType = discountType;
    }

    public class RecognizedLineModel {

        protected String name;

        protected BigDecimal qty;

        protected BigDecimal price;

        protected BigDecimal discount;

        protected DiscountType discountType;

        public RecognizedLineModel(String name, BigDecimal qty, BigDecimal price, BigDecimal discount, DiscountType discountType) {
            this.name = name;
            this.qty = qty;
            this.price = price;
            this.discount = discount;
            this.discountType = discountType;
        }

        public RecognizedLineModel() {
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
    }
}
