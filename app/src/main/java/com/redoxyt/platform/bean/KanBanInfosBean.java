package com.redoxyt.platform.bean;

import java.util.List;

/**
 * Created by zz.
 * description:
 */

public class KanBanInfosBean {
    int total;
    List<KanBanStateInfosBean> list;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<KanBanStateInfosBean> getList() {
        return list;
    }

    public void setList(List<KanBanStateInfosBean> list) {
        this.list = list;
    }
}
