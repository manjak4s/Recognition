package com.mayer.sql.update.version;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * @author Ivan v. Rikhmayer
 *         This class is intended to
 */
class UpdateBlock {

    // tables
    private static final String SQL_CREATE_EXAMPLE = "ALTER TABLE bp_description ADD COLUMN is_failed INTEGER";
    private static final String SQL_DROP_EXAMPLE = "DROP TABLE wireless_pin_item;";

    // indexes
    private static final String SQL_CREATE_INDEX_EXAMPLE = "create index idx_employee_tips_shift on employee_tips( shift_id)";

    // Views
    private static final String SQL_CREATE_VIEW_EXAMPLE = "CREATE VIEW tips_report_view AS SELECT  tips_table.guid as tips_table_guid, tips_table.parent_guid as tips_table_parent_guid, tips_table.employee_id as tips_table_employee_id, tips_table.shift_id as tips_table_shift_id, tips_table.order_id as tips_table_order_id, tips_table.payment_transaction_id as tips_table_payment_transaction_id, tips_table.create_time as tips_table_create_time, tips_table.amount as tips_table_amount, tips_table.comment as tips_table_comment, tips_table.payment_type as tips_table_payment_type, tips_table.is_deleted as tips_table_is_deleted, tips_table.update_time as tips_table_update_time, shift_table.start_time as shift_table_start_time, shift_table.end_time as shift_table_end_time, employee_table.first_name as employee_table_first_name, employee_table.last_name as employee_table_last_name FROM employee_tips AS tips_table JOIN shift AS shift_table ON shift_table.guid = tips_table.shift_id and shift_table.is_deleted = 0 LEFT OUTER JOIN employee AS employee_table ON employee_table.guid = tips_table.employee_id and employee_table.is_deleted = 0 where tips_table.is_deleted = 0";private static final String SQL_ALTER_DROP_AWESOME_VIEW = "DROP view if exists awesome_view";


    static void update1to2(SQLiteDatabase database) {
        //database.execSQL(SQL_CREATE_EXAMPLE);
    }

    private static String makePlaceholders(int len) {
        if (len < 1) {
            throw new RuntimeException("No placeholders");
        } else {
            StringBuilder sb = new StringBuilder(len * 2 - 1);
            sb.append("?");
            for (int i = 1; i < len; i++) {
                sb.append(",?");
            }
            return sb.toString();
        }
    }

    private static ArrayList<String> getMerchantGuids(Cursor c) {
        Pattern pattern = Pattern.compile("^[0-9]{3}$");
        ArrayList<String> guids = new ArrayList<String>();
        while (c.moveToNext()) {
            String login = c.getString(c.getColumnIndex("login"));
            if (!pattern.matcher(login).matches())
                guids.add(c.getString(c.getColumnIndex("guid")));
        }
        return guids;
    }

}
