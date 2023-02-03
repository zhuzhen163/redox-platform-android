package com.redoxyt.platform.bean;

import java.io.Serializable;

/**
 * Created by zz
 * 经纬度上报
 */

public class AddressMessageBean implements Serializable{


    /**
     * create_time : 2020-02-20T11:42:19.568Z
     * driver_name : string
     * last_update_time : 2020-02-20T11:42:19.568Z
     * last_user_address : string
     * last_user_lat : 0
     * last_user_lon : 0
     * rotation_time : string
     * update_time : 2020-02-20T11:42:19.568Z
     * user_address : string
     * user_id : 0
     * user_lat : 0
     * user_lon : 0
     * user_mobile : string
     */

    private int rotation_time;
    private String user_address;
    private double user_lat;
    private double user_lon;

    public int getRotation_time() {
        return rotation_time;
    }

    public void setRotation_time(int rotation_time) {
        this.rotation_time = rotation_time;
    }

    public String getUser_address() {
        return user_address;
    }

    public void setUser_address(String user_address) {
        this.user_address = user_address;
    }

    public double getUser_lat() {
        return user_lat;
    }

    public void setUser_lat(double user_lat) {
        this.user_lat = user_lat;
    }

    public double getUser_lon() {
        return user_lon;
    }

    public void setUser_lon(double user_lon) {
        this.user_lon = user_lon;
    }
}
