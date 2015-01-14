package com.mayer.recognition.fragment.history;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.mayer.recognition.adapter.ObjectCursorAdapter;
import com.mayer.recognition.model.dao.order.OrderModel;

/**
 * Created by irikhmayer on 14.01.2015.
 */
public class HistoryOrderListAdapter extends ObjectCursorAdapter<OrderModel> {

    public HistoryOrderListAdapter(Context context) {
        super(context);
    }

    @Override
    protected View newView(int position, ViewGroup parent) {
        return null;//UnitItemView_.build(getContext());
    }

    @Override
    protected View bindView(View convertView, int position, final OrderModel item) {
        //        br.com.bematech.bemacash.fragment.wireless.UnitItemView itemView = (UnitItemView_) convertView;
//        itemView.bind(item.serialCode, String.valueOf(item.warrantyPeriod), item.status.toString());
        return convertView;
    }
}