package com.mayer.recognition.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

/**
 * Created by irikhmayer on 13.01.2015.
 */
public class StorageSchemaImpl {
    private static final String CREATE_STATEMENT_PREFIX = "SQL_CREATE";
    private static final String CREATE_TABLE_STATEMENT_PREFIX = "CREATE TABLE";
    private static final String CREATE_INDEX_STATEMENT_PREFIX = "CREATE INDEX";
    private static final String CREATE_VIEW_STATEMENT_PREFIX = "CREATE VIEW";
    private static final String FOREIGN_KEY_STATEMENT_FORMAT = "FOREIGN KEY(%1$s) REFERENCES %2$s(%3$s)";
    private static final String DROP_TABLE_STATEMENT_FORMAT = "DROP TABLE IF EXISTS %s";
    private static final String DROP_VIEW_STATEMENT_FORMAT = "DROP VIEW IF EXISTS %s";
    private static final String SELECT_TABLE_NAMES_STATEMENT = "SELECT name FROM sqlite_master WHERE type='table'";
    private static final String SELECT_VIEW_NAMES_STATEMENT = "SELECT name FROM sqlite_master WHERE type='view'";
    private static final String AUTOINCREMENT_STATEMENT = "AUTOINCREMENT";

    private static final String ANDROID_METADATA_TABLE_NAME = "android_metadata";
    private static final String SQLITE_SYSTEM_TABLES_PREFIX = "sqlite_";

    private static final HashMap<String, Table> CREATE_FOREIGN_KEY_STATEMENTS = new HashMap<String, Table>();

    public static void applyForeignKeys(String childTable, ForeignKey... foreignKeys) {
        if (TextUtils.isEmpty(childTable))
            throw new IllegalArgumentException("childTable cannot be empty or null!");
        if (foreignKeys == null || foreignKeys.length == 0)
            throw new IllegalArgumentException("foreignKeys cannot be empty or null!");
        if (CREATE_FOREIGN_KEY_STATEMENTS.containsKey(childTable))
            throw new IllegalStateException("foreign keys already applied to the table " + childTable + "!");

        CREATE_FOREIGN_KEY_STATEMENTS.put(childTable, new Table(childTable, foreignKeys));
    }

    private static HashMap<String, List<String>> TMP_FIELDS = new HashMap<String, List<String>>();

    public static void applyTmpFields(String tableName, String... fields){
        if (TextUtils.isEmpty(tableName))
            throw new IllegalArgumentException("applyTmpFields: table cannot be empty or null!");
        if (fields == null || fields.length == 0)
            throw new IllegalArgumentException("applyTmpFields: fields cannot be empty or null!");
        if (TMP_FIELDS.containsKey(tableName))
            throw new IllegalStateException("temp fields already applied to the table " + tableName + "!");

        TMP_FIELDS.put(tableName, Arrays.asList(fields));
    }

    public static void onCreate(final SQLiteDatabase db) {
        onCreate(db, false, false);
    }

    public static void onCreate(final SQLiteDatabase db, boolean keepSyncData) {
        onCreate(db, keepSyncData, false);
    }

    public static void onCreate(final SQLiteDatabase db, boolean keepSyncData, boolean isSyncDatabase) {
        Storage.init();

        ArrayList<Table> createTables = new ArrayList<Table>();
        List<String> createIndexStatements = new ArrayList<String>();
        List<String> createViewStatements = new ArrayList<String>();

        Field[] declaredFields = Storage.class.getDeclaredFields();
        for (Field field : declaredFields) {
            if (!isCreateStatementField(field)) {
                continue;
            }

            String sqlCreateStatement = getCreateStatement(field);
            if (sqlCreateStatement.startsWith(CREATE_TABLE_STATEMENT_PREFIX)) {
                String tableName = getTableName(sqlCreateStatement);

                Table table = !isSyncDatabase ? CREATE_FOREIGN_KEY_STATEMENTS.get(tableName) : null;
                if (table == null) {
                    table = new Table(tableName);
                }
                table.createStatement = sqlCreateStatement;
                createTables.add(table);
            } else if (sqlCreateStatement.startsWith(CREATE_INDEX_STATEMENT_PREFIX)) {
                createIndexStatements.add(sqlCreateStatement);
            } else if (sqlCreateStatement.startsWith(CREATE_VIEW_STATEMENT_PREFIX)) {
                createViewStatements.add(sqlCreateStatement);
            }
        }

        if (!isSyncDatabase)
            sortCreateTables(createTables);

        for (Table table: createTables) {
            db.execSQL(getCreateTableStatement(table, isSyncDatabase));
        }

        for (String sqlCreateStatement : createIndexStatements) {
            db.execSQL(sqlCreateStatement);
        }

        if (isSyncDatabase)
            return;

        for (String sqlCreateStatement : createViewStatements) {
            db.execSQL(sqlCreateStatement);
        }
    }

    public static void onDrop(final SQLiteDatabase db) {
        onDrop(db, false);
    }

    public static void onDrop(final SQLiteDatabase db, boolean keepSyncData) {
        Storage.init();

        ArrayList<Table> dropTables = new ArrayList<Table>();
        List<String> dropViewStatements = new ArrayList<String>();

        Cursor c = db.rawQuery(SELECT_TABLE_NAMES_STATEMENT, null);
        while (c.moveToNext()) {
            String tableName = c.getString(0);
            if (isSystemTable(tableName))
                continue;

            Table table = CREATE_FOREIGN_KEY_STATEMENTS.get(tableName);
            if (table == null) {
                table = new Table(tableName);
            }
            dropTables.add(table);
        }
        c.close();

        c = db.rawQuery(SELECT_VIEW_NAMES_STATEMENT, null);
        while (c.moveToNext()) {
            String viewName = c.getString(0);
            dropViewStatements.add(getDropViewStatement(viewName));
        }
        c.close();

        sortCreateTables(dropTables);

        int count = dropTables.size();
        for (int i = count - 1; i >= 0; i--) {
            Table table = dropTables.get(i);
            db.execSQL(getDropTableStatement(table));
        }

        for (String sqlDropStatement : dropViewStatements) {
            db.execSQL(sqlDropStatement);
        }
    }

    private static boolean isSystemTable(String tableName) {
        return tableName.startsWith(SQLITE_SYSTEM_TABLES_PREFIX) || tableName.equals(ANDROID_METADATA_TABLE_NAME);
    }

    private static boolean isCreateStatementField(Field field) {
        return field.getName().startsWith(CREATE_STATEMENT_PREFIX);
    }

    private static String getCreateStatement(Field createStatementField) {
        String createStatement = null;
        try {
            createStatement = (String) createStatementField.get(null);
        } catch (IllegalAccessException ignore) {}
        if (TextUtils.isEmpty(createStatement)) {
            throw new IllegalArgumentException("Cannot get sql create statement from: " + createStatementField.getName());
        }
        return createStatement;
    }

    private static String getDropTableStatement(Table table) {
        return String.format(Locale.US, DROP_TABLE_STATEMENT_FORMAT, table.tableName);
    }

    private static String getDropViewStatement(String viewName) {
        return String.format(Locale.US, DROP_VIEW_STATEMENT_FORMAT, viewName);
    }

    private static String getTableName(String createTableStatement) {
        return createTableStatement.substring(CREATE_TABLE_STATEMENT_PREFIX.length() + 1, createTableStatement.indexOf('('));
    }

    private static String getCreateTableStatement(Table table, boolean isSyncDatabase) {
        if (isSyncDatabase) {
            String statement = cutOffAutoincrementField(table);
            return cutOffTmpFields(table.tableName, statement);
        }

        if (table.foreignKeys == null) {
            return table.createStatement;
        }

        StringBuilder foreignKeyStatementsBuilder = new StringBuilder();
        for (ForeignKey foreignKey: table.foreignKeys) {
            if (foreignKeyStatementsBuilder.length() > 0) {
                foreignKeyStatementsBuilder.append(',');
            }
            foreignKeyStatementsBuilder.append(generateForeignKeyStatement(foreignKey));
        }
        return appendCreateForeignKeyStatement(table.createStatement, foreignKeyStatementsBuilder.toString());
    }

    private static String cutOffAutoincrementField(Table table) {
        int firstIndex = table.createStatement.indexOf(AUTOINCREMENT_STATEMENT);
        if (firstIndex == -1)
            return table.createStatement;

        int index = firstIndex + (AUTOINCREMENT_STATEMENT.length() - 1);
        boolean hasEndSemi = false;
        do {
            char ch = table.createStatement.charAt(++index);
            if (ch == ',') {
                hasEndSemi = true;
                --index;
                break;
            }
            if (ch == ')') {
                --index;
                break;
            }
        } while (index < table.createStatement.length());
        int lastIndex = index;

        index = firstIndex;
        boolean hasStartSemi = false;
        do {
            char ch = table.createStatement.charAt(--index);
            if (ch == '(') {
                ++index;
                break;
            }
            if (ch == ',') {
                hasStartSemi = true;
                ++index;
                break;
            }
        } while (index < table.createStatement.length());
        firstIndex = index;

        if (hasStartSemi && hasEndSemi) {
            firstIndex--;
        } else if (hasStartSemi) {
            firstIndex--;
        } else if (hasEndSemi) {
            lastIndex++;
        }

        return table.createStatement.substring(0, firstIndex) + table.createStatement.substring(lastIndex + 1, table.createStatement.length());
    }

    private static String cutOffTmpFields(String tableName, String createStatement){
        if (!TMP_FIELDS.containsKey(tableName))
            return createStatement;

        List<String> tmpFields = TMP_FIELDS.get(tableName);
        if (tmpFields.isEmpty())
            return createStatement;

        int openBracketIndex = createStatement.indexOf('(');

        String sub = createStatement.substring(openBracketIndex + 1, createStatement.length() - 1);

        for (String field : tmpFields){
            int index = sub.indexOf(field);
            if (index == -1)
                continue;

            int startIndex = 0;
            boolean first = true;
            while (index > 0){
                char ch = sub.charAt(--index);
                if (ch == ','){
                    startIndex = index;
                    first = false;
                    break;
                }
            }

            if (first){
                sub = sub.substring(sub.indexOf(',') + 1);
                continue;
            }

            boolean last = false;
            int endIndex = sub.indexOf(',', startIndex + 1);
            if (endIndex == -1){
                last = true;
            }

            if (last){
                sub = sub.substring(0, startIndex);
                continue;
            }

            sub = sub.substring(0, startIndex) + sub.substring(endIndex);
        }

        return createStatement.substring(0, openBracketIndex + 1) + sub + ")";
    }

    private static String generateForeignKeyStatement(ForeignKey foreignKey) {
        return String.format(Locale.US, FOREIGN_KEY_STATEMENT_FORMAT, foreignKey.childKey, foreignKey.parentTable, foreignKey.parentKey);
    }

    private static String appendCreateForeignKeyStatement(String createTableStatement, String createForeignKeyStatement) {
        return createTableStatement.substring(0, createTableStatement.length() - 1) + ',' + createForeignKeyStatement + ')';
    }

    private static void sortCreateTables(ArrayList<Table> createTableStatements) {
        boolean changed;
        HashSet<String> leftTables = new HashSet<String>(createTableStatements.size() - 1);
        do {
            changed = false;
            leftTables.clear();
            for (int i = 0; i < createTableStatements.size() - 1; i++) {
                Table leftTable = createTableStatements.get(i);

                boolean moveDown = false;
                if (leftTable.foreignKeys != null) {
                    for (ForeignKey foreignKey : leftTable.foreignKeys) {
                        if (foreignKey.parentTable.equals(leftTable.tableName))
                            continue;
                        if (!leftTables.contains(foreignKey.parentTable)) {
                            moveDown = true;
                            break;
                        }
                    }
                }

                if (!moveDown) {
                    leftTables.add(leftTable.tableName);
                    continue;
                }

                Table rightTable = createTableStatements.set(i + 1, leftTable);
                createTableStatements.set(i, rightTable);
                leftTables.add(rightTable.tableName);
                changed = true;
            }

        } while (changed);
    }


    private static class Table {
        public final String tableName;
        public final ArrayList<ForeignKey> foreignKeys;
        public String createStatement;

        public Table(String tableName) {
            this.tableName = tableName;
            foreignKeys = null;
        }

        public Table(String tableName, ForeignKey[] foreignKeys) {
            this.tableName = tableName;
            this.foreignKeys = new ArrayList<ForeignKey>();
            this.foreignKeys.addAll(Arrays.asList(foreignKeys));
        }
    }

    public static class ForeignKey {
        protected final String childKey;
        protected final String parentTable;
        protected final String parentKey;

        public static ForeignKey foreignKey(String childKey, String parentTable, String parentKey) {
            return new ForeignKey(childKey, parentTable, parentKey);
        }

        private ForeignKey(String childKey, String parentTable, String parentKey) {
            this.childKey = childKey;
            this.parentTable = parentTable;
            this.parentKey = parentKey;
        }
    }
}
