package com.redoxyt.platform.bean;

import java.util.List;

/**
 * Created by zz.
 * description:
 */

public class ManagerKanBanBean {
    int total;
    List<WarehouseCarListBean> list;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<WarehouseCarListBean> getList() {
        return list;
    }

    public void setList(List<WarehouseCarListBean> list) {
        this.list = list;
    }
}
