package org.cloud.carassistant.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.cloud.carassistant.db.table.ConsumerTable;

/**
 * @author d05660ddw
 * @version 1.0 2017/2/27
 */

public class DbOpenHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "Car.db";
    private static final int DB_Version = 1;

    public DbOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_Version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ConsumerTable.create());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
