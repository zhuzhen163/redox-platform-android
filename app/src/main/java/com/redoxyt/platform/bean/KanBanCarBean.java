package com.redoxyt.platform.bean;

import java.util.List;

/**
 * 看板
 */
public class KanBanCarBean {

    private String platformId;
    private String platformName;
    private List<Subs> subs;

    public String getPlatformId() {
        return platformId;
    }

    public void setPlatformId(String platformId) {
        this.platformId = platformId;
    }

    public String getPlatformName() {
        return platformName;
    }

    public void setPlatformName(String platformName) {
        this.platformName = platformName;
    }

    public List<Subs> getSubs() {
        return subs;
    }

    public void setSubs(List<Subs> subs) {
        this.subs = subs;
    }

    public class Subs{
        private String carCode;
        private int carState;

        public String getCarCode() {
            return carCode;
        }

        public void setCarCode(String carCode) {
            this.carCode = carCode;
        }

        public int getCarState() {
            return carState;
        }

        public void setCarState(int carState) {
            this.carState = carState;
        }
    }
}
