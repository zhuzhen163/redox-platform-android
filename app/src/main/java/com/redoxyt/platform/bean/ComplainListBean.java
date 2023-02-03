package com.redoxyt.platform.bean;


import java.util.List;

public class ComplainListBean {

    private List<ComplaintBean> list;
    private int total;

    public List<ComplaintBean> getList() {
        return list;
    }

    public void setList(List<ComplaintBean> list) {
        this.list = list;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}

