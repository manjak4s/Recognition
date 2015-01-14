package com.mayer.recognition.fragment.history;

import android.app.Fragment;
import android.app.ListFragment;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.mayer.recognition.R;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

/**
 * Created by dot on 19.11.2014.
 */
@EFragment(R.layout.fragment_order_list)
public class HistoryOrderItemListFragment extends ListFragment {

    @ViewById
    protected ProgressBar loadingSpinner;

    @ViewById
    protected RelativeLayout bodyContent;


}
