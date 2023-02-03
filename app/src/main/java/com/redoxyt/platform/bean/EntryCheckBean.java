package com.redoxyt.platform.bean;

import java.util.List;

/**
 * Created by zz.
 * description:入场检查列表
 */

public class EntryCheckBean {
    private String reserveId;//预约ID
    private String warehouseGroupId;//仓库机构ID
    private String driverId;//司机ID
    private List<WarehouseRule> ruleList;

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

    public List<WarehouseRule> getRuleList() {
        return ruleList;
    }

    public void setRuleList(List<WarehouseRule> ruleList) {
        this.ruleList = ruleList;
    }

    public class WarehouseRule{
        private String ruleId;//规则ID
        private String warehouseGroupId;//仓库机构ID
        private String ruleText;//规则文本
        private String status; //检查是否通过
        private String imageUrl;//图片地址
        private int required; //是否必须遵守
        private int deleteFlag;//逻辑删标识
        private int imageUpload;//是否必传照片
        private String createUserId;//创建人ID
        private String createTime;//    创建时间
        private String updateUserId;//更新人ID
        private String updateTime;//	 更新时间

        public int getImageUpload() {
            return imageUpload;
        }

        public void setImageUpload(int imageUpload) {
            this.imageUpload = imageUpload;
        }

        public String getRuleId() {
            return ruleId;
        }

        public void setRuleId(String ruleId) {
            this.ruleId = ruleId;
        }

        public String getWarehouseGroupId() {
            return warehouseGroupId;
        }

        public void setWarehouseGroupId(String warehouseGroupId) {
            this.warehouseGroupId = warehouseGroupId;
        }

        public String getRuleText() {
            return ruleText;
        }

        public void setRuleText(String ruleText) {
            this.ruleText = ruleText;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public int getRequired() {
            return required;
        }

        public void setRequired(int required) {
            this.required = required;
        }

        public int getDeleteFlag() {
            return deleteFlag;
        }

        public void setDeleteFlag(int deleteFlag) {
            this.deleteFlag = deleteFlag;
        }

        public String getCreateUserId() {
            return createUserId;
        }

        public void setCreateUserId(String createUserId) {
            this.createUserId = createUserId;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getUpdateUserId() {
            return updateUserId;
        }

        public void setUpdateUserId(String updateUserId) {
            this.updateUserId = updateUserId;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }
    }
}
