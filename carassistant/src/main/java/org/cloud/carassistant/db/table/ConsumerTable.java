package org.cloud.carassistant.db.table;

/**
 * @author d05660ddw
 * @version 1.0 2017/2/27
 */

public class ConsumerTable {
    public static final String NAME = "t_consumer";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_CREATE_TIME = "createTime";
    public static final String COLUMN_CONSUMPTION_TIME = "consumptionTime";
    public static final String COLUMN_MONEY = "money";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_NOTES = "notes";
    public static final String COLUMN_OIL_TYPE = "oilType";
    public static final String COLUMN_UNIT_PRICE = "unitPrice";
    public static final String COLUMN_CURRENT_MILEAGE = "currentMileage";

    public static String create() {
        return new StringBuilder("CREATE TABLE ").append(NAME).append("(")
                .append(COLUMN_ID).append(" INTEGER PRIMARY KEY AUTOINCREMENT,")
                .append(COLUMN_CREATE_TIME).append(" INTEGER NOT NULL,")
                .append(COLUMN_CONSUMPTION_TIME).append(" INTEGER NOT NULL,")
                .append(COLUMN_MONEY).append(" INTEGER,")
                .append(COLUMN_UNIT_PRICE).append(" INTEGER,")
                .append(COLUMN_TYPE).append(" INTEGER,")
                .append(COLUMN_OIL_TYPE).append(" INTEGER,")
                .append(COLUMN_CURRENT_MILEAGE).append(" INTEGER,")
                .append(COLUMN_NOTES).append(" TEXT")
                .append(")").toString();
    }
}
