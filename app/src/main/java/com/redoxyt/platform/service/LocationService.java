package com.redoxyt.platform.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.redoxyt.platform.bean.AddressMessageBean;

import utils.ConfigUtils;


/**
 * Created by zz.
 * description:
 */

public class LocationService extends Service {

    private static final String TAG = "LocationService";
    public LocationClient locationClient;
    LocationClientOption locationOption;
    private int locationNum = 0;
    @Override
    public void onCreate() {
        initLocationOption();
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new Binder();
    }

    public class Binder extends android.os.Binder {
         public LocationService getService() {
             return LocationService.this;
         }
     }

    private void initLocationOption() {
        //定位服务的客户端。宿主程序在客户端声明此类，并调用，目前只支持在主线程中启动
        locationClient = new LocationClient(getApplicationContext());
        //声明LocationClient类实例并配置定位参数
        locationOption = new LocationClientOption();
        MyLocationListener myLocationListener = new MyLocationListener();
        //注册监听函数
        locationClient.registerLocationListener(myLocationListener);
        //可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        locationOption.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //可选，默认gcj02，设置返回的定位结果坐标系，如果配合百度地图使用，建议设置为bd09ll;
        locationOption.setCoorType("bd09ll");
        //可选，默认0，即仅定位一次，设置发起连续定位请求的间隔需要大于等于1000ms才是有效的
        locationOption.setScanSpan(ConfigUtils.getLocTime());
        //可选，设置是否需要地址信息，默认不需要
        locationOption.setIsNeedAddress(true);
        //可选，设置是否需要设备方向结果
        locationOption.setNeedDeviceDirect(true);
        //可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        locationOption.setLocationNotify(false);
        //可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        locationOption.setIgnoreKillProcess(true);
        //可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        locationOption.setIsNeedLocationDescribe(true);
        //可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        locationOption.setIsNeedLocationPoiList(true);
        //可选，默认false，设置是否收集CRASH信息，默认收集
        locationOption.SetIgnoreCacheException(false);
        //可选，默认false，设置是否开启Gps定位
        locationOption.setOpenGps(true);
        //可选，默认false，设置定位时是否需要海拔信息，默认不需要，除基础定位版本都可用
        locationOption.setIsNeedAltitude(true);
        //设置打开自动回调位置模式，该开关打开后，期间只要定位SDK检测到位置变化就会主动回调给开发者，该模式下开发者无需再关心定位间隔是多少，定位SDK本身发现位置变化就会及时回调给开发者
//        locationOption.setOpenAutoNotifyMode();
        //设置打开自动回调位置模式，该开关打开后，期间只要定位SDK检测到位置变化就会主动回调给开发者
        //        locationOption.setOpenAutoNotifyMode(3000, 1, LocationClientOption.LOC_SENSITIVITY_HIGHT);
        //需将配置好的LocationClientOption对象，通过setLocOption方法传递给LocationClient对象使用
        locationClient.setLocOption(locationOption);
        //开始定位
        locationClient.start();
    }

    /**
     * 实现定位回调
     */
    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            String addrStr = location.getAddrStr();
            if (addrStr != null){
                locationNum = 0;
                //获取纬度信息
                double latitude = location.getLatitude();
                //获取经度信息
                double longitude = location.getLongitude();
                if (String.valueOf(longitude).equals("4.9E-324")){
                    latitude = 0.0;
                    longitude = 0.0;
                }
                //获取经纬度坐标类型，以LocationClientOption中设置过的坐标类型为准
                String coorType = location.getCoorType();
                //获取定位类型、定位错误返回码，具体信息可参照类参考中BDLocation类中的说明
                int errorCode = location.getLocType();
                locationOption.setScanSpan(ConfigUtils.getLocTime());

                AddressMessageBean addressMessageBean = new AddressMessageBean();
                addressMessageBean.setUser_lat(latitude);
                addressMessageBean.setUser_lon(longitude);
                addressMessageBean.setUser_address(addrStr);
                if (callback != null){
                    callback.onDataChange(addressMessageBean);
                }
//                try {
//                    if (BuildConfig.BUILD_TYPE.equals("debug"))
//                    TXTManager.saveAction(TimeUtil.getNowStr()+",精度："+longitude+"纬度："+latitude+"，地址："+addrStr,"locationTXT");
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
            }else {
                try {
                    if (locationClient != null && locationClient.isStarted() && locationNum<5) {
                        locationClient.requestLocation();
                        locationNum++;
                    }else {
                        locationNum = 0;
                        AddressMessageBean addressMessageBean = new AddressMessageBean();
                        addressMessageBean.setUser_lat(0.0);
                        addressMessageBean.setUser_lon(0.0);
                        addressMessageBean.setUser_address("");
                        if (callback != null){
                            callback.onDataChange(addressMessageBean);
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    public interface Callback {
        void onDataChange(AddressMessageBean bean);
    }
    private Callback callback;

    public Callback getCallback() {
        return callback;
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (locationClient != null && locationClient.isStarted()) {
            locationClient.stop();
        }
    }
}
