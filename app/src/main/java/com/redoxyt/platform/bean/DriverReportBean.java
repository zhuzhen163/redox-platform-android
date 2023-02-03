package com.redoxyt.platform.bean;

/**
 * Created by zz.
 * description:
 */

public class DriverReportBean {

    private int flag;//"flag":1,message:"您还未支付!"    or   "flag":2,message:"报道成功!"
    private String message;
    private String time;//时间
    private String queueNum;//排队车辆
    private String platformChargeUnitprice;
    private int workRuleStatus;//作业规则:0:库管扫码作业;1:司机扫码作业;2:库管与司机都可扫码作业
    private int entranceRemind;//0:不提醒;1:提醒
    private String second;//倒计时
    private String platformNumberCar;//最大进场车辆数

    public String getPlatformNumberCar() {
        return platformNumberCar;
    }

    public void setPlatformNumberCar(String platformNumberCar) {
        this.platformNumberCar = platformNumberCar;
    }

    public int getWorkRuleStatus() {
        return workRuleStatus;
    }

    public void setWorkRuleStatus(int workRuleStatus) {
        this.workRuleStatus = workRuleStatus;
    }

    public int getEntranceRemind() {
        return entranceRemind;
    }

    public void setEntranceRemind(int entranceRemind) {
        this.entranceRemind = entranceRemind;
    }

    public String getSecond() {
        return second;
    }

    public void setSecond(String second) {
        this.second = second;
    }

    private String itemDesc;

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }

    public String getPlatformChargeUnitprice() {
        return platformChargeUnitprice;
    }

    public void setPlatformChargeUnitprice(String platformChargeUnitprice) {
        this.platformChargeUnitprice = platformChargeUnitprice;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getQueueNum() {
        return queueNum;
    }

    public void setQueueNum(String queueNum) {
        this.queueNum = queueNum;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
}
