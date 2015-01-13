package com.mayer.sql.update.version;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by dot on 24.06.2014.
 */
public interface IUpdateContainer {

    int VERSION1 = 1;
    int VERSION2 = 2;

    void onUpdate(final SQLiteDatabase db);
    int getSqlOldVersion();
    int getSqlNewVersion();
}
