package org.cloud.carassistant.entity;

import java.io.Serializable;

/**
 * @author d05660ddw
 * @version 1.0 2017/2/28
 */

public class ConsumerDetail implements Serializable {
    private static final long serialVersionUID = 6708495983469206253L;

    private long id;
    private long createTime;
    private long consumptionTime;
    private float money;

    private int type;
    private String notes;

    private int oilType;
    private float unitPrice;
    private long currentMileage;

    public ConsumerDetail() {
    }

    public ConsumerDetail(long consumptionTime, float money, int type) {
        this.consumptionTime = consumptionTime;
        this.money = money;
        this.type = type;
    }

    public ConsumerDetail(int type, float money, long consumptionTime, String notes) {
        this.type = type;
        this.money = money;
        this.consumptionTime = consumptionTime;
        this.notes = notes;
    }

    public ConsumerDetail(int type, float money, long consumptionTime, float unitPrice, long
            currentMileage, String notes, int oilType) {
        this.type = type;
        this.money = money;
        this.consumptionTime = consumptionTime;
        this.unitPrice = unitPrice;
        this.currentMileage = currentMileage;
        this.notes = notes;
        this.oilType = oilType;
        this.createTime = System.currentTimeMillis();
    }

    public ConsumerDetail(long id, long createTime, long consumptionTime, float money, int type,
                          String notes, int oilType, float unitPrice, long currentMileage) {
        this.id = id;
        this.createTime = createTime;
        this.consumptionTime = consumptionTime;
        this.money = money;
        this.type = type;
        this.notes = notes;
        this.oilType = oilType;
        this.unitPrice = unitPrice;
        this.currentMileage = currentMileage;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getConsumptionTime() {
        return consumptionTime;
    }

    public void setConsumptionTime(long consumptionTime) {
        this.consumptionTime = consumptionTime;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getOilType() {
        return oilType;
    }

    public void setOilType(int oilType) {
        this.oilType = oilType;
    }

    public float getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(float unitPrice) {
        this.unitPrice = unitPrice;
    }

    public long getCurrentMileage() {
        return currentMileage;
    }

    public void setCurrentMileage(long currentMileage) {
        this.currentMileage = currentMileage;
    }
}
