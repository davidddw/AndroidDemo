package org.cloud.carassistant.utils;

import android.database.Cursor;

/**
 * @author d05660ddw
 * @version 1.0 2017/2/28
 */

public class CursorUtil {
    private CursorUtil() {

    }

    public static String getString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndexOrThrow(columnName));
    }

    public static long getLong(Cursor cursor, String columnName) {
        return cursor.getLong(cursor.getColumnIndexOrThrow(columnName));
    }

    public static double getDouble(Cursor cursor, String columnName){
        return cursor.getDouble(cursor.getColumnIndexOrThrow(columnName));
    }

    public static float getFloat(Cursor cursor, String columnName){
        return cursor.getFloat(cursor.getColumnIndexOrThrow(columnName));
    }

    public static short getShort(Cursor cursor, String columnName){
        return cursor.getShort(cursor.getColumnIndexOrThrow(columnName));
    }

    public static int getInt(Cursor cursor, String columnName){
        return cursor.getInt(cursor.getColumnIndexOrThrow(columnName));
    }

    public static byte[] getBlob(Cursor cursor, String columnName){
        return cursor.getBlob(cursor.getColumnIndexOrThrow(columnName));
    }
}
