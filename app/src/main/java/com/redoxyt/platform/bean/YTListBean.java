package com.redoxyt.platform.bean;

import com.redoxyt.platform.base.AppModelStatus;

/**
 * 月台列表
 */
public class YTListBean extends AppModelStatus {

    private String platformId;//月台Id
    private String platformName;//月台名称
    private String warehouseGroupId;//仓库机构id
    private String warehouseName;//仓库名称
    private String warehouseCode;//仓库代码
    private String warehouseId;//仓库id
    private boolean isCheck;

    private String reserveId;//预约ID
    private String reserveCode;//预约码
    private String transportCode;//运单号
    private String carCode;//车牌号

    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getReserveId() {
        return reserveId;
    }

    public void setReserveId(String reserveId) {
        this.reserveId = reserveId;
    }

    public String getReserveCode() {
        return reserveCode;
    }

    public void setReserveCode(String reserveCode) {
        this.reserveCode = reserveCode;
    }

    public String getTransportCode() {
        return transportCode;
    }

    public void setTransportCode(String transportCode) {
        this.transportCode = transportCode;
    }

    public String getCarCode() {
        return carCode;
    }

    public void setCarCode(String carCode) {
        this.carCode = carCode;
    }

    public String getPlatformId() {
        return platformId;
    }

    public void setPlatformId(String platformId) {
        this.platformId = platformId;
    }

    public String getPlatformName() {
        return platformName;
    }

    public void setPlatformName(String platformName) {
        this.platformName = platformName;
    }

    public String getWarehouseGroupId() {
        return warehouseGroupId;
    }

    public void setWarehouseGroupId(String warehouseGroupId) {
        this.warehouseGroupId = warehouseGroupId;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

}
