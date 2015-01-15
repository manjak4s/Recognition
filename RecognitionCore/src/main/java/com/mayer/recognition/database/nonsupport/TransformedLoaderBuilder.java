package com.mayer.recognition.database.nonsupport;

/**
 * Created by irikhmayer on 15.01.2015.
 */
import com.getbase.android.db.common.QueryData;
import com.getbase.android.db.loaders.WrappedLoaderBuilder;
import com.google.common.base.Function;
import com.google.common.base.Functions;

import android.content.Context;
import android.content.Loader;
import android.database.Cursor;

import java.util.List;

public class TransformedLoaderBuilder<T> {

    private final QueryData queryData;
    private final Function<Cursor, T> cursorTransformation;

    public TransformedLoaderBuilder(QueryData queryData, Function<Cursor, T> transformation) {
        this.queryData = queryData;
        this.cursorTransformation = transformation;
    }

    public <Out> TransformedLoaderBuilder<Out> transform(final Function<T, Out> function) {
        return new TransformedLoaderBuilder<Out>(queryData, Functions.compose(function, cursorTransformation));
    }

    public <Out> WrappedLoaderBuilder<Out> wrap(final Function<List<T>, Out> wrapper) {
        return new WrappedLoaderBuilder<Out>(queryData, Functions.compose(wrapper, getTransformationFunction()));
    }

    public Loader<List<T>> build(Context context) {
        return new ComposedCursorLoader<>(context, queryData, getTransformationFunction());
    }

    private Function<Cursor, List<T>> getTransformationFunction() {
        return new Function<Cursor, List<T>>() {
            @Override
            public List<T> apply(Cursor cursor) {
                return new LazyCursorList<T>(cursor, cursorTransformation);
            }
        };
    }
}