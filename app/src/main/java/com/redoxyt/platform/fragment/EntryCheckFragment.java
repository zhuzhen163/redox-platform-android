package com.redoxyt.platform.fragment;


import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.redoxyt.platform.FactoryApplication;
import com.redoxyt.platform.R;
import com.redoxyt.platform.activity.MainActivity;
import com.redoxyt.platform.adapter.EntryCheckAdapter;
import com.redoxyt.platform.base.BaseFragment;
import com.redoxyt.platform.bean.BindMessageBean;
import com.redoxyt.platform.bean.CheckRecordDetail;
import com.redoxyt.platform.bean.EntryCheckBean;
import com.redoxyt.platform.bean.CheckSubmitBean;
import com.redoxyt.platform.qr.CaptureActivity;
import com.redoxyt.platform.uitl.CreditPermissionUtil;
import com.redoxyt.platform.uitl.FileUtil;
import com.redoxyt.platform.uitl.StringUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import http.callback.QueryVoDialogCallback;
import http.model.QueryVoLzyResponse;
import http.utils.BaseUrl;
import http.utils.Constant;
import util.CropImageUtils;
import util.GlideUtils;
import util.PermissionsUtils;
import util.PickImage;
import utils.ConfigUtils;
import widget.CommonToolbar;

import static android.app.Activity.RESULT_OK;

/**
 * 门卫--入场检查
 */

public class EntryCheckFragment extends BaseFragment implements EntryCheckAdapter.OnClickListener{

    @BindView(R.id.ctb_title)
    CommonToolbar ctb_title;
    @BindView(R.id.ll_carry)
    LinearLayout ll_carry;
    @BindView(R.id.iv_scan)
    ImageView iv_scan;
    @BindView(R.id.et_input_reservationCode)
    EditText et_input_reservationCode;
    @BindView(R.id.tv_submit)
    TextView tv_submit;
    @BindView(R.id.lv_list)
    LRecyclerView lv_list;
    @BindView(R.id.ll_confirm)
    LinearLayout ll_confirm;
    @BindView(R.id.tv_confirm)
    TextView tv_confirm;
    @BindView(R.id.ll_list)
    LinearLayout ll_list;

    private LRecyclerViewAdapter mLRecyclerViewAdapter = null;
    private EntryCheckAdapter entryCheckAdapter;
    private EntryCheckBean data;
    private String ImagePath; //拍照 照片本地地址
    private EntryCheckBean.WarehouseRule warehouseRule;

    @Override
    protected void initData() {
        setListView();
        ctb_title.getGoBack().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ll_carry.setVisibility(View.VISIBLE);
                ll_list.setVisibility(View.GONE);
                ctb_title.getGoBack().setVisibility(View.INVISIBLE);
                et_input_reservationCode.setText("");
            }
        });
        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ruleCommit();
            }
        });
    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_entry_check;
    }

    @OnClick({R.id.tv_submit,R.id.iv_scan})
    public void onViewClicked(View view){
        switch (view.getId()){
            case R.id.tv_submit:
                String code = et_input_reservationCode.getText().toString().trim();
                if (StringUtils.isNotBlank(code)){
                    listByReserveCode(code);
                }else {
                    showToast("请输入预约码");
                }
                break;
            case R.id.iv_scan:
                applyCameraPermission();
                break;
        }
    }

    /**
     * 申请相机权限开始ocr扫描
     */
    private void applyCameraPermission() {
        String[] permissions = new String[]{PermissionsUtils.CAMERA,PermissionsUtils.WRITE_EXTERNAL_STORAGE,PermissionsUtils.READ_EXTERNAL_STORAGE};

        String permissionDes = "手机相机";
        CreditPermissionUtil.applyPermission((MainActivity)getActivity(), FactoryApplication.getInstances(), permissions, permissionDes,
                new CreditPermissionUtil.PermissionCallback() {
                    @Override
                    public void onGrant() {
                        Bundle bundleCar = new Bundle();
                        bundleCar.putString("title", "扫描司机预约码");
                        startActivityForResult(CaptureActivity.class, bundleCar, 0x13);
                    }
                }
        );
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode){
                case 0x11:
                    if (data != null){
                        Uri uri = data.getData();
                        if (uri != null){
                            ImagePath = FileUtil.getFilePathByUri(getActivity(), uri);
                            saveInfomation(ImagePath);
                        }
                    }
                    break;
                case 0x12:
                    saveInfomation(ImagePath);
                    break;
                case 0x13:
                    String scan = data.getExtras().getString("uuid").toString();
                    try {
                        HashMap<String, String> car = new Gson().fromJson(scan, new TypeToken<HashMap<String, String>>() {
                        }.getType());
                        if (StringUtils.isNotBlank(car.get("code"))){
                            String code = car.get("code");
                            listByReserveCode(code);
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }

    @Override
    public void onUploadClick(int position) {
        warehouseRule = entryCheckAdapter.getDataList().get(position);
        showPictureDialog();
    }

    /**
     * 选择图片弹窗
     */
    private void showPictureDialog() {
        //1、使用Dialog、设置style
        final Dialog dialog = new Dialog(getActivity(), R.style.Dialog);
        //2、设置布局
        View view = View.inflate(getActivity(), R.layout.dialog_custom_layout, null);
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(false);
        Window window = dialog.getWindow();
        //设置弹出位置
        window.setGravity(Gravity.BOTTOM);
        //设置弹出动画
        window.setWindowAnimations(R.style.dialogAnim);
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.findViewById(R.id.tv_take_photo).setVisibility(View.VISIBLE);
        dialog.findViewById(R.id.tv_take_pic).setVisibility(View.VISIBLE);
        dialog.show();

        dialog.findViewById(R.id.tv_take_photo).setOnClickListener(view1 -> {
            //拍照
            ArrayList<String> list1 = new ArrayList<>();
            list1.add("android.permission.WRITE_EXTERNAL_STORAGE");
            list1.add("android.permission.CAMERA");
            if (PermissionsUtils.requestPermission(getActivity(), list1)) {
                ImagePath = Constant.CCB_PATH + "/" + "Image" + System.currentTimeMillis() + ".png";
                dialog.dismiss();
                PickImage.pickImageFromCamera(getActivity(), ImagePath, 0x12);
            } else {
                showToast("需要照相机权限");
            }
        });

        dialog.findViewById(R.id.tv_take_pic).setOnClickListener(view12 -> {
            //相册
            ArrayList<String> list1 = new ArrayList<>();
            list1.add("android.permission.WRITE_EXTERNAL_STORAGE");
            list1.add("android.permission.CAMERA");
            if (PermissionsUtils.requestPermission(getActivity(), list1)) {
                dialog.dismiss();
                PickImage.pickImageFromPhoto(getActivity(), 0x11);
            } else {
                showToast("需要照相机权限");
            }
        });
        dialog.findViewById(R.id.tv_cancel).setOnClickListener(view14 -> {
            //取消
            dialog.dismiss();
        });
    }


    private void setListView() {
        lv_list.setLayoutManager(new LinearLayoutManager(getActivity()));
        lv_list.setHasFixedSize(true);
        lv_list.refresh();
        entryCheckAdapter = new EntryCheckAdapter(getActivity());
        entryCheckAdapter.setOnItemClickListener(this);
        mLRecyclerViewAdapter = new LRecyclerViewAdapter(entryCheckAdapter);
        lv_list.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader); //设置下拉刷新Progress的样式
        lv_list.setAdapter(mLRecyclerViewAdapter);
        lv_list.setLoadMoreEnabled(false);
        lv_list.setPullRefreshEnabled(false);
    }



    /**
     * 根据预约码查询入场检查列表
     * @param reserveCode
     */
    public void listByReserveCode(String reserveCode) {
        OkGo.<QueryVoLzyResponse<EntryCheckBean>>get(BaseUrl.YT_Base + BaseUrl.listByReserveCode+reserveCode)
                .execute(new QueryVoDialogCallback<QueryVoLzyResponse<EntryCheckBean>>(getActivity(), true) {
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
                    public void onSuccessNotData(Response<QueryVoLzyResponse<EntryCheckBean>> response, String desc) {
                        super.onSuccessNotData(response, desc);
                    }

                    @Override
                    public void onSuccess(Response<QueryVoLzyResponse<EntryCheckBean>> response, String desc) {
                        data = response.body().getData();
                        if (data != null && data.getRuleList().size()>0){
                            ll_carry.setVisibility(View.GONE);
                            ll_list.setVisibility(View.VISIBLE);
                            ctb_title.getGoBack().setVisibility(View.VISIBLE);
                            entryCheckAdapter.setDataList(data.getRuleList());
                            entryCheckAdapter.notifyDataSetChanged();
                        }else {
                            ll_carry.setVisibility(View.VISIBLE);
                            ll_list.setVisibility(View.GONE);
                            ctb_title.getGoBack().setVisibility(View.INVISIBLE);
                        }
                    }
                });
    }

    /**
     * 提交入场检查
     */
    public void ruleCommit() {
        CheckSubmitBean bean = new CheckSubmitBean();
        bean.setDriverId(data.getDriverId());
        bean.setWarehouseGroupId(data.getWarehouseGroupId());
        bean.setReserveId(data.getReserveId());
        List<CheckRecordDetail> detailList = new ArrayList<>();
        List<EntryCheckBean.WarehouseRule> dataList = entryCheckAdapter.getDataList();
        for (int i = 0; i <dataList.size() ; i++) {
            EntryCheckBean.WarehouseRule warehouseRule = dataList.get(i);
            CheckRecordDetail detail = new CheckRecordDetail();
            detail.setRuleText(warehouseRule.getRuleText());
            detail.setStatus(warehouseRule.getStatus());
            detail.setWarehouseRuleId(warehouseRule.getRuleId());
            detail.setImageUrl(warehouseRule.getImageUrl());
            detail.setRequired(warehouseRule.getRequired());
            detail.setImageUpload(warehouseRule.getImageUpload());
            detailList.add(detail);
        }
        bean.setRecordDetailList(detailList);

        String detail = new Gson().toJson(bean);

        OkGo.<QueryVoLzyResponse<String>>post(BaseUrl.YT_Base + BaseUrl.ruleCommit)
                .upJson(detail)
                .execute(new QueryVoDialogCallback<QueryVoLzyResponse<String>>(getActivity(), true) {
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
                    public void onSuccessNotData(Response<QueryVoLzyResponse<String>> response, String desc) {
                        super.onSuccessNotData(response, desc);
                        showToast(desc);
                        et_input_reservationCode.setText("");
                        ll_list.setVisibility(View.GONE);
                        ctb_title.getGoBack().setVisibility(View.INVISIBLE);
                        ll_carry.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onSuccess(Response<QueryVoLzyResponse<String>> response, String desc) {
                        showToast(desc);
                        et_input_reservationCode.setText("");
                        ll_list.setVisibility(View.GONE);
                        ctb_title.getGoBack().setVisibility(View.INVISIBLE);
                        ll_carry.setVisibility(View.VISIBLE);
                    }
                });
    }

    /**
     * @param path 图片路径
     * @param （入场检查图片26）
     */
    public void saveInfomation(String path) {
        OkGo.<QueryVoLzyResponse<BindMessageBean>>post(BaseUrl.YT_Base + BaseUrl.pub)
                .params("type", 26)
                .params("file", CropImageUtils.compress(path))
                .execute(new QueryVoDialogCallback<QueryVoLzyResponse<BindMessageBean>>(getActivity(), true) {
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
                    public void onSuccessNotData(Response<QueryVoLzyResponse<BindMessageBean>> response, String desc) {
                        super.onSuccessNotData(response, desc);
                        showToast(desc);
                    }

                    @Override
                    public void onSuccess(Response<QueryVoLzyResponse<BindMessageBean>> response, String desc) {
                        BindMessageBean data = response.body().getData();
                        if (data != null) {
                            warehouseRule.setImageUrl(data.getVehicleCheckUrl());
                        }
                    }
                });
    }

}
