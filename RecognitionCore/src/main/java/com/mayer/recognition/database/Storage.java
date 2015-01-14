package com.mayer.recognition.database;

/**
 * Created by irikhmayer on 13.01.2015.
 */

import com.annotatedsql.annotation.provider.Provider;
import com.annotatedsql.annotation.provider.URI;
import com.annotatedsql.annotation.sql.Autoincrement;
import com.annotatedsql.annotation.sql.Column;
import com.annotatedsql.annotation.sql.Index;
import com.annotatedsql.annotation.sql.Indexes;
import com.annotatedsql.annotation.sql.NotNull;
import com.annotatedsql.annotation.sql.PrimaryKey;
import com.annotatedsql.annotation.sql.Schema;
import com.annotatedsql.annotation.sql.Table;

@Schema(className = "StorageSchema", dbName = "storage.db", dbVersion = Storage.DB_VERSION)
@Provider(name = "StorageProvider", authority = "com.mayer.recognition.database.AUTHORITY", schemaClass = "StorageSchema", openHelperClass = "StorageHelper")
public abstract class Storage {

    public static final int DB_VERSION = 2;

    public static final String CREATE_TIME = "update_time";

    /*force static initialization of class*/
    public static void init() { }

    public static interface ISyncTable {

        @PrimaryKey
        @Autoincrement
        @Column(type = Column.Type.INTEGER)
        String ID = "_id";

        @Column(type = Column.Type.INTEGER, defVal = "0")
        String FAVOURITE = "favourite";

        @Column(type = Column.Type.TEXT)
        String URL = "image_url";
    }

    public static interface IReceiptTable extends ISyncTable {
        @Column(type = Column.Type.TEXT)
        String DISCOUNT_VALUE = "discount_value";

        @Column(type = Column.Type.INTEGER)
        String DISCOUNT_TYPE = "discount_type";

        @Column(type = Column.Type.TEXT)
        String TAX_VALUE = "tax_value";
    }

    @Table(RecognitionOrderTable.TABLE_NAME)
    public static interface RecognitionOrderTable extends IReceiptTable {

        @URI
        String URI_CONTENT = "recognition_item";

        String TABLE_NAME = "recognition_item";

        @Column(type = Column.Type.TEXT)
        String PLAIN_BODY = "plain_body";

        @Column(type = Column.Type.TEXT)
        String TOTAL_PRICE = "total_price";

        @Column(type = Column.Type.TEXT)
        String LONGITUDE_TAKEN = "longitudeTaken";

        @Column(type = Column.Type.TEXT)
        String LATITUDE_TAKEN = "latitudeTaken";

        @Column(type = Column.Type.INTEGER)
        String CREATE_TIME = Storage.CREATE_TIME;

        @Column(type = Column.Type.TEXT)
        String TIPS_VALUE = "tips_value";
    }

    @Table(RecognitionSubitemTable.TABLE_NAME)
    @Indexes(@Index(name = "order", columns = RecognitionSubitemTable.ORDER_ID))
    public static interface RecognitionSubitemTable extends IReceiptTable {

        @URI
        String URI_CONTENT = "recognition_subitem";

        String TABLE_NAME = "recognition_subitem";

        @Column(type = Column.Type.TEXT)
        String TOTAL_PRICE = "total_price";

        @Column(type = Column.Type.TEXT)
        String PRICE = "price";

        @Column(type = Column.Type.TEXT)
        String QUANTITY = "quantity";

        @NotNull
        @Column(type = Column.Type.INTEGER)
        String ORDER_ID = "order_id";

        @Column(type = Column.Type.INTEGER, defVal = "0")
        String SELECTER = "selected";
    }

//    static {
//        applyForeignKeys(RecognitionSubitemTable.TABLE_NAME, foreignKey(RecognitionSubitemTable.ORDER_ID, RecognitionOrderTable.TABLE_NAME, RecognitionOrderTable.ID));
//    }
}
