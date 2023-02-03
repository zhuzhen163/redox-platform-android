package com.redoxyt.platform.activity;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.redoxyt.platform.R;
import com.redoxyt.platform.adapter.EntryCheckAdapter;
import com.redoxyt.platform.base.BaseActivity;
import com.redoxyt.platform.bean.BindMessageBean;
import com.redoxyt.platform.bean.CheckRecordDetail;
import com.redoxyt.platform.bean.CheckSubmitBean;
import com.redoxyt.platform.bean.EntryCheckBean;
import com.redoxyt.platform.uitl.FileUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import http.callback.QueryVoDialogCallback;
import http.model.QueryVoLzyResponse;
import http.utils.BaseUrl;
import http.utils.Constant;
import util.CropImageUtils;
import util.PermissionsUtils;
import util.PickImage;

/**
 * Created by zz.
 * description: 入场须知调查表
 */

public class QuestionnaireActivity extends BaseActivity implements EntryCheckAdapter.OnClickListener{
    @BindView(R.id.lv_list)
    LRecyclerView lv_list;
    @BindView(R.id.ll_confirm)
    LinearLayout ll_confirm;
    @BindView(R.id.tv_confirm)
    TextView tv_confirm;

    private LRecyclerViewAdapter mLRecyclerViewAdapter = null;
    private EntryCheckAdapter entryCheckAdapter;
    private EntryCheckBean data;
    private String ImagePath; //拍照 照片本地地址
    private EntryCheckBean.WarehouseRule warehouseRule;

    @Override
    public int setView() {
        return R.layout.acitivty_questionnaire;
    }

    @Override
    public void initData() {
        String reserveCode = getIntent().getStringExtra("reserveCode");
        setListView();
        listByReserveCode(reserveCode);
        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ruleCommit();
            }
        });
    }

    private void setListView() {
        lv_list.setLayoutManager(new LinearLayoutManager(QuestionnaireActivity.this));
        lv_list.setHasFixedSize(true);
        lv_list.refresh();
        entryCheckAdapter = new EntryCheckAdapter(QuestionnaireActivity.this);
        entryCheckAdapter.setOnItemClickListener(QuestionnaireActivity.this);
        mLRecyclerViewAdapter = new LRecyclerViewAdapter(entryCheckAdapter);
        lv_list.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader); //设置下拉刷新Progress的样式
        lv_list.setAdapter(mLRecyclerViewAdapter);
        lv_list.setLoadMoreEnabled(false);
        lv_list.setPullRefreshEnabled(false);
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
        final Dialog dialog = new Dialog(QuestionnaireActivity.this, R.style.Dialog);
        //2、设置布局
        View view = View.inflate(QuestionnaireActivity.this, R.layout.dialog_custom_layout, null);
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
            if (PermissionsUtils.requestPermission(QuestionnaireActivity.this, list1)) {
                ImagePath = Constant.CCB_PATH + "/" + "Image" + System.currentTimeMillis() + ".png";
                dialog.dismiss();
                PickImage.pickImageFromCamera(QuestionnaireActivity.this, ImagePath, 0x12);
            } else {
                showToast("需要照相机权限");
            }
        });

        dialog.findViewById(R.id.tv_take_pic).setOnClickListener(view12 -> {
            //相册
            ArrayList<String> list1 = new ArrayList<>();
            list1.add("android.permission.WRITE_EXTERNAL_STORAGE");
            list1.add("android.permission.CAMERA");
            if (PermissionsUtils.requestPermission(QuestionnaireActivity.this, list1)) {
                dialog.dismiss();
                PickImage.pickImageFromPhoto(QuestionnaireActivity.this, 0x11);
            } else {
                showToast("需要照相机权限");
            }
        });
        dialog.findViewById(R.id.tv_cancel).setOnClickListener(view14 -> {
            //取消
            dialog.dismiss();
        });
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
                            ImagePath = FileUtil.getFilePathByUri(QuestionnaireActivity.this, uri);
                            saveInfomation(ImagePath);
                        }
                    }
                    break;
                case 0x12:
                    saveInfomation(ImagePath);
                    break;
            }
        }
    }

    /**
     * 根据预约码查询入场检查列表
     * @param reserveCode
     */
    public void listByReserveCode(String reserveCode) {
        OkGo.<QueryVoLzyResponse<EntryCheckBean>>get(BaseUrl.YT_Base + BaseUrl.listByReserveCode+reserveCode)
                .execute(new QueryVoDialogCallback<QueryVoLzyResponse<EntryCheckBean>>(QuestionnaireActivity.this, true) {
                    @Override
                    public void onFail(int code, String desc) {
                        super.onFail(code, desc);
                        showToast(desc);
                        tv_confirm.setVisibility(View.GONE);
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
                            entryCheckAdapter.setDataList(data.getRuleList());
                            entryCheckAdapter.notifyDataSetChanged();
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
                .execute(new QueryVoDialogCallback<QueryVoLzyResponse<String>>(QuestionnaireActivity.this, true) {
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
                        finish();
                    }

                    @Override
                    public void onSuccess(Response<QueryVoLzyResponse<String>> response, String desc) {
                        showToast(desc);
                        finish();
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
                .execute(new QueryVoDialogCallback<QueryVoLzyResponse<BindMessageBean>>(QuestionnaireActivity.this, true) {
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
