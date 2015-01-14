package com.mayer.recognition.model.dao.order;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by irikhmayer on 13.01.2015.
 */
public class OrderMathModel implements Serializable {

    protected List<OrderItemMathModel> items;

    protected BigDecimal discount;

    protected BigDecimal tips;

    protected BigDecimal tax;

    protected DiscountType discountType;

    public OrderMathModel(List<OrderItemMathModel> items, BigDecimal discount, BigDecimal tips, BigDecimal tax, DiscountType discountType) {
        this.items = items;
        this.discount = discount;
        this.tips = tips;
        this.tax = tax;
        this.discountType = discountType;
    }

    public OrderMathModel() {
    }

    public List<OrderItemMathModel> getItems() {
        return items;
    }

    public void setItems(List<OrderItemMathModel> items) {
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


}
