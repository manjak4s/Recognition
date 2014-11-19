package com.mayer.recognition.componenet.drawer;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.mayer.recognition.util.Logger;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EView;

/**
 * Created by dot on 19.11.2014.
 */
@EView
public class SmartDrawer extends RecyclerView {

    public SmartDrawer(Context context) {
        super(context);
    }

    public SmartDrawer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SmartDrawer(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @AfterViews
    protected void init() {
        setLayoutManager(new LinearLayoutManager(getContext()));
        setItemAnimator(new DefaultItemAnimator());
        setHasFixedSize(true);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST);
        addItemDecoration(itemDecoration);
    }

    public void navigateTo(int position) {
        Logger.d("navigated to " + position);
        getChildAt(position).requestFocus();
    }

}
