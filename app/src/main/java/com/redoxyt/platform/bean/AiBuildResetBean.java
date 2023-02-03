package com.redoxyt.platform.bean;

import java.io.Serializable;

public class AiBuildResetBean implements Serializable {

    public int getMuchId() {
        return muchId;
    }

    public void setMuchId(int muchId) {
        this.muchId = muchId;
    }

    public int getWarehouseGroupId() {
        return warehouseGroupId;
    }

    public void setWarehouseGroupId(int warehouseGroupId) {
        this.warehouseGroupId = warehouseGroupId;
    }

    public int getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(int warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
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

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStartEndTime() {
        return startEndTime;
    }

    public void setStartEndTime(String startEndTime) {
        this.startEndTime = startEndTime;
    }

    public int getPlatformTimezoneId() {
        return platformTimezoneId;
    }

    public void setPlatformTimezoneId(int platformTimezoneId) {
        this.platformTimezoneId = platformTimezoneId;
    }

    public int getMuchWarehouseState() {
        return muchWarehouseState;
    }

    public void setMuchWarehouseState(int muchWarehouseState) {
        this.muchWarehouseState = muchWarehouseState;
    }

    public int getQueueNum() {
        return queueNum;
    }

    public void setQueueNum(int queueNum) {
        this.queueNum = queueNum;
    }

    public int getClassNo() {
        return classNo;
    }

    public void setClassNo(int classNo) {
        this.classNo = classNo;
    }

    public int getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(int createUserId) {
        this.createUserId = createUserId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(int updateUserId) {
        this.updateUserId = updateUserId;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getYyyymmdd() {
        return yyyymmdd;
    }

    public void setYyyymmdd(String yyyymmdd) {
        this.yyyymmdd = yyyymmdd;
    }

    public String getPlatformRuleId() {
        return platformRuleId;
    }

    public void setPlatformRuleId(String platformRuleId) {
        this.platformRuleId = platformRuleId;
    }

    public int getReserveType() {
        return reserveType;
    }

    public void setReserveType(int reserveType) {
        this.reserveType = reserveType;
    }

    /**
     * muchId : 124
     * warehouseGroupId : 1164
     * warehouseId : 20
     * warehouseCode : CK0020
     * warehouseName : 新建仓库004
     * platformId : 153
     * platformName : ck0020新建月台1
     * startTime : 20:00:00
     * endTime : 21:00:00
     * startEndTime : 20:00:00-21:00:00
     * platformTimezoneId : 3107
     * muchWarehouseState : 1
     * queueNum : 17
     * classNo : 18
     * createUserId : 1095
     * createTime : 2021-03-09 18:19:01
     * updateUserId : 1095
     * updateTime : 2021-03-09 18:19:59
     */

    private int muchId;
    private int warehouseGroupId;
    private int warehouseId;
    private String warehouseCode;
    private String warehouseName;
    private String platformId;
    private String platformName;
    private String startTime;
    private String endTime;
    private String startEndTime;
    private int platformTimezoneId;
    private int muchWarehouseState;
    private int queueNum;
    private int classNo;
    private int createUserId;
    private String createTime;
    private int updateUserId;
    private String updateTime;
    private String yyyymmdd;
    private String platformRuleId;
    private int reserveType;
}
