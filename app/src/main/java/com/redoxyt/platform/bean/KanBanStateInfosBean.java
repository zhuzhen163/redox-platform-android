package com.redoxyt.platform.bean;

/**
 */
public class KanBanStateInfosBean {

    private String carCode;
    private String reserveStatusName;
    private String carInfo;
    private int carState;

    public int getCarState() {
        return carState;
    }

    public void setCarState(int carState) {
        this.carState = carState;
    }

    public String getCarCode() {
        return carCode;
    }

    public void setCarCode(String carCode) {
        this.carCode = carCode;
    }

    public String getReserveStatusName() {
        return reserveStatusName;
    }

    public void setReserveStatusName(String reserveStatusName) {
        this.reserveStatusName = reserveStatusName;
    }

    public String getCarInfo() {
        return carInfo;
    }

    public void setCarInfo(String carInfo) {
        this.carInfo = carInfo;
    }
}
