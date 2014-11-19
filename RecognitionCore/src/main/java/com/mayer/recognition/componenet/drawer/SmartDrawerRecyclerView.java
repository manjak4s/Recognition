package com.mayer.recognition.componenet.drawer;

import android.content.Context;
import android.os.Parcelable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.mayer.recognition.util.Logger;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EView;
import org.androidannotations.annotations.UiThread;

/**
 * Created by dot on 19.11.2014.
 */
@EView
public class SmartDrawerRecyclerView extends RecyclerView {

    protected int lastSelection;

    public SmartDrawerRecyclerView(Context context) {
        super(context);
    }

    public SmartDrawerRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SmartDrawerRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @AfterViews
    protected void init() {
        setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        setItemAnimator(new DefaultItemAnimator());
        setHasFixedSize(true);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST);
        addItemDecoration(itemDecoration);
    }

    public void navigateTo(int position) {
        lastSelection = position;
        View view = getChildAt(position);
        Logger.d("hello position " + position);
        if (view != null && !view.isFocused()) {
            Logger.d("hello position done");
            view.requestFocus();
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        Logger.d("onWindowFocusChanged");
        navigateTo(lastSelection);
    }
}
