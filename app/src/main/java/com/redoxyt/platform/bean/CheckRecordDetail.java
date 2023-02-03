package com.redoxyt.platform.bean;

/**
 * Created by zz.
 * description:
 */

public class CheckRecordDetail {
    private String warehouseRuleId;//仓库规则ID
    private String ruleText;//规则文本
    private int required;//是否必须遵守
    private String status;//是否通过，0:未通过;1:通过
    private String imageUrl;//图片地址
    private int imageUpload;//是否必传照片

    public int getImageUpload() {
        return imageUpload;
    }

    public void setImageUpload(int imageUpload) {
        this.imageUpload = imageUpload;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getWarehouseRuleId() {
        return warehouseRuleId;
    }

    public void setWarehouseRuleId(String warehouseRuleId) {
        this.warehouseRuleId = warehouseRuleId;
    }

    public String getRuleText() {
        return ruleText;
    }

    public void setRuleText(String ruleText) {
        this.ruleText = ruleText;
    }

    public int getRequired() {
        return required;
    }

    public void setRequired(int required) {
        this.required = required;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
