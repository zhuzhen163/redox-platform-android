package com.redoxyt.platform.bean;

import java.util.List;

/**
 * Created by zz.
 * description:
 */

public class CheckSubmitBean {
    private String reserveId;
    private String warehouseGroupId;
    private String driverId;
    List<CheckRecordDetail> recordDetailList;

    public String getReserveId() {
        return reserveId;
    }

    public void setReserveId(String reserveId) {
        this.reserveId = reserveId;
    }

    public String getWarehouseGroupId() {
        return warehouseGroupId;
    }

    public void setWarehouseGroupId(String warehouseGroupId) {
        this.warehouseGroupId = warehouseGroupId;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public List<CheckRecordDetail> getRecordDetailList() {
        return recordDetailList;
    }

    public void setRecordDetailList(List<CheckRecordDetail> recordDetailList) {
        this.recordDetailList = recordDetailList;
    }
}
