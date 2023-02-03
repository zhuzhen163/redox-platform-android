package com.redoxyt.platform.bean;


/**
 * Created by zz.
 * description: 管理看板
 */

public class BillboardBean {

    private String reserveDate;//日期
    private String reservationTotal;//总预约数
    private String finishedTotal;//总预约完成数
    private String inReservationNumber;//入库预约数
    private String inReservationFinishedNumber;//入库预约完成数
    private String outReservationNumber;//出库预约数
    private String outReservationFinishedNumber;//出库预约完成数
    private String achievementRateTotal;//总达成率
    private String inAchievementRate;//入库达成率
    private String outAchievementRate;//出库达成率

    public String getReserveDate() {
        return reserveDate;
    }

    public void setReserveDate(String reserveDate) {
        this.reserveDate = reserveDate;
    }

    public String getReservationTotal() {
        return reservationTotal;
    }

    public void setReservationTotal(String reservationTotal) {
        this.reservationTotal = reservationTotal;
    }

    public String getFinishedTotal() {
        return finishedTotal;
    }

    public void setFinishedTotal(String finishedTotal) {
        this.finishedTotal = finishedTotal;
    }

    public String getInReservationNumber() {
        return inReservationNumber;
    }

    public void setInReservationNumber(String inReservationNumber) {
        this.inReservationNumber = inReservationNumber;
    }

    public String getInReservationFinishedNumber() {
        return inReservationFinishedNumber;
    }

    public void setInReservationFinishedNumber(String inReservationFinishedNumber) {
        this.inReservationFinishedNumber = inReservationFinishedNumber;
    }

    public String getOutReservationNumber() {
        return outReservationNumber;
    }

    public void setOutReservationNumber(String outReservationNumber) {
        this.outReservationNumber = outReservationNumber;
    }

    public String getOutReservationFinishedNumber() {
        return outReservationFinishedNumber;
    }

    public void setOutReservationFinishedNumber(String outReservationFinishedNumber) {
        this.outReservationFinishedNumber = outReservationFinishedNumber;
    }

    public String getAchievementRateTotal() {
        return achievementRateTotal;
    }

    public void setAchievementRateTotal(String achievementRateTotal) {
        this.achievementRateTotal = achievementRateTotal;
    }

    public String getInAchievementRate() {
        return inAchievementRate;
    }

    public void setInAchievementRate(String inAchievementRate) {
        this.inAchievementRate = inAchievementRate;
    }

    public String getOutAchievementRate() {
        return outAchievementRate;
    }

    public void setOutAchievementRate(String outAchievementRate) {
        this.outAchievementRate = outAchievementRate;
    }
}
