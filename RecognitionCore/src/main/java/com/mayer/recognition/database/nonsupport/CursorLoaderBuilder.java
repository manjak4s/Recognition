package com.mayer.recognition.database.nonsupport;

/**
 * Created by irikhmayer on 15.01.2015.
 */

import com.getbase.android.db.common.QueryData;
import com.getbase.android.db.loaders.WrappedLoaderBuilder;
import com.getbase.android.db.provider.ProviderAction;
import com.getbase.android.db.provider.Query;
import com.google.common.base.Function;

import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;

public class CursorLoaderBuilder {

    public static CursorLoaderBuilder forUri(Uri uri) {
        return new CursorLoaderBuilder(uri);
    }

    public final Query query;

    private CursorLoaderBuilder(Uri uri) {
        this.query = ProviderAction.query(uri);
    }

    public CursorLoaderBuilder projection(String... projection) {
        query.projection(projection);
        return this;
    }

    public CursorLoaderBuilder where(String selection, Object... selectionArgs) {
        query.where(selection, selectionArgs);
        return this;
    }

    public CursorLoaderBuilder orderBy(String orderBy) {
        query.orderBy(orderBy);
        return this;
    }

    public <Out> TransformedLoaderBuilder<Out> transform(Function<Cursor, Out> function) {
        return new TransformedLoaderBuilder<Out>(query.getQueryData(), function);
    }

    public <Out> WrappedLoaderBuilder<Out> wrap(Function<Cursor, Out> wrapper) {
        return new WrappedLoaderBuilder<Out>(query.getQueryData(), wrapper);
    }

    public Loader<Cursor> build(Context context) {
        final QueryData queryData = query.getQueryData();
        final CursorLoader loader = new CursorLoader(context);
        loader.setUri(queryData.getUri());
        loader.setProjection(queryData.getProjection());
        loader.setSelection(queryData.getSelection());
        loader.setSelectionArgs(queryData.getSelectionArgs());
        loader.setSortOrder(queryData.getOrderBy());
        return loader;
    }
}
