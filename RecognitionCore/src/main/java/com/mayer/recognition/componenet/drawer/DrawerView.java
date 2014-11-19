package com.mayer.recognition.componenet.drawer;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mayer.recognition.R;
import com.mayer.recognition.util.Logger;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * Created by irikhmayer on 17.11.2014.
 */
@EViewGroup(R.layout.drawer_item_view)
public class DrawerView extends LinearLayout {

    @ViewById
    public TextView name;

    public DrawerView(Context context) {
        super(context);
    }

    public DrawerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DrawerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @AfterViews
    protected void init() {
        Logger.d("itemview init");
    }

    public void bind(String title) {
        name.setText(title);
    }
}
