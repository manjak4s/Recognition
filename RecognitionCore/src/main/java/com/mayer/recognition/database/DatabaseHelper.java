package com.mayer.recognition.database;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import com.mayer.sql.update.SqlUpdateVersionMatcher;
import com.mayer.sql.update.version.IUpdateContainer;

/**
 * Created by irikhmayer on 13.01.2015.
 */
public class DatabaseHelper  extends SQLiteOpenHelper {

    private static final String PRAGMA_ENABLE_FOREIGN_KEYS = "PRAGMA foreign_keys = ON;";

    protected static String getDbName() {
        return StorageSchema.DB_NAME;
    }

    protected static int getDbVersion() {
        return StorageSchema.DB_VERSION;
    }

    protected final Context mContext;

    public DatabaseHelper(Context context) {
        super(context, getDbName(), null, getDbVersion());
        mContext = context;
    }

    protected DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        if (!isForeignKeysEnabled())
            return;
        if (!db.isReadOnly()) {
            db.execSQL(PRAGMA_ENABLE_FOREIGN_KEYS);
        }
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (isWriteAheadLoggingEnabled())
            db.enableWriteAheadLogging();
        if (!isForeignKeysEnabled())
            return;
        if (Build.VERSION.SDK_INT != Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
            return;
        }
        if (!db.isReadOnly()) {
            db.execSQL(PRAGMA_ENABLE_FOREIGN_KEYS);
        }
    }

    protected boolean isWriteAheadLoggingEnabled() {
        return false;
    }

    protected boolean isForeignKeysEnabled() {
        return true;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        StorageSchemaImpl.onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (isForeignKeysEnabled() && Build.VERSION.SDK_INT == Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
            boolean wasInTransaction = false;
            if (db.inTransaction()) {
                db.endTransaction();
                wasInTransaction = true;
            }
            db.execSQL(PRAGMA_ENABLE_FOREIGN_KEYS);
            if (wasInTransaction)
                db.beginTransaction();
        }

        IUpdateContainer updater = SqlUpdateVersionMatcher.get().getUpdater(oldVersion, newVersion);

        try {
            if (updater == null) {
                throw new UnsupportedOperationException();
            }
            updater.onUpdate(db);
        } catch (UnsupportedOperationException e) {
            upgradeDrop(db);
        } catch (SQLiteConstraintException e) {
            onUpgradeConstraintError(db);
        }
    }

    protected void onUpgradeConstraintError(SQLiteDatabase db) {
        upgradeDropKeepSync(db);
    }

    protected void upgradeDrop(SQLiteDatabase db) {
        drop(db);
        onCreate(db);
        clearDbRelatedPreferences();
    }

    private void upgradeDropKeepSync(SQLiteDatabase db) {
        StorageSchemaImpl.onDrop(db, true);
        StorageSchemaImpl.onCreate(db, true);
        clearDbRelatedPreferences();
    }

    protected void drop(SQLiteDatabase db) {
        StorageSchemaImpl.onDrop(db);
    }

    public synchronized boolean clearDatabase() {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            drop(db);
            onCreate(db);
            db.setTransactionSuccessful();
            return true;
        } catch (Exception e) {
        } finally {
            db.endTransaction();
        }
        return false;
    }

    protected void clearDbRelatedPreferences() {
    }
}