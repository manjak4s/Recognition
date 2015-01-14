package com.mayer.recognition.fragment.history;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.mayer.recognition.adapter.ObjectCursorAdapter;
import com.mayer.recognition.model.dao.order.OrderModel;
import com.mayer.recognition.util.ContentValueUtil;

/**
 * Created by irikhmayer on 14.01.2015.
 */
public class HistoryOrderListAdapter extends ObjectCursorAdapter<OrderModel> {

    public HistoryOrderListAdapter(Context context) {
        super(context);
    }

    @Override
    protected View newView(int position, ViewGroup parent) {
        return HistoryOrderItemView_.build(getContext());
    }

    @Override
    protected View bindView(View convertView, int position, final OrderModel item) {
        HistoryOrderItemView itemView = (HistoryOrderItemView_) convertView;
        itemView.bind(null, "Recognized receipt", ContentValueUtil.date(item.getDate()));
        return convertView;
    }
}