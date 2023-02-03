package com.redoxyt.platform.bean;

import java.util.List;

/**
 * 看板
 */
public class KanBanCarListBean {
    private int total;
    List<KanBanCarBean> list;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<KanBanCarBean> getList() {
        return list;
    }

    public void setList(List<KanBanCarBean> list) {
        this.list = list;
    }
}
