package com.redoxyt.platform.bean;

import java.util.List;

/**
 * 预约时间
 */
public class ReservationTimeBean {


    /**
     * yyyyMMdd : 2020-09-07
     * mmdd : 09-07
     * week : 周一
     * reserveStatus : 0
     * reserveStatusName : 可预约
     * subs : [{"platformTimezoneId":115,"startTime":"18:00:00","endTime":"19:00:00","startEndTime":"18:00:00-19:00:00","classNum":3}]
     */

    private String yyyyMMdd;
    private String mmdd;
    private String week;
    private int reserveStatus;
    private String reserveStatusName;
    private String platformRuleId;
    private List<SubsBean> subs;
    private boolean isCheck;

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public String getPlatformRuleId() {
        return platformRuleId;
    }

    public void setPlatformRuleId(String platformRuleId) {
        this.platformRuleId = platformRuleId;
    }

    public String getYyyyMMdd() {
        return yyyyMMdd;
    }

    public void setYyyyMMdd(String yyyyMMdd) {
        this.yyyyMMdd = yyyyMMdd;
    }

    public String getMmdd() {
        return mmdd;
    }

    public void setMmdd(String mmdd) {
        this.mmdd = mmdd;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public int getReserveStatus() {
        return reserveStatus;
    }

    public void setReserveStatus(int reserveStatus) {
        this.reserveStatus = reserveStatus;
    }

    public String getReserveStatusName() {
        return reserveStatusName;
    }

    public void setReserveStatusName(String reserveStatusName) {
        this.reserveStatusName = reserveStatusName;
    }

    public List<SubsBean> getSubs() {
        return subs;
    }

    public void setSubs(List<SubsBean> subs) {
        this.subs = subs;
    }

    public class SubsBean {
        /**
         * platformTimezoneId : 115
         * startTime : 18:00:00
         * endTime : 19:00:00
         * startEndTime : 18:00:00-19:00:00
         * classNum : 3
         */

        private int platformTimezoneId;
        private String startTime;
        private String endTime;
        private String startEndTime;
        private int classNum;

        public int getPlatformTimezoneId() {
            return platformTimezoneId;
        }

        public void setPlatformTimezoneId(int platformTimezoneId) {
            this.platformTimezoneId = platformTimezoneId;
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

        public int getClassNum() {
            return classNum;
        }

        public void setClassNum(int classNum) {
            this.classNum = classNum;
        }
    }
}
