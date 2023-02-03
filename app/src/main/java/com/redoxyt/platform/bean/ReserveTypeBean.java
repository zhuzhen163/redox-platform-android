package com.redoxyt.platform.bean;


/**
 * 看板
 */
public class ReserveTypeBean {
    private String total;
    private float inStorageNumber;
    private float outStorageNumber;
    private int inFinishedNumber;//入库已完成
    private String inUnfinishedNumber;//入库未完成
    private int outFinishedNumber;//出库已完成
    private String outUnfinishedNumber;//出库未完成
    private String totalRate;//总预约率

    public String getTotalRate() {
        return totalRate;
    }

    public void setTotalRate(String totalRate) {
        this.totalRate = totalRate;
    }

    public int getInFinishedNumber() {
        return inFinishedNumber;
    }

    public void setInFinishedNumber(int inFinishedNumber) {
        this.inFinishedNumber = inFinishedNumber;
    }

    public String getInUnfinishedNumber() {
        return inUnfinishedNumber;
    }

    public void setInUnfinishedNumber(String inUnfinishedNumber) {
        this.inUnfinishedNumber = inUnfinishedNumber;
    }

    public int getOutFinishedNumber() {
        return outFinishedNumber;
    }

    public void setOutFinishedNumber(int outFinishedNumber) {
        this.outFinishedNumber = outFinishedNumber;
    }

    public String getOutUnfinishedNumber() {
        return outUnfinishedNumber;
    }

    public void setOutUnfinishedNumber(String outUnfinishedNumber) {
        this.outUnfinishedNumber = outUnfinishedNumber;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public float getInStorageNumber() {
        return inStorageNumber;
    }

    public void setInStorageNumber(float inStorageNumber) {
        this.inStorageNumber = inStorageNumber;
    }

    public float getOutStorageNumber() {
        return outStorageNumber;
    }

    public void setOutStorageNumber(float outStorageNumber) {
        this.outStorageNumber = outStorageNumber;
    }
}
