package org.cloud.carassistant.entity;

/**
 * @author d05660ddw
 * @version 1.0 2017/3/3
 */

public class FuelConsumption {
    private float oilMass;
    private float money;
    private long mileage;

    public FuelConsumption() {
    }

    public FuelConsumption(float oilMass, float money, long mileage) {
        this.oilMass = oilMass;
        this.money = money;
        this.mileage = mileage;
    }

    public float getOilMass() {
        return oilMass;
    }

    public void setOilMass(float oilMass) {
        this.oilMass = oilMass;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public long getMileage() {
        return mileage;
    }

    public void setMileage(long mileage) {
        this.mileage = mileage;
    }
}
