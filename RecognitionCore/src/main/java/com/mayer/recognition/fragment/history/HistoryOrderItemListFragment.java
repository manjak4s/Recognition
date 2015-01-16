package com.mayer.recognition.fragment.history;

import android.app.ListFragment;
import android.net.Uri;
import android.os.Bundle;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Loader;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.getbase.android.db.loaders.CursorLoaderBuilderPrime;
import com.mayer.recognition.R;
import com.mayer.recognition.database.Storage;
import com.mayer.recognition.database.StorageProvider;
import com.mayer.recognition.database.function.OrderItemModelFunction;
import com.mayer.recognition.model.dao.order.OrderModel;


import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.List;

/**
 * Created by dot on 19.11.2014.
 */
@EFragment(R.layout.fragment_order_list)
public class HistoryOrderItemListFragment extends ListFragment implements LoaderCallbacks<List<OrderModel>> {

    protected static final Uri URI_ORDERS = StorageProvider.contentUri(Storage.RecognitionOrderTable.URI_CONTENT);

    @ViewById
    protected ProgressBar loadingSpinner;

    @ViewById
    protected RelativeLayout bodyContent;

    protected HistoryOrderListAdapter itemAdapter;

    @AfterViews
    protected void init() {
        setListAdapter(itemAdapter = new HistoryOrderListAdapter(getActivity()));
        getLoaderManager().restartLoader(0, null, this);
    }

    @Override
    public Loader<List<OrderModel>> onCreateLoader(int id, Bundle args) {
        CursorLoaderBuilderPrime loader = CursorLoaderBuilderPrime.forUri(URI_ORDERS);
//        loader.where(Storage.RecognitionOrderTable.ITEM_ID + " = ?", model.guid);
//        if (status != null) {
//            loader.where(ShopStore.UnitTable.STATUS + " = ?", status.ordinal());
//        }
        return loader.orderBy(Storage.RecognitionOrderTable.CREATE_TIME + " desc ").transform(new OrderItemModelFunction()).build(getActivity());
    }

    @Override
    public void onLoadFinished(Loader<List<OrderModel>> loader, List<OrderModel> data) {
        itemAdapter.changeCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<List<OrderModel>> loader) {
        if (getActivity() != null) {
            itemAdapter.changeCursor(null);
        }
    }
}
