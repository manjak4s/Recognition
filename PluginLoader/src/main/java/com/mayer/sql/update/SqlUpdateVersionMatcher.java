package com.mayer.sql.update;

import com.mayer.sql.update.version.IUpdateContainer;
import com.mayer.sql.update.version.Update1to2;

public class SqlUpdateVersionMatcher {

    private SqlUpdateVersionMatcher() {

    }

    public static SqlUpdateVersionMatcher get() {
        return new SqlUpdateVersionMatcher();
    }

    public IUpdateContainer getUpdater(int oldVersion, int newVersion) {
        for (VERSION version : VERSION.values()) {
            if (version.container.getSqlOldVersion() == oldVersion && version.container.getSqlNewVersion() == newVersion) {
                return version.container;
            }
        }

        throw new UnsupportedOperationException("This update not supported. oldVersion: " + oldVersion + " newVersion: " + newVersion);
    }

    private enum VERSION {

        UPDATE1TO2(new Update1to2());
        private IUpdateContainer container;

        private VERSION(IUpdateContainer container) {

            this.container = container;
        }
    }
}
