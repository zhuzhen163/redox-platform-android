package com.redoxyt.platform.bean;

import java.util.List;

/**
 * 账单
 */
public class PlatformRuleBean {

    List<PlatformTimezoneExt> platformTimezoneList;

    public List<PlatformTimezoneExt> getPlatformTimezoneList() {
        return platformTimezoneList;
    }

    public void setPlatformTimezoneList(List<PlatformTimezoneExt> platformTimezoneList) {
        this.platformTimezoneList = platformTimezoneList;
    }

    public class PlatformTimezoneExt{
        float classNum;//时段最大车数
        int reservationNum;//时段已预约车数
        String startTime;//开始时间

        public float getClassNum() {
            return classNum;
        }

        public void setClassNum(float classNum) {
            this.classNum = classNum;
        }

        public int getReservationNum() {
            return reservationNum;
        }

        public void setReservationNum(int reservationNum) {
            this.reservationNum = reservationNum;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }
    }
}
