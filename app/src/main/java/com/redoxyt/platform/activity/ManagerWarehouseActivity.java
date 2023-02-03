package com.redoxyt.platform.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.redoxyt.platform.R;
import com.redoxyt.platform.adapter.ManagerWarehouseAdapter;
import com.redoxyt.platform.base.BaseActivity;
import com.redoxyt.platform.bean.WarehouseBean;
import com.redoxyt.platform.uitl.StringUtils;
import com.redoxyt.platform.widget.AddWarehouseDialog;
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
 * description:我管理的仓库
 */

public class ManagerWarehouseActivity extends BaseActivity implements ManagerWarehouseAdapter.OnItemClickListener{

    @BindView(R.id.rv_swipe)
    SwipeMenuRecyclerView swipeRecyclerView;

    private List<WarehouseBean> list = new ArrayList<>();
    private ManagerWarehouseAdapter adapter;
    private LinearLayoutManager layoutManager;

    @Override
    public int setView() {
        return R.layout.acitivty_manager_warehouse;
    }

    @Override
    public void initData() {
        listWarehouse();
        loadCH();
    }

    @Override
    public void onItemClick(WarehouseBean bean) {
        //从我的页面进入常用仓库
        Bundle bundle = new Bundle();
        bundle.putString("warehouseName",bean.getPlatformWarehouseName());
        bundle.putString("warehouseCode",bean.getPlatformWarehouseCode());
        bundle.putString("warehouseId",bean.getWarehouseId());
        bundle.putString("warehouseAddress",bean.getGroupAddress());
        startActivity(WarehouseQrActivity.class,bundle);
    }


    private void loadCH() {
        adapter = new ManagerWarehouseAdapter(list);
        adapter.setOnItemClickListener(this);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        swipeRecyclerView.setLayoutManager(layoutManager);
        // 创建菜单：
//        SwipeMenuCreator mSwipeMenuCreator = (leftMenu, rightMenu, viewType) -> {
//
//            int width = getResources().getDimensionPixelOffset(R.dimen.dimen60dp);
//            int height = ViewGroup.LayoutParams.MATCH_PARENT;
//            // 注意：哪边不想要菜单，那么不要添加即可。
//            SwipeMenuItem addItem = new SwipeMenuItem(ManagerWarehouseActivity.this)
//                    .setBackgroundColor(Color.RED)
//                    .setText("删除")
//                    .setTextColor(Color.WHITE)
//                    .setWidth(width)
//                    .setHeight(height);
//            rightMenu.addMenuItem(addItem); // 添加菜单到右侧。
//        };
//    // 设置监听器。
//        swipeRecyclerView.setSwipeMenuCreator(mSwipeMenuCreator);

//        SwipeMenuItemClickListener mMenuItemClickListener = menuBridge -> {
//            // 任何操作必须先关闭菜单，否则可能出现Item菜单打开状态错乱。
//            menuBridge.closeMenu();
//            int adapterPosition = menuBridge.getAdapterPosition(); // RecyclerView的Item的position。
//            WarehouseBean bean = list.get(adapterPosition);
//            deletePltfFleetWarehouseById(bean.getWarehouseId());
//        };
//
//        // 菜单点击监听。
//        swipeRecyclerView.setSwipeMenuItemClickListener(mMenuItemClickListener);
//
//        // 添加适配器
        swipeRecyclerView.setAdapter(adapter);
    }

    /**
     * 车队查询常用仓库
     */
    public void listWarehouse() {
        OkGo.<QueryVoLzyResponse<List<WarehouseBean>>>get(BaseUrl.YT_Base + BaseUrl.listWarehouse)
                .execute(new QueryVoDialogCallback<QueryVoLzyResponse<List<WarehouseBean>>>(ManagerWarehouseActivity.this, true) {
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
                    public void onSuccessNotData(Response<QueryVoLzyResponse<List<WarehouseBean>>> response, String desc) {
                        super.onSuccessNotData(response, desc);
                    }

                    @Override
                    public void onSuccess(Response<QueryVoLzyResponse<List<WarehouseBean>>> response, String desc) {
                        List<WarehouseBean> data = response.body().getData();
                        if (data != null){
                            list = data;
                            adapter.setList(list);
                        }
                    }
                });
    }

}
