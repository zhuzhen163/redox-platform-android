package com.redoxyt.platform.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.redoxyt.platform.R;
import com.redoxyt.platform.adapter.UserWarehouseAdapter;
import com.redoxyt.platform.base.BaseActivity;
import com.redoxyt.platform.bean.ReservationBean;
import com.redoxyt.platform.bean.WarehouseBean;
import com.redoxyt.platform.bean.WarehouseListBean;
import com.redoxyt.platform.uitl.StringUtils;
import com.redoxyt.platform.widget.AddWarehouseDialog;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import http.callback.QueryVoDialogCallback;
import http.model.QueryVoLzyResponse;
import http.utils.BaseUrl;
import utils.ConfigUtils;

/**
 * Created by zz.
 * description:
 */

public class UserWarehouseActivity extends BaseActivity implements UserWarehouseAdapter.OnItemClickListener,
        AddWarehouseDialog.SubmitCallBack{

    @BindView(R.id.rv_swipe)
    SwipeMenuRecyclerView swipeRecyclerView;
    @BindView(R.id.tv_addWarehouse)
    TextView tv_addWarehouse;

    private List<ReservationBean> list = new ArrayList<>();
    private UserWarehouseAdapter adapter;
    private LinearLayoutManager layoutManager;

    private AddWarehouseDialog dialog;
    private String groupId,warehouseId,warehouseName,warehouseCode;
    private String type;

    @Override
    public int setView() {
        return R.layout.acitivty_user_warehouse;
    }

    @Override
    public void initData() {
        type = getIntent().getStringExtra("type");
        if (StringUtils.isBlank(type)) type = "";
        dialog = new AddWarehouseDialog(UserWarehouseActivity.this);
        dialog.setCallBack(this);
        getListByFleetId();
        loadCH();
        tv_addWarehouse.setOnClickListener(view -> {
            if (dialog != null && !dialog.isShowing()){
                dialog.show();
            }
        });
    }

    @Override
    public void onItemClick(ReservationBean bean) {
        if (type.equals("mine")) return;//?????????????????????????????????
        Intent intent = new Intent();
        intent.putExtra("warehouseName",bean.getWarehouseName());
        intent.putExtra("warehouseCode",bean.getWarehouseCode());
        intent.putExtra("warehouseId",bean.getWarehouseId());
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void submit() {
        savePltfFleetWarehouse();
    }

    @Override
    public void findWarehouse(String code) {
        selectByWarehouseCode(code);
    }

    private void loadCH() {
        adapter = new UserWarehouseAdapter(list);
        adapter.setOnItemClickListener(this);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        swipeRecyclerView.setLayoutManager(layoutManager);
        // ???????????????
        SwipeMenuCreator mSwipeMenuCreator = (leftMenu, rightMenu, viewType) -> {

            int width = getResources().getDimensionPixelOffset(R.dimen.dimen60dp);
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            // ????????????????????????????????????????????????????????????
            SwipeMenuItem addItem = new SwipeMenuItem(UserWarehouseActivity.this)
                    .setBackgroundColor(Color.RED)
                    .setText("??????")
                    .setTextColor(Color.WHITE)
                    .setWidth(width)
                    .setHeight(height);
            rightMenu.addMenuItem(addItem); // ????????????????????????
        };
    // ??????????????????
        swipeRecyclerView.setSwipeMenuCreator(mSwipeMenuCreator);

        SwipeMenuItemClickListener mMenuItemClickListener = menuBridge -> {
            // ??????????????????????????????????????????????????????Item???????????????????????????
            menuBridge.closeMenu();
            int adapterPosition = menuBridge.getAdapterPosition(); // RecyclerView???Item???position???
            ReservationBean bean = list.get(adapterPosition);
            deletePltfFleetWarehouseById(bean.getId());
        };

        // ?????????????????????
        swipeRecyclerView.setSwipeMenuItemClickListener(mMenuItemClickListener);

        // ???????????????
        swipeRecyclerView.setAdapter(adapter);
    }

    /**
     * ????????????????????????
     */
    public void getListByFleetId() {
        HttpParams params = new HttpParams();
        params.put("fleetId",ConfigUtils.getGroupId());
        OkGo.<QueryVoLzyResponse<WarehouseListBean>>get(BaseUrl.YT_Base + BaseUrl.getListByFleetId)
                .params(params)
                .execute(new QueryVoDialogCallback<QueryVoLzyResponse<WarehouseListBean>>(UserWarehouseActivity.this, true) {
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
                    public void onSuccessNotData(Response<QueryVoLzyResponse<WarehouseListBean>> response, String desc) {
                        super.onSuccessNotData(response, desc);
                    }

                    @Override
                    public void onSuccess(Response<QueryVoLzyResponse<WarehouseListBean>> response, String desc) {
                        WarehouseListBean data = response.body().getData();
                        if (data != null){
                            list = data.getList();
                            adapter.setList(list);
                        }
                    }
                });
    }

    /**
     * ??????????????????????????????
     */
    public void selectByWarehouseCode(String code) {
        HttpParams params = new HttpParams();
        params.put("warehouseCode",code);
        OkGo.<QueryVoLzyResponse<WarehouseBean>>get(BaseUrl.YT_Base + BaseUrl.selectByWarehouseCode)
                .params(params)
                .execute(new QueryVoDialogCallback<QueryVoLzyResponse<WarehouseBean>>(UserWarehouseActivity.this, true) {
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
                    public void onSuccessNotData(Response<QueryVoLzyResponse<WarehouseBean>> response, String desc) {
                        super.onSuccessNotData(response, desc);
                    }

                    @Override
                    public void onSuccess(Response<QueryVoLzyResponse<WarehouseBean>> response, String desc) {
                        WarehouseBean data = response.body().getData();
                        if (data != null){
                            if (dialog != null){
                                dialog.setWarehouseName(data.getPlatformWarehouseName());
                                groupId = data.getGroupId();
                                warehouseCode = data.getPlatformWarehouseCode();
                                warehouseName = data.getPlatformWarehouseName();
                                warehouseId = data.getWarehouseId();
                            }
                        }
                    }
                });
    }

    /**
     * ????????????????????????
     */
    public void savePltfFleetWarehouse() {
        HttpParams params = new HttpParams();
        params.put("fleetId", ConfigUtils.getGroupId());
        params.put("warehouseGroupId", groupId);
        params.put("warehouseName", warehouseName);
        params.put("warehouseCode", warehouseCode);
        params.put("warehouseId", warehouseId);
        OkGo.<QueryVoLzyResponse<Object>>post(BaseUrl.YT_Base + BaseUrl.savePltfFleetWarehouse)
                .params(params)
                .execute(new QueryVoDialogCallback<QueryVoLzyResponse<Object>>(UserWarehouseActivity.this, true) {
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
                    public void onSuccessNotData(Response<QueryVoLzyResponse<Object>> response, String desc) {
                        super.onSuccessNotData(response, desc);
                        showToast(desc);
                        getListByFleetId();
                    }

                    @Override
                    public void onSuccess(Response<QueryVoLzyResponse<Object>> response, String desc) {
                        showToast(desc);
                        getListByFleetId();
                    }
                });
    }

    /**
     * ????????????????????????
     */
    public void deletePltfFleetWarehouseById(String id) {
        HttpParams params = new HttpParams();
        params.put("id", id);
        OkGo.<QueryVoLzyResponse<Object>>get(BaseUrl.YT_Base + BaseUrl.deletePltfFleetWarehouseById)
                .params(params)
                .execute(new QueryVoDialogCallback<QueryVoLzyResponse<Object>>(UserWarehouseActivity.this, true) {
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
                    public void onSuccessNotData(Response<QueryVoLzyResponse<Object>> response, String desc) {
                        super.onSuccessNotData(response, desc);
                        showToast(desc);
                        getListByFleetId();
                    }

                    @Override
                    public void onSuccess(Response<QueryVoLzyResponse<Object>> response, String desc) {
                        showToast(desc);
                        getListByFleetId();
                    }
                });
    }

}
