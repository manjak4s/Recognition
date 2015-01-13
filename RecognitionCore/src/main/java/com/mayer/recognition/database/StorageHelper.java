package com.mayer.recognition.database;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import com.mayer.recognition.util.Logger;
import com.mayer.sql.update.SqlUpdateVersionMatcher;
import com.mayer.sql.update.version.IUpdateContainer;

/**
 * Created by irikhmayer on 13.01.2015.
 */
public class StorageHelper extends SQLiteOpenHelper {

    private static String getDbName() {
        return StorageSchema.DB_NAME;
    }

    private static int getDbVersion() {
        return StorageSchema.DB_VERSION;
    }

    private final Context mContext;

    public StorageHelper(Context context) {
        super(context, getDbName(), null, getDbVersion());
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        StorageSchema.onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onDrop(db);
        this.onCreate(db);
    }

    public void onDrop(SQLiteDatabase db) {
        StorageSchema.onDrop(db);
    }

    public synchronized boolean recompileDatabase() {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            onDrop(db);
            onCreate(db);
            db.setTransactionSuccessful();
            return true;
        } catch (Exception e) {
            Logger.e(this.getClass().getSimpleName() + "recompileDatabase failed", e);
        } finally {
            db.endTransaction();
        }
        return false;
    }
}