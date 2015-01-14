package com.mayer.recognition.fragment.history;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.CardView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.mayer.recognition.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * Created by irikhmayer on 14.01.2015.
 */
@EViewGroup(R.layout.view_order_list_item)
public class HistoryOrderItemView extends CardView {

    @ViewById
    protected ImageView imageView;

    @ViewById
    protected TextView descriptionLabel;

    @ViewById
    protected TextView dateLabel;

    public HistoryOrderItemView(Context context) {
        super(context);
    }

    @AfterViews
    protected void init() {
    }

    public void bind(Bitmap image,
                     String descriptionLabel,
                     String dateLabel) {
        this.imageView.setImageBitmap(image);
        this.descriptionLabel.setText(descriptionLabel);
        this.dateLabel.setText(dateLabel);
    }
}