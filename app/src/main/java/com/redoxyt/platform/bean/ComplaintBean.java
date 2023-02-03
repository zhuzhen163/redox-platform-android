package com.redoxyt.platform.bean;



public class ComplaintBean {


    /**
     * createUserId : 138
     * createTime : 2020-03-07 14:21:15
     * updateUserId : null
     * updateTime : null
     * beginTime : null
     * endTime : null
     * version : null
     * complainId : 1
     * complainUserId : 138
     * complainUserName : 韩涛
     * complainUserMobile : 15948356651
     * complainClass : 1
     * complainType : 1
     * complainContent : 投诉测试
     * complainResult : null
     * complainFeedback : null
     * complainOperateId : null
     * complainOperater : null
     * complainStatus : 1
     */

    private int createUserId;
    private String createTime;
    private int complainId;
    private int complainUserId;
    private String complainUserName;
    private String complainUserMobile;
    private int complainClass;
    private String complainClassName;
    private int complainType;
    private String complainContent;
    private int complainStatus;
    private String complainTypeName;
    private String complainResult;
    private String updateTime;
    private int complainFeedbackFlag;

    public int getComplainFeedbackFlag() {
        return complainFeedbackFlag;
    }

    public void setComplainFeedbackFlag(int complainFeedbackFlag) {
        this.complainFeedbackFlag = complainFeedbackFlag;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getComplainResult() {
        return complainResult;
    }

    public void setComplainResult(String complainResult) {
        this.complainResult = complainResult;
    }

    public String getComplainClassName() {
        return complainClassName;
    }

    public void setComplainClassName(String complainClassName) {
        this.complainClassName = complainClassName;
    }

    public String getComplainTypeName() {
        return complainTypeName;
    }

    public void setComplainTypeName(String complainTypeName) {
        this.complainTypeName = complainTypeName;
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

    public int getComplainId() {
        return complainId;
    }

    public void setComplainId(int complainId) {
        this.complainId = complainId;
    }

    public int getComplainUserId() {
        return complainUserId;
    }

    public void setComplainUserId(int complainUserId) {
        this.complainUserId = complainUserId;
    }

    public String getComplainUserName() {
        return complainUserName;
    }

    public void setComplainUserName(String complainUserName) {
        this.complainUserName = complainUserName;
    }

    public String getComplainUserMobile() {
        return complainUserMobile;
    }

    public void setComplainUserMobile(String complainUserMobile) {
        this.complainUserMobile = complainUserMobile;
    }

    public int getComplainClass() {
        return complainClass;
    }

    public void setComplainClass(int complainClass) {
        this.complainClass = complainClass;
    }

    public int getComplainType() {
        return complainType;
    }

    public void setComplainType(int complainType) {
        this.complainType = complainType;
    }

    public String getComplainContent() {
        return complainContent;
    }

    public void setComplainContent(String complainContent) {
        this.complainContent = complainContent;
    }

    public int getComplainStatus() {
        return complainStatus;
    }

    public void setComplainStatus(int complainStatus) {
        this.complainStatus = complainStatus;
    }
}

