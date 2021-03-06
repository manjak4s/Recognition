package com.getbase.android.db.loaders;

import com.getbase.android.db.cursors.Cursors;
import com.google.common.base.Function;

import android.database.Cursor;

import java.util.AbstractList;

class LazyCursorListPrime<T> extends AbstractList<T> {

    private final Cursor cursor;
    private final Function<Cursor, T> transformation;

    public LazyCursorListPrime(Cursor cursor, Function<Cursor, T> function) {
        this.cursor = Cursors.returnSameOrEmptyIfNull(cursor);
        this.transformation = function;
    }

    @Override
    public T get(int i) {
        // TODO - caching.
        cursor.moveToPosition(i);
        return transformation.apply(cursor);
    }

    @Override
    public int size() {
        return cursor.getCount();
    }
}