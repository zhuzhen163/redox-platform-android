package com.redoxyt.platform.bean;

import java.util.List;

/**
 */
public class WarehouseCarListBean {

    private String platformId;
    private String platformName;
    private List<KanBanStatesBean> kanBanStates;

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

    public List<KanBanStatesBean> getKanBanStates() {
        return kanBanStates;
    }

    public void setKanBanStates(List<KanBanStatesBean> kanBanStates) {
        this.kanBanStates = kanBanStates;
    }
}
