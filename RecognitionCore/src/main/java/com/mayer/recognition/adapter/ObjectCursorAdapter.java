package com.mayer.recognition.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import org.parceler.apache.commons.collections.ListUtils;

import java.util.List;

/**
 * Created by irikhmayer on 14.01.2015.
 */
public abstract class ObjectCursorAdapter<Type> extends BaseAdapter implements CursorAdapterInterface<Type> {

    private List<Type> list;
    private Context mContext;

    public ObjectCursorAdapter(Context context) {
        this.mContext = context;
    }

    public Context getmContext() {
        return mContext;
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Type getItem(int position) {
        assert list != null;
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public synchronized View getView(int position, View convertView, ViewGroup parent) {
        return bindView(convertView == null ? newView(position, parent) : convertView, position, getItem(position));
    }

    protected abstract View newView(int position, ViewGroup parent);

    protected abstract View bindView(View convertView, int position, Type item);

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return bindView(convertView == null ? newDropDownView(position, parent) : convertView, position, getItem(position));
    }

    protected View newDropDownView(int position, ViewGroup parent) {
        return newView(position, parent);
    }

    public synchronized void changeCursor(List<Type> list) {
        this.list = list;
        notifyDataSetChanged();
    }
}
