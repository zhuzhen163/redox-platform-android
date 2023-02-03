package com.redoxyt.platform.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.redoxyt.platform.BuildConfig;
import com.redoxyt.platform.FactoryApplication;
import com.redoxyt.platform.R;
import com.redoxyt.platform.activity.AdminQueryPaymentActivity;
import com.redoxyt.platform.activity.AdminReservationListActivity;
import com.redoxyt.platform.activity.CarListActivity;
import com.redoxyt.platform.activity.ComplaintActivity;
import com.redoxyt.platform.activity.FleetHistoryFindActivity;
import com.redoxyt.platform.activity.FleetRegisterActivity;
import com.redoxyt.platform.activity.HistoryFindActivity;
import com.redoxyt.platform.activity.InfoAuthActivity;
import com.redoxyt.platform.activity.InspectionRecordActivity;
import com.redoxyt.platform.activity.LoginActivity;
import com.redoxyt.platform.activity.MainActivity;
import com.redoxyt.platform.activity.ManagerWarehouseActivity;
import com.redoxyt.platform.activity.MyBillActivity;
import com.redoxyt.platform.activity.ReservationRecordActivity;
import com.redoxyt.platform.activity.TodayFindActivity;
import com.redoxyt.platform.activity.UserWarehouseActivity;
import com.redoxyt.platform.base.BaseFragment;
import com.redoxyt.platform.bean.UserBean;
import com.redoxyt.platform.uitl.CreditPermissionUtil;

import butterknife.BindView;
import butterknife.OnClick;
import http.callback.QueryVoDialogCallback;
import http.model.QueryVoLzyResponse;
import http.utils.BaseUrl;
import util.PermissionsUtils;
import utils.ConfigUtils;
import utils.SpUtils;

/**
 * 我的
 */

public class MineFragment extends BaseFragment {

    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_phone)
    TextView tv_phone;
    @BindView(R.id.ll_carList)
    LinearLayout ll_carList;//车辆列表
    @BindView(R.id.ll_bill)
    LinearLayout ll_bill;//我的账单
    @BindView(R.id.ll_reservation)
    LinearLayout ll_reservation;//我的预约
    @BindView(R.id.ll_changeMsg)
    LinearLayout ll_changeMsg;//修改个人信息
    @BindView(R.id.ll_inspectionRecord)
    LinearLayout ll_inspectionRecord;//查看检查记录
    @BindView(R.id.ll_historyFind)
    LinearLayout ll_historyFind;
    @BindView(R.id.ll_fleet)
    LinearLayout ll_fleet;
    @BindView(R.id.ll_feedback)
    LinearLayout ll_feedback;
    @BindView(R.id.tv_unLogin)
    TextView tv_unLogin;
    @BindView(R.id.ll_warehouse)
    LinearLayout ll_warehouse;
    @BindView(R.id.tv_version)
    TextView tv_version;
    @BindView(R.id.ll_todayFind)
    LinearLayout ll_todayFind;
    @BindView(R.id.tv_fleetId)
    TextView tv_fleetId;
    @BindView(R.id.ll_build_reservation_fk)
    LinearLayout ll_build_reservation_fk;//紧急预约查询与付款
    @BindView(R.id.ll_reservationListFind)
    LinearLayout ll_reservationListFind;
    @BindView(R.id.tv_warehouse)
    TextView tv_warehouse;
    @BindView(R.id.dev)
    TextView dev;
    @BindView(R.id.guangguang)
    TextView guangguang;
    @BindView(R.id.rl_debug_eve)
    RelativeLayout rl_debug_eve;

    private String groupId;

    @Override
    protected void initData() {
        String type = "";
        if (BuildConfig.BUILD_TYPE.equals("debug")){
            type = "--debug";
        }
        tv_version.setText("版本号:"+ BuildConfig.VERSION_NAME+type);

        int userFlag = ConfigUtils.getUserFlag();
        if (userFlag == 4){//车队长
            ll_carList.setVisibility(View.GONE);
            ll_bill.setVisibility(View.GONE);
            ll_reservation.setVisibility(View.GONE);
            ll_inspectionRecord.setVisibility(View.GONE);
            ll_fleet.setVisibility(View.GONE);
            ll_warehouse.setVisibility(View.VISIBLE);
            tv_fleetId.setVisibility(View.VISIBLE);
            tv_fleetId.setText("车队ID:"+ConfigUtils.getGroupId());
        }else if (userFlag == 8 || userFlag == 12){//8管理员，12子管理员
            ll_carList.setVisibility(View.GONE);
            ll_bill.setVisibility(View.GONE);
            ll_reservation.setVisibility(View.GONE);
            ll_inspectionRecord.setVisibility(View.GONE);
            ll_historyFind.setVisibility(View.GONE);
            ll_fleet.setVisibility(View.GONE);
            ll_changeMsg.setVisibility(View.GONE);
            ll_build_reservation_fk.setVisibility(View.VISIBLE);
            ll_reservationListFind.setVisibility(View.VISIBLE);
            ll_warehouse.setVisibility(View.VISIBLE);
            tv_warehouse.setText("我管理的仓库");
        }else if(userFlag == 9){//库管员
            ll_carList.setVisibility(View.GONE);
            ll_bill.setVisibility(View.GONE);
            ll_reservation.setVisibility(View.GONE);
            ll_inspectionRecord.setVisibility(View.GONE);
            ll_fleet.setVisibility(View.GONE);
            ll_changeMsg.setVisibility(View.GONE);
            ll_todayFind.setVisibility(View.VISIBLE);
        }else if (userFlag == 10){//门卫
            ll_carList.setVisibility(View.GONE);
            ll_bill.setVisibility(View.GONE);
            ll_reservation.setVisibility(View.GONE);
            ll_historyFind.setVisibility(View.GONE);
            ll_fleet.setVisibility(View.GONE);
            ll_changeMsg.setVisibility(View.GONE);
        }else if (userFlag == 5){//车队司机
            ll_warehouse.setVisibility(View.VISIBLE);
            ll_inspectionRecord.setVisibility(View.GONE);
            ll_historyFind.setVisibility(View.GONE);
            ll_fleet.setVisibility(View.GONE);
        }else {//普通司机
            ll_warehouse.setVisibility(View.VISIBLE);
            ll_inspectionRecord.setVisibility(View.GONE);
            ll_historyFind.setVisibility(View.GONE);
        }
        if(BuildConfig.DEBUG) {
            rl_debug_eve.setVisibility(View.VISIBLE);
        }else {
            rl_debug_eve.setVisibility(View.GONE);
        }
        dev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("环境已经切换至dev");
                BaseUrl.YT_Base="http://apidev.changchangbao.com/";
            }
        });
        guangguang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("环境已经切换至鲁光光");
                BaseUrl.YT_Base="http://192.168.31.102:59200/";
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        myInfo();
    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_mine;
    }

    @OnClick({R.id.ll_carList,R.id.ll_bill,R.id.ll_reservation,R.id.ll_changeMsg,R.id.ll_feedback,R.id.tv_unLogin,
    R.id.ll_fleet,R.id.ll_historyFind,R.id.ll_inspectionRecord,R.id.ll_warehouse,R.id.ll_todayFind,R.id.ll_build_reservation_fk,
    R.id.ll_reservationListFind})
    public void onViewClicked(View view){
        switch (view.getId()){
            case R.id.ll_carList:
                applyPermission(R.id.ll_carList);
                break;
            case R.id.ll_bill:
                startActivity(MyBillActivity.class);
                break;
            case R.id.ll_reservation:
                startActivity(ReservationRecordActivity.class);
                break;
            case R.id.ll_changeMsg:
                applyPermission(R.id.ll_changeMsg);
                break;
            case R.id.ll_feedback:
                startActivity(ComplaintActivity.class);
                break;
            case R.id.tv_unLogin:
                startToLogin();
                break;
            case R.id.ll_fleet:
                applyPermission(R.id.ll_fleet);
                break;
            case R.id.ll_historyFind:
                int flag = ConfigUtils.getUserFlag();
                if (flag == 9){
                    startActivity(HistoryFindActivity.class);
                }else {
                    startActivity(FleetHistoryFindActivity.class);
                }
                break;
            case R.id.ll_inspectionRecord:
                Bundle bundle1 = new Bundle();
                bundle1.putString("groupId",groupId);
                startActivity(InspectionRecordActivity.class,bundle1);
                break;
            case R.id.ll_warehouse:
                int userFlag1 = ConfigUtils.getUserFlag();
                if (userFlag1 == 8 || userFlag1 == 12){
                    startActivity(ManagerWarehouseActivity.class);
                }else {
                    Bundle bundle = new Bundle();
                    bundle.putString("type","mine");
                    startActivity(UserWarehouseActivity.class,bundle);
                }
                break;
            case R.id.ll_todayFind:
                startActivity(TodayFindActivity.class);
                break;
            case R.id.ll_build_reservation_fk://紧急预约查询与付款
                startActivity(AdminQueryPaymentActivity.class);
                break;
            case R.id.ll_reservationListFind://管理员 预约列表查询
                startActivity(AdminReservationListActivity.class);
                break;
        }
    }

    /**
     * 申请相机权限开始ocr扫描
     * @param viewId
     */
    private void applyPermission(int viewId) {
        String[] permissions = new String[]{PermissionsUtils.CAMERA,PermissionsUtils.WRITE_EXTERNAL_STORAGE,PermissionsUtils.READ_EXTERNAL_STORAGE};

        String permissionDes = "手机相册";
        CreditPermissionUtil.applyPermission((MainActivity)getActivity(), FactoryApplication.getInstances(), permissions, permissionDes,
                new CreditPermissionUtil.PermissionCallback() {
                    @Override
                    public void onGrant() {
                        if (R.id.ll_carList == viewId){
                            startActivity(CarListActivity.class);
                        }else if (R.id.ll_fleet == viewId){
                            Bundle bu = new Bundle();
                            bu.putString("userType","1");
                            startActivity(FleetRegisterActivity.class,bu);
                        }else if (R.id.ll_changeMsg == viewId){
                            int userFlag = ConfigUtils.getUserFlag();
                            Bundle bund = new Bundle();
                            if (userFlag == 4){//车队信息
                                bund.putString("userType","3");
                                startActivity(FleetRegisterActivity.class,bund);
                            }else {
                                bund.putString("userType","1");
                                startActivity(InfoAuthActivity.class,bund);
                            }
                        }
                    }
                }
        );
    }

    public void startToLogin(){
        SpUtils.clear();
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        getActivity().finish();
    }

    public void myInfo(){
        OkGo.<QueryVoLzyResponse<UserBean>>get(BaseUrl.YT_Base+BaseUrl.myInfo)
                .execute(new QueryVoDialogCallback<QueryVoLzyResponse<UserBean>>(getActivity(), false) {
                    @Override
                    public void onFail(int code, String desc) {
                        super.onFail(code, desc);
                        showToast(desc);
                    }

                    @Override
                    public void onError(String desc) {
                        super.onError(desc);
                        showToast(desc);
                    }

                    @Override
                    public void onSuccessNotData(Response<QueryVoLzyResponse<UserBean>> response, String desc) {
                        super.onSuccessNotData(response, desc);
                    }

                    @Override
                    public void onSuccess(Response<QueryVoLzyResponse<UserBean>> response, String desc) {
                        UserBean data = response.body().getData();
                        if (data != null){
                            ConfigUtils.saveUserMoble(data.getUserMobile());
                            ConfigUtils.saveUserName(data.getUserName());
                            tv_name.setText(data.getUserName());
                            tv_phone.setText(data.getUserMobile());
                            ConfigUtils.saveUserStatus(data.getUserStatus());
                            ConfigUtils.saveGroupAbbr(data.getTmsUserExt().getGroupAbbr());
                            UserBean.TmsBean tmsUserExt = data.getTmsUserExt();
                            if (tmsUserExt != null){
                                groupId = tmsUserExt.getGroupId();
                            }
                        }
                    }
                });
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            myInfo();
        }
    }
}
