package com.redoxyt.platform.bean;

import com.redoxyt.platform.base.AppModelStatus;

/**
 * 车辆列表
 */
public class CarListBean extends AppModelStatus {

    private String carId;
    private String carCode;
    private String createTime;
    private int flag;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public String getCarCode() {
        return carCode;
    }

    public void setCarCode(String carCode) {
        this.carCode = carCode;
    }
}
