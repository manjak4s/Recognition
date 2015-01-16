package com.getbase.android.db.loaders;

/**
 * Created by irikhmayer on 15.01.2015.
 */
import com.getbase.android.db.common.QueryData;
import com.google.common.base.Function;
import com.google.common.base.Functions;

import android.content.Context;
import android.content.Loader;
import android.database.Cursor;

import java.util.List;

public class TransformedLoaderBuilderPrime<T> {

    private final QueryData queryData;
    private final Function<Cursor, T> cursorTransformation;

    public TransformedLoaderBuilderPrime(QueryData queryData, Function<Cursor, T> transformation) {
        this.queryData = queryData;
        this.cursorTransformation = transformation;
    }

    public <Out> TransformedLoaderBuilderPrime<Out> transform(final Function<T, Out> function) {
        return new TransformedLoaderBuilderPrime<Out>(queryData, Functions.compose(function, cursorTransformation));
    }

    public <Out> WrappedLoaderBuilder<Out> wrap(final Function<List<T>, Out> wrapper) {
        return new WrappedLoaderBuilder<Out>(queryData, Functions.compose(wrapper, getTransformationFunction()));
    }

    public Loader<List<T>> build(Context context) {
        return new ComposedCursorLoaderPrime<>(context, queryData, getTransformationFunction());
    }

    private Function<Cursor, List<T>> getTransformationFunction() {
        return new Function<Cursor, List<T>>() {
            @Override
            public List<T> apply(Cursor cursor) {
                return new LazyCursorListPrime<T>(cursor, cursorTransformation);
            }
        };
    }
}