package com.redoxyt.platform.bean;

/**
 * 预约列表
 */
public class WarehouseBean {

    private String groupId;//仓库机构ID
    private String platformWarehouseName;//仓库名称，货主名称
    private String platformWarehouseCode;//仓库代码
    private String warehouseId;//仓库id
    private String groupAddress;//仓库地址

    public String getGroupAddress() {
        return groupAddress;
    }

    public void setGroupAddress(String groupAddress) {
        this.groupAddress = groupAddress;
    }

    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getPlatformWarehouseName() {
        return platformWarehouseName;
    }

    public void setPlatformWarehouseName(String platformWarehouseName) {
        this.platformWarehouseName = platformWarehouseName;
    }

    public String getPlatformWarehouseCode() {
        return platformWarehouseCode;
    }

    public void setPlatformWarehouseCode(String platformWarehouseCode) {
        this.platformWarehouseCode = platformWarehouseCode;
    }
}
