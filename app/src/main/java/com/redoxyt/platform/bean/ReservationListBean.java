package com.redoxyt.platform.bean;

import java.util.List;

/**
 *
 */
public class ReservationListBean {

    private int total;

    List<ReservationBean> reservationList;

    private ReservationBean reservation;//司机绿码

    public ReservationBean getReservation() {
        return reservation;
    }

    public void setReservation(ReservationBean reservation) {
        this.reservation = reservation;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<ReservationBean> getReservationList() {
        return reservationList;
    }

    public void setReservationList(List<ReservationBean> reservationList) {
        this.reservationList = reservationList;
    }
}
