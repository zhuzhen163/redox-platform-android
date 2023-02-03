package com.redoxyt.platform.bean;

import java.util.List;

/**
 *
 */
public class WarehouseListBean {

    private int total;

    List<ReservationBean> list;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<ReservationBean> getList() {
        return list;
    }

    public void setList(List<ReservationBean> list) {
        this.list = list;
    }
}
