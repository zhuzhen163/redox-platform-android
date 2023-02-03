package com.redoxyt.platform.bean;

import java.util.List;

/**
 * Created by zz.
 * description: 统计历史预约数
 */

public class ReservationHistoryBean {
    private String total;//总预约数
    private String rushNumber;//紧急预约数
    private int finishNumber;//已完成预约数
    private int normalNumber;//正常预约数
    private double finishedPercent;//历史达成率
    private float todayTotal;//今日总预约数
    private int todayFinishedNumber;//今日已完成
    private int todayUnfinishedNumber;//今日未完成
    private List<String>  lastMonthDateList;
    private List<Integer> lastMonthFinishedList;
    private List<Integer> lastMonthTotalList;
    private int maximum;//单日最大预约数

    public int getNormalNumber() {
        return normalNumber;
    }

    public void setNormalNumber(int normalNumber) {
        this.normalNumber = normalNumber;
    }

    public int getMaximum() {
        return maximum;
    }

    public void setMaximum(int maximum) {
        this.maximum = maximum;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getRushNumber() {
        return rushNumber;
    }

    public void setRushNumber(String rushNumber) {
        this.rushNumber = rushNumber;
    }

    public int getFinishNumber() {
        return finishNumber;
    }

    public void setFinishNumber(int finishNumber) {
        this.finishNumber = finishNumber;
    }

    public double getFinishedPercent() {
        return finishedPercent;
    }

    public void setFinishedPercent(double finishedPercent) {
        this.finishedPercent = finishedPercent;
    }

    public float getTodayTotal() {
        return todayTotal;
    }

    public void setTodayTotal(float todayTotal) {
        this.todayTotal = todayTotal;
    }

    public int getTodayFinishedNumber() {
        return todayFinishedNumber;
    }

    public void setTodayFinishedNumber(int todayFinishedNumber) {
        this.todayFinishedNumber = todayFinishedNumber;
    }

    public int getTodayUnfinishedNumber() {
        return todayUnfinishedNumber;
    }

    public void setTodayUnfinishedNumber(int todayUnfinishedNumber) {
        this.todayUnfinishedNumber = todayUnfinishedNumber;
    }

    public List<String> getLastMonthDateList() {
        return lastMonthDateList;
    }

    public void setLastMonthDateList(List<String> lastMonthDateList) {
        this.lastMonthDateList = lastMonthDateList;
    }

    public List<Integer> getLastMonthFinishedList() {
        return lastMonthFinishedList;
    }

    public void setLastMonthFinishedList(List<Integer> lastMonthFinishedList) {
        this.lastMonthFinishedList = lastMonthFinishedList;
    }

    public List<Integer> getLastMonthTotalList() {
        return lastMonthTotalList;
    }

    public void setLastMonthTotalList(List<Integer> lastMonthTotalList) {
        this.lastMonthTotalList = lastMonthTotalList;
    }
}
