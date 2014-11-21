package com.mayer.recognition.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.mayer.recognition.R;
import com.mayer.recognition.componenet.drawer.DrawerView;
import com.mayer.recognition.componenet.drawer.DrawerView_;
import com.mayer.recognition.model.ui.TabViewModel;
import com.mayer.recognition.util.Logger;

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
    protected void bind(TabViewModel value, DrawerView view, boolean isChecked) {
        view.container.getBackground().setLevel(isChecked ? 1 : 0);
        view.name.setText(value.getTitle());
    }
}