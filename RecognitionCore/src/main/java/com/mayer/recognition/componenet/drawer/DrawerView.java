package com.mayer.recognition.componenet.drawer;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mayer.recognition.R;
import com.mayer.recognition.model.ui.TabViewModel;
import com.mayer.recognition.util.Logger;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.ColorRes;

/**
 * Created by irikhmayer on 17.11.2014.
 */
@EViewGroup(R.layout.drawer_item_view)
public class DrawerView extends LinearLayout {

    @ColorRes(R.color.teal_700)
    protected int nonPressed;

    @ColorRes(R.color.color_accent)
    protected int pressed;

    @ViewById
    public TextView name;

    @ViewById(R.id.drawer_item_container_id)
    public LinearLayout container;

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
    protected void init() { }

    public void bind(TabViewModel model, boolean isChecked)
    {
        if (isChecked) {
            container.getBackground().setLevel(1);
            name.setTextColor(pressed);
        } else {
            container.getBackground().setLevel(0);
            name.setTextColor(nonPressed);
        }
        name.setText(model.getTitle());
    }
}