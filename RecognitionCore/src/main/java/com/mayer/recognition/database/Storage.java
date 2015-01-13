package com.mayer.recognition.database;

/**
 * Created by irikhmayer on 13.01.2015.
 */

import com.annotatedsql.annotation.provider.Provider;
import com.annotatedsql.annotation.provider.URI;
import com.annotatedsql.annotation.sql.Autoincrement;
import com.annotatedsql.annotation.sql.Column;
import com.annotatedsql.annotation.sql.NotNull;
import com.annotatedsql.annotation.sql.PrimaryKey;
import com.annotatedsql.annotation.sql.Schema;
import com.annotatedsql.annotation.sql.Table;

@Schema(className = "StorageSchema", dbName = "storage.db", dbVersion = 1)
@Provider(name = "StorageProvider", authority = "com.mayer.recognition.database.AUTHORITY", schemaClass = "StorageSchema", openHelperClass = "DatabaseHelper")
public abstract class Storage {

    public static final String CREATE_TIME = "update_time";

    /*force static initialization of class*/
    public static void init() { }

    public static interface ISyncTable {

        @PrimaryKey
        @Autoincrement
        @Column(type = Column.Type.INTEGER)
        String ID = "_id";

        @Column(type = Column.Type.INTEGER, defVal = "0")
        String favourite = "favourite";

        @Column(type = Column.Type.TEXT)
        String url = "image_url";

        @Column(type = Column.Type.TEXT)
        String longitudeTaken = "longitudeTaken";

        @Column(type = Column.Type.TEXT)
        String latitudeTaken = "latitudeTaken";

        @Column(type = Column.Type.INTEGER)
        String CREATE_TIME = Storage.CREATE_TIME;
    }

    public static interface IReceiptTable extends ISyncTable {
        @Column(type = Column.Type.TEXT)
        String discount_value = "discount_value";

        @Column(type = Column.Type.INTEGER)
        String discount_type = "discount_type";

        @Column(type = Column.Type.TEXT)
        String tips_value = "tips_value";

        @Column(type = Column.Type.TEXT)
        String tax_value = "tax_value";
    }

    @Table(RecognitionOrderTable.TABLE_NAME)
    public static interface RecognitionOrderTable extends IReceiptTable {

        @URI
        String URI_CONTENT = "recognition_item";

        String TABLE_NAME = "recognition_item";

        @Column(type = Column.Type.TEXT)
        String plain_body = "plain_body";

        @Column(type = Column.Type.TEXT)
        String total_price = "total_price";
    }

    @Table(RecognitionSubitemTable.TABLE_NAME)
    public static interface RecognitionSubitemTable extends IReceiptTable {

        @URI
        String URI_CONTENT = "recognition_subitem";

        String TABLE_NAME = "recognition_subitem";

        @Column(type = Column.Type.TEXT)
        String total_price = "total_price";

        @Column(type = Column.Type.TEXT)
        String price = "price";

        @Column(type = Column.Type.TEXT)
        String quantity = "quantity";

        @NotNull
        @Column(type = Column.Type.INTEGER)
        String ORDER_ID = "order_id";
    }
}
