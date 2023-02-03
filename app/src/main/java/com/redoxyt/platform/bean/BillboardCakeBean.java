package com.redoxyt.platform.bean;


/**
 * Created by zz.
 * description: 管理看板
 */

public class BillboardCakeBean {

    private String platformName;//	月台名称
    private String finishedNumber;//完成数量
    private String reservationTotal;//预约数量
    private String achievementRate;// 达成率

    public String getPlatformName() {
        return platformName;
    }

    public void setPlatformName(String platformName) {
        this.platformName = platformName;
    }

    public String getFinishedNumber() {
        return finishedNumber;
    }

    public void setFinishedNumber(String finishedNumber) {
        this.finishedNumber = finishedNumber;
    }

    public String getReservationTotal() {
        return reservationTotal;
    }

    public void setReservationTotal(String reservationTotal) {
        this.reservationTotal = reservationTotal;
    }

    public String getAchievementRate() {
        return achievementRate;
    }

    public void setAchievementRate(String achievementRate) {
        this.achievementRate = achievementRate;
    }
}
