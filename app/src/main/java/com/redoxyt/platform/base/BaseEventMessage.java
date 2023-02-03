package com.redoxyt.platform.base;

public class BaseEventMessage {
    private boolean isRefresh;//判断是否刷新
    private String str;//看需求
    private Object obj;//可以添加数据

    public BaseEventMessage(boolean isRefresh) {
        this.isRefresh = isRefresh;
    }

    public BaseEventMessage(boolean isRefresh, String str) {
        this.isRefresh = isRefresh;
        this.str = str;
    }

    public BaseEventMessage(boolean isRefresh, String str, Object obj) {
        this.isRefresh = isRefresh;
        this.str = str;
        this.obj = obj;
    }

    public BaseEventMessage(Object obj) {
        this.isRefresh = isRefresh;
        this.str = str;
        this.obj = obj;
    }

    public BaseEventMessage(String str) {
        this.isRefresh = isRefresh;
        this.str = str;
    }

    public BaseEventMessage(String str, Object obj) {
        this.isRefresh = isRefresh;
        this.str = str;
        this.obj = obj;
    }

    public boolean isRefresh() {
        return isRefresh;
    }

    public void setRefresh(boolean refresh) {
        isRefresh = refresh;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    @Override
    public String toString() {
        return "BaseEventMessage{" +
                "isRefresh=" + isRefresh +
                ", str='" + str + '\'' +
                ", obj=" + obj +
                '}';
    }
}
