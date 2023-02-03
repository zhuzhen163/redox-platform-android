package com.redoxyt.platform.bean;

import com.redoxyt.platform.base.AppModelStatus;

import java.io.Serializable;

public class ReserveListBean extends AppModelStatus implements Serializable {

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

    public int getPlatformId() {
        return platformId;
    }

    public void setPlatformId(int platformId) {
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

    /**
     * muchId : 24
     * warehouseGroupId : 1164
     * warehouseId : 20
     * warehouseCode : CK0020
     * warehouseName : 新建仓库004
     * platformId : 155
     * platformName : ck0020新建月台3
     * startTime : 16:00:00
     * endTime : 17:00:00
     * startEndTime : 16:00:00-17:00:00
     * platformTimezoneId : 3138
     * muchWarehouseState : 0
     * queueNum : 1
     * classNo : 12
     * createUserId : 1095
     * createTime : 2021-03-09 15:04:19
     * updateUserId : 1095
     * updateTime : 2021-03-09 15:04:19
     */

    private int muchId;
    private int warehouseGroupId;
    private int warehouseId;
    private String warehouseCode;
    private String warehouseName;
    private int platformId;
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
}
