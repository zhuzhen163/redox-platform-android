package com.redoxyt.platform.uitl;

import com.baidu.location.BDLocation;

public interface OnLocationSuccess {
    void getBdLocationSuccess(BDLocation bdLocation);

    void getBdLocationFailed(String desc);
}
