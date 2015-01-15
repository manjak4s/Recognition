package com.mayer.recognition.database.nonsupport;

import com.getbase.android.db.cursors.Cursors;
import com.google.common.base.Function;

import android.database.Cursor;

import java.util.AbstractList;

class LazyCursorList<T> extends AbstractList<T> {

    private final Cursor cursor;
    private final Function<Cursor, T> transformation;

    public LazyCursorList(Cursor cursor, Function<Cursor, T> function) {
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