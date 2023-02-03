package com.redoxyt.platform.bean;

import java.util.List;

/**
 * Created by zz.
 * description: 管理看板
 */

public class ManagementBillboardBean {
    private String reservationHistoryNumber;//历史正常预约数
    private String urgentReservationHistoryNumber;//历史紧急预约数
    private String achievementRateHistoryNumber;//	历史达成率
    private List<BillboardBean> list;

    public String getReservationHistoryNumber() {
        return reservationHistoryNumber;
    }

    public void setReservationHistoryNumber(String reservationHistoryNumber) {
        this.reservationHistoryNumber = reservationHistoryNumber;
    }

    public String getUrgentReservationHistoryNumber() {
        return urgentReservationHistoryNumber;
    }

    public void setUrgentReservationHistoryNumber(String urgentReservationHistoryNumber) {
        this.urgentReservationHistoryNumber = urgentReservationHistoryNumber;
    }

    public String getAchievementRateHistoryNumber() {
        return achievementRateHistoryNumber;
    }

    public void setAchievementRateHistoryNumber(String achievementRateHistoryNumber) {
        this.achievementRateHistoryNumber = achievementRateHistoryNumber;
    }

    public List<BillboardBean> getList() {
        return list;
    }

    public void setList(List<BillboardBean> list) {
        this.list = list;
    }
}
