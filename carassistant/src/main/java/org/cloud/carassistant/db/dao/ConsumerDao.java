package org.cloud.carassistant.db.dao;

import android.content.ContentValues;
import android.database.Cursor;

import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;

import org.cloud.carassistant.db.table.ConsumerTable;
import org.cloud.carassistant.entity.ConsumerDetail;
import org.cloud.carassistant.utils.CloseUtil;
import org.cloud.carassistant.utils.CursorUtil;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.functions.Func1;

/**
 * @author d05660ddw
 * @version 1.0 2017/2/28
 */

public class ConsumerDao {
    private BriteDatabase mDatabase;

    public ConsumerDao(BriteDatabase mDatabase) {
        this.mDatabase = mDatabase;
    }

    public long insert(ConsumerDetail detail) {
        ContentValues values = new ContentValues();
        values.put(ConsumerTable.COLUMN_CREATE_TIME, detail.getCreateTime());
        values.put(ConsumerTable.COLUMN_CONSUMPTION_TIME, detail.getConsumptionTime());
        values.put(ConsumerTable.COLUMN_TYPE, detail.getType());
        values.put(ConsumerTable.COLUMN_MONEY, detail.getMoney());
        values.put(ConsumerTable.COLUMN_OIL_TYPE, detail.getOilType());
        values.put(ConsumerTable.COLUMN_UNIT_PRICE, detail.getUnitPrice());
        values.put(ConsumerTable.COLUMN_CURRENT_MILEAGE, detail.getCurrentMileage());
        values.put(ConsumerTable.COLUMN_NOTES, detail.getNotes());
        return mDatabase.insert(ConsumerTable.NAME, values);
    }

    public void insert(List<ConsumerDetail> list) {
        BriteDatabase.Transaction transaction = mDatabase.newTransaction();
        try {
            for (ConsumerDetail detail : list) {
                insert(detail);
            }
            transaction.markSuccessful();
        } finally {
            transaction.end();
        }
    }

    public int update(ConsumerDetail detail) {
        ContentValues values = new ContentValues();
        values.put(ConsumerTable.COLUMN_CONSUMPTION_TIME, detail.getConsumptionTime());
        values.put(ConsumerTable.COLUMN_TYPE, detail.getType());
        values.put(ConsumerTable.COLUMN_MONEY, detail.getMoney());
        values.put(ConsumerTable.COLUMN_OIL_TYPE, detail.getOilType());
        values.put(ConsumerTable.COLUMN_UNIT_PRICE, detail.getUnitPrice());
        values.put(ConsumerTable.COLUMN_CURRENT_MILEAGE, detail.getCurrentMileage());
        values.put(ConsumerTable.COLUMN_NOTES, detail.getNotes());
        return mDatabase.update(ConsumerTable.NAME, values, ConsumerTable.COLUMN_ID + " = ?",
                String.valueOf(detail.getId()));
    }

    public Observable<List<ConsumerDetail>> queryAll() {
        StringBuilder sql = new StringBuilder("SELECT * FROM ").append(ConsumerTable.NAME)
                .append(" ORDER BY ").append(ConsumerTable.COLUMN_CONSUMPTION_TIME)
                .append(" DESC ");
        return queryBySQL(sql.toString());
    }

    public Observable<List<ConsumerDetail>> queryByType(Integer type) {
        StringBuilder sql = new StringBuilder("SELECT * FROM ").append(ConsumerTable.NAME);
        if (null != type) {
            sql.append(" WHERE ").append(ConsumerTable.COLUMN_TYPE).append(" = ").append(type);
        }
        sql.append(" ORDER BY ").append(ConsumerTable.COLUMN_CONSUMPTION_TIME);
        return queryBySQL(sql.toString());
    }

    public int delete(long id) {
        return mDatabase.delete(ConsumerTable.NAME, ConsumerTable.COLUMN_ID + " = ? ", String
                .valueOf(id));
    }

    private Observable<List<ConsumerDetail>> queryBySQL(String s) {
        return mDatabase.createQuery(ConsumerTable.NAME, s)
                .map(new Func1<SqlBrite.Query, List<ConsumerDetail>>() {
                    @Override
                    public List<ConsumerDetail> call(SqlBrite.Query query) {
                        return convert(query.run());
                    }
                });
    }

    private List<ConsumerDetail> convert(Cursor cursor) {
        if (null == cursor) {
            return null;
        }
        ArrayList<ConsumerDetail> result = new ArrayList<>();
        try {
            while (cursor.moveToNext()) {
                result.add(new ConsumerDetail(CursorUtil.getLong(cursor, ConsumerTable.COLUMN_ID),
                        CursorUtil.getLong(cursor, ConsumerTable.COLUMN_CREATE_TIME),
                        CursorUtil.getLong(cursor, ConsumerTable.COLUMN_CONSUMPTION_TIME),
                        CursorUtil.getFloat(cursor, ConsumerTable.COLUMN_MONEY),
                        CursorUtil.getInt(cursor, ConsumerTable.COLUMN_TYPE),
                        CursorUtil.getString(cursor, ConsumerTable.COLUMN_NOTES),
                        CursorUtil.getInt(cursor, ConsumerTable.COLUMN_OIL_TYPE),
                        CursorUtil.getFloat(cursor, ConsumerTable.COLUMN_UNIT_PRICE),
                        CursorUtil.getLong(cursor, ConsumerTable.COLUMN_CURRENT_MILEAGE)
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            CloseUtil.close(cursor);
        }
        return result;
    }
}
