package com.getbase.android.db.loaders;

/**
 * Created by irikhmayer on 15.01.2015.
 */

import com.getbase.android.db.common.QueryData;
import com.getbase.android.db.provider.ProviderAction;
import com.getbase.android.db.provider.Query;
import com.google.common.base.Function;

import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;

public class CursorLoaderBuilderPrime {

    public static CursorLoaderBuilderPrime forUri(Uri uri) {
        return new CursorLoaderBuilderPrime(uri);
    }

    public final Query query;

    private CursorLoaderBuilderPrime(Uri uri) {
        this.query = ProviderAction.query(uri);
    }

    public CursorLoaderBuilderPrime projection(String... projection) {
        query.projection(projection);
        return this;
    }

    public CursorLoaderBuilderPrime where(String selection, Object... selectionArgs) {
        query.where(selection, selectionArgs);
        return this;
    }

    public CursorLoaderBuilderPrime orderBy(String orderBy) {
        query.orderBy(orderBy);
        return this;
    }

    public <Out> TransformedLoaderBuilderPrime<Out> transform(Function<Cursor, Out> function) {
        return new TransformedLoaderBuilderPrime<Out>(query.getQueryData(), function);
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
