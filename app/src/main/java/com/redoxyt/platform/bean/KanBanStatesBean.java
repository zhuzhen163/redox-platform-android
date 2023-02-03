package com.redoxyt.platform.bean;

import java.util.List;

/**
 */
public class KanBanStatesBean {

    private int reserveStatus;
    private String reserveStatusName;
    private int countCar;
    private List<KanBanStateInfosBean> kanBanStateInfos;

    public int getCountCar() {
        return countCar;
    }

    public void setCountCar(int countCar) {
        this.countCar = countCar;
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

    public List<KanBanStateInfosBean> getKanBanStateInfos() {
        return kanBanStateInfos;
    }

    public void setKanBanStateInfos(List<KanBanStateInfosBean> kanBanStateInfos) {
        this.kanBanStateInfos = kanBanStateInfos;
    }
}
