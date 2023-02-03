package com.redoxyt.platform.bean;

import java.util.List;

public class OrderWareHouseYtListBean {
    private List<String> warehouseIds;

    public List<String> getWarehouseIds() {
        return warehouseIds;
    }

    public void setWarehouseIds(List<String> warehouseIds) {
        this.warehouseIds = warehouseIds;
    }

    public List<String> getPlatformIds() {
        return platformIds;
    }

    public void setPlatformIds(List<String> platformIds) {
        this.platformIds = platformIds;
    }

    private List<String> platformIds;
}
