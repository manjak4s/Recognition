package com.mayer.recognition.database.function;

import android.database.Cursor;

import com.google.common.base.Function;
import com.mayer.recognition.model.dao.order.OrderModel;

/**
 * Created by irikhmayer on 14.01.2015.
 */
public class OrderItemModelFunction implements Function<Cursor, OrderModel> {

    @Override
    public OrderModel apply(Cursor c) {
        return new OrderModel(c);
    }
}