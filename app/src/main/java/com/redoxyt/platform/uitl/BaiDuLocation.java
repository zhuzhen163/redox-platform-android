package com.redoxyt.platform.uitl;

import android.content.Context;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.baidu.mapapi.model.LatLng;


/**
 * description:
 * author: SHANG
 * date:2017/5/27 16:50
 * 百度定位工具类
 */
public class BaiDuLocation {
    LocationClient client;// 发起定位请求的”客户端“
    private MyLocationListener listener;
    public LatLng myPosition;// 自己的位置
    LocationClientOption option;
    Context context;

    public BaiDuLocation(Context context) {
        this.context = context;
    }

    public void getMyPosition(int span, OnLocationSuccess lisener) {
        if (client == null) {
            client = new LocationClient(context);
        }
//        stopLocation();
        if (option == null) {
            option = new LocationClientOption();
        }
        option.setLocationMode(LocationMode.Hight_Accuracy);// 可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");// 可选，默认gcj02，设置返回的定位结果坐标系
        option.setScanSpan(span);// 可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);// 可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);// 可选，默认false,设置是否使用gps
        option.setOpenAutoNotifyMode();
        option.setLocationNotify(true);// 可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
//        option.setIsNeedLocationDescribe(true);// 可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
       option.setIsNeedLocationPoiList(true);// 可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(true);// 可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);// 可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);// 可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        client.setLocOption(option);

        if (listener == null) {
            listener = new MyLocationListener(lisener);
        }
        client.registerLocationListener(listener);
        startlocation();
        //0：离线定位请求成功 1:service没有启动 2：无监听函数 6：两次请求时间太短
        //	Log.e("start","1启动结果"+client.requestLocation());
//		final Handler handler=new Handler();
//		final Runnable runnable=new Runnable() {
//			@Override
//			public void run() {
//					client.start();
//				if(client.requestLocation()==1){
//					Log.e("start","2启动结果"+client.requestLocation());
//					handler.postDelayed(this, 1000*10);
//				}
//			}
//		};
//		if(client.requestLocation()==1){
//			Log.e("start","3启动结果"+client.requestLocation());
//			handler.postDelayed(runnable, 1000*10);
//		}
    }

    class MyLocationListener implements BDLocationListener {
        OnLocationSuccess lisener;

        public MyLocationListener(OnLocationSuccess lisener) {
            // TODO Auto-generated constructor stub
            this.lisener = lisener;
        }

        @Override
        public void onReceiveLocation(BDLocation result) {
            int type = result.getLocType();
            double lat = 0;
            double lng = 0;
            if (result != null) {
                lisener.getBdLocationSuccess(result);
            } else {
                lisener.getBdLocationFailed("定位失败");
            }
            int errorCode = result.getLocType();
//            if (!String.valueOf(result.getLatitude()).equals("4.9E-324") && !String.valueOf(result.getLongitude()).equals("4.9E-324")) {
//                lisener.getBdLocationSuccess(result);
//            } else {
//                lisener.getBdLocationFailed("定位失败");
//            }

//            if (!String.valueOf(result.getLatitude()).equals("4.9E-324"))
//                if (type == 61 || type == 66 || type == 161) {
//                    //Toast.makeText(ApplicationLike.context,"定位--次",Toast.LENGTH_SHORT).show();
//                    // 定位成功
//
//                    Logger.e(result.toString());
//                    if (!String.valueOf(result.getLatitude()).equals("4.9E-324") && !String.valueOf(result.getLongitude()).equals("4.9E-324")) {
//                        lisener.getBdLocation(result);
//                    } else {
//                    }
//
//                } else {
//                    // 定位失败
//                    lat = 39.909604;
//                    lng = 116.397228;
//                }

        }


    }

    public void addListener(int span, OnLocationSuccess lisener) {
        getMyPosition(span, lisener);
    }

    public void startlocation() {
        if (client != null && !client.isStarted()) {
            client.start();
        }else if (client != null &&client.isStarted()){
            client.requestLocation();
        }
    }

    public void stopLocation() {
        if (client != null && client.isStarted()) {
            client.stop();
        }
    }

}
