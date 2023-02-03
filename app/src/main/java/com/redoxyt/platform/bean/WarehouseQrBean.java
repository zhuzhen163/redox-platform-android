package com.redoxyt.platform.bean;

public class WarehouseQrBean {
    private String codeType;//二维码标识1企业，2仓库，3月台
    private String warehouseId;//仓库id
    private String warehouseCode;//仓库码

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public String getCodeType() {
        return codeType;
    }

    public void setCodeType(String codeType) {
        this.codeType = codeType;
    }

    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }

}
