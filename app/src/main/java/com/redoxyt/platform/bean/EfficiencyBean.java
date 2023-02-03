package com.redoxyt.platform.bean;

import java.util.List;

/**
 * 效率看板
 */
public class EfficiencyBean {

    private String stowageVelocity;//平均装货速度
    private String accumulateStowageVelocity;//累计平均装货速度
    private String stowageVelocityRate;//装货速度变化率
    private String unloadVelocity;//平均卸货速度
    private String accumulateUnloadVelocity;//累计平均卸货速度
    private String unloadVelocityRate;//卸货速度变化率
    private List<LoadBean> list;

    public String getStowageVelocity() {
        return stowageVelocity;
    }

    public void setStowageVelocity(String stowageVelocity) {
        this.stowageVelocity = stowageVelocity;
    }

    public String getAccumulateStowageVelocity() {
        return accumulateStowageVelocity;
    }

    public void setAccumulateStowageVelocity(String accumulateStowageVelocity) {
        this.accumulateStowageVelocity = accumulateStowageVelocity;
    }

    public String getStowageVelocityRate() {
        return stowageVelocityRate;
    }

    public void setStowageVelocityRate(String stowageVelocityRate) {
        this.stowageVelocityRate = stowageVelocityRate;
    }

    public String getUnloadVelocity() {
        return unloadVelocity;
    }

    public void setUnloadVelocity(String unloadVelocity) {
        this.unloadVelocity = unloadVelocity;
    }

    public String getAccumulateUnloadVelocity() {
        return accumulateUnloadVelocity;
    }

    public void setAccumulateUnloadVelocity(String accumulateUnloadVelocity) {
        this.accumulateUnloadVelocity = accumulateUnloadVelocity;
    }

    public String getUnloadVelocityRate() {
        return unloadVelocityRate;
    }

    public void setUnloadVelocityRate(String unloadVelocityRate) {
        this.unloadVelocityRate = unloadVelocityRate;
    }

    public List<LoadBean> getList() {
        return list;
    }

    public void setList(List<LoadBean> list) {
        this.list = list;
    }

    public class LoadBean{
        private String reserveDate;
        private String stowageRate;
        private String unloadRate;
        private String notWorkRate;
        private String stowageVelocityRate;
        private String unloadVelocityRate;

        public String getReserveDate() {
            return reserveDate;
        }

        public void setReserveDate(String reserveDate) {
            this.reserveDate = reserveDate;
        }

        public String getStowageRate() {
            return stowageRate;
        }

        public void setStowageRate(String stowageRate) {
            this.stowageRate = stowageRate;
        }

        public String getUnloadRate() {
            return unloadRate;
        }

        public void setUnloadRate(String unloadRate) {
            this.unloadRate = unloadRate;
        }

        public String getNotWorkRate() {
            return notWorkRate;
        }

        public void setNotWorkRate(String notWorkRate) {
            this.notWorkRate = notWorkRate;
        }

        public String getStowageVelocityRate() {
            return stowageVelocityRate;
        }

        public void setStowageVelocityRate(String stowageVelocityRate) {
            this.stowageVelocityRate = stowageVelocityRate;
        }

        public String getUnloadVelocityRate() {
            return unloadVelocityRate;
        }

        public void setUnloadVelocityRate(String unloadVelocityRate) {
            this.unloadVelocityRate = unloadVelocityRate;
        }
    }

}
