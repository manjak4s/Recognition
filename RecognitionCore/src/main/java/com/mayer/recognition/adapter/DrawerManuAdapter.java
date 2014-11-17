package com.mayer.recognition.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mayer.recognition.R;
import com.mayer.recognition.componenet.DrawerView;
import com.mayer.recognition.componenet.DrawerView_;
import com.mayer.recognition.model.ui.TabViewModel;

import org.androidannotations.annotations.EBean;

/**
 * Created by irikhmayer on 17.11.2014.
 */
public class DrawerManuAdapter extends MenuAdapter<TabViewModel, DrawerView> {

    public DrawerManuAdapter(Context ctx) {
        super(ctx);
    }

    @Override
    protected DrawerView newView(Context ctx, ViewGroup viewGroup, int pos) {
        return DrawerView_.build(ctx);
    }

    @Override
    protected void bind(TabViewModel value, DrawerView view) {
        view.name.setText(value.getTitle());
    }
}