package com.mayer.recognition.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by irikhmayer on 13.01.2015.
 */
class ProviderHelper {

    private Context context;
    private SQLiteOpenHelper dbHelper;

    ProviderHelper(Context context, SQLiteOpenHelper dbHelper) {
        this.context = context;
        this.dbHelper = dbHelper;
    }

    public Context getContext() {
        return context;
    }

    protected void bulkUpdate(String tableName, ArrayList<ContentValues> values, String idName) {
        bulkUpdate(tableName, values, idName, null, null);
    }

    protected void bulkUpdate(String tableName, ArrayList<ContentValues> values, String idName, String where, String[] args) {
        if (values == null || values.isEmpty()) {
            return;
        }
        SQLiteDatabase sql = dbHelper.getWritableDatabase();
        sql.beginTransaction();
        try {
            for (ContentValues v : values) {
                String id = v.getAsString(idName);
                v.remove(idName);
                String select;
                String[] selectArgs;
                if (where == null) {
                    select = idName + " = ?";
                    selectArgs = new String[]{id};
                } else {
                    select = idName + " = ?" + " and " + where;
                    selectArgs = new String[args.length + 1];
                    selectArgs[0] = id;
                    System.arraycopy(args, 0, selectArgs, 1, args.length);
                }
                sql.update(tableName, v, select, selectArgs);
            }
            sql.setTransactionSuccessful();
        } finally {
            sql.endTransaction();
        }
    }
}