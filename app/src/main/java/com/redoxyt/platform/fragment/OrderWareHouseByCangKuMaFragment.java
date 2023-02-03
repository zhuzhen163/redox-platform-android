package com.redoxyt.platform.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.redoxyt.platform.R;
import com.redoxyt.platform.activity.PlatformListActivity;
import com.redoxyt.platform.base.BaseFragment;
import com.redoxyt.platform.base.CommonAdapter;
import com.redoxyt.platform.base.ViewHolder;
import com.redoxyt.platform.bean.OrderWareHouseYtListBean;
import com.redoxyt.platform.bean.QiyeXinXiReportBean;
import com.redoxyt.platform.bean.YTListBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import http.callback.QueryVoDialogCallback;
import http.model.QueryVoLzyResponse;
import http.utils.BaseUrl;
import utils.ConfigUtils;

public class OrderWareHouseByCangKuMaFragment extends BaseFragment {
    @BindView(R.id.et_warehouse_code)
    EditText etWarehouseCode;
    @BindView(R.id.tv_warehouse_name)
    TextView tvWarehouseName;
    @BindView(R.id.tv_yt_name)
    TextView tvYtName;
    @BindView(R.id.tv_add_platform_moreorder)
    TextView tvAddPlatformMoreorder;
    @BindView(R.id.rc_yt_list_cangkuorder)
    RecyclerView rcYtListCangkuorder;
    Unbinder unbinder;
    @BindView(R.id.bt_orderby_cangku)
    Button btOrderbyCangku;
    Unbinder unbinder1;
    private OptionsPickerView<Object> typeOptions;
    private List<YTListBean> data = new ArrayList<>();//服务器返回的月台列表
    private List<YTListBean> yonghuSelectData = new ArrayList<>();//用户选择的月台以及仓库集合
    private List<Object> ytNamesList = new ArrayList<>();//选择器展示的月台名称列表
    private List<String> ytIdsList = new ArrayList<>();//选择器保存的月台ID列表
    private List<String> serverYtIdsList = new ArrayList<>();//向服务器提交的月台ID列表
    private List<String> serverWareHouseIdsList = new ArrayList<>();//向服务器提交的仓库ID列表
    private String ytId;//用户在选择器中选中的月台ID
    Handler handler = new Handler();
    private YTListBean ytListBean;
    private CommonAdapter<YTListBean> mAdapter;
    private boolean isDeleteAble = true;
    private boolean isQrScanWareHouse;//判断是用户是否扫描仓库码
    private boolean isQrScanPlatForm;//判断是用户是否扫描月台码
    private String platformId;

    public OrderWareHouseByCangKuMaFragment() {
    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_orderwarehousebycangkuma;
    }

    @Override
    protected void initData() {
        etWarehouseCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (etWarehouseCode.getText().toString().trim().length() == 6) {
                    listByWarehouseCode(etWarehouseCode.getText().toString().trim());
                }
            }
        });
        rcYtListCangkuorder.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = initRecyclerViewAdapter();
        this.rcYtListCangkuorder.setAdapter(mAdapter);
    }

    public void setQrView(String warehouseCode, boolean isQrScan) {
        this.isQrScanWareHouse = isQrScan;
        etWarehouseCode.setText(warehouseCode);
    }

    public void setplatformId(String warehouseCode, String platformId, boolean isQrScanPlatForm) {
        this.platformId = platformId;
        this.isQrScanPlatForm = isQrScanPlatForm;
        etWarehouseCode.setText(warehouseCode);

    }

    public void listByWarehouseCode(String warehouseCode) {
        HttpParams params = new HttpParams();
        params.put("warehouseCode", warehouseCode);
        if (ConfigUtils.getUserFlag() == 4) {
            params.put("fleetId", ConfigUtils.getGroupId());
        }
        params.put("muchWarehouseReserve", 1);
        OkGo.<QueryVoLzyResponse<List<YTListBean>>>get(BaseUrl.YT_Base + BaseUrl.litsByWarehouseCode)
                .params(params)
                .execute(new QueryVoDialogCallback<QueryVoLzyResponse<List<YTListBean>>>(getActivity(), true) {
                    @Override
                    public void onFail(int code, String desc) {
                        super.onFail(code, desc);
                        showToast(desc);
                        etWarehouseCode.setText("");
                    }

                    @Override
                    public void onError(String desc) {
                        super.onError(desc);
                        showToast(desc);
                        etWarehouseCode.setText("");
                    }

                    @Override
                    public void onSuccessNotData(Response<QueryVoLzyResponse<List<YTListBean>>> response, String desc) {
                        super.onSuccessNotData(response, desc);
                    }

                    @Override
                    public void onSuccess(Response<QueryVoLzyResponse<List<YTListBean>>> response, String desc) {
                        data.clear();
                        ytIdsList.clear();
                        ytNamesList.clear();
                        data = response.body().getData();
                        if (data != null && data.size() != 0) {
                            tvWarehouseName.setText(data.get(0).getWarehouseName());
                            ytNamesList.add("全部");
                            tvYtName.setText("全部");
                            for (int i = 0; i < data.size(); i++) {
                                ytNamesList.add(data.get(i).getPlatformName());
                                ytIdsList.add(data.get(i).getPlatformId());
                            }
                            if (isQrScanWareHouse) {
                                addInList();
                                isQrScanWareHouse = false;
                            }
                            if (isQrScanPlatForm) {
                                for (int i = 0; i < data.size(); i++) {
                                    if (data.get(i).getPlatformId().equals(platformId)) {
                                        ytListBean = data.get(i);
                                        tvYtName.setText(ytListBean.getPlatformName());
                                        addInList();
                                    }
                                }
                            }
                        }
                    }
                });
    }

    /**
     * 新建多仓预约列表
     */
    private void addMuchWareHouse() {
        if (data.size() == 0) {
            showToast("请先填写仓库码或扫描仓库/月台二维码");
            return;
        }
        serverWareHouseIdsList.clear();
        serverYtIdsList.clear();
        OrderWareHouseYtListBean orderWareHouseYtListBean = new OrderWareHouseYtListBean();
        if (yonghuSelectData != null && yonghuSelectData.size() != 0) {
            for (int i = 0; i < yonghuSelectData.size(); i++) {
                if (yonghuSelectData.get(i).isAll) {
                    serverWareHouseIdsList.add(yonghuSelectData.get(i).getWarehouseId());
                } else {
                    serverYtIdsList.add(yonghuSelectData.get(i).getPlatformId());
                }

            }
        } else {
            serverWareHouseIdsList.add(data.get(0).getWarehouseId());
        }
        if (serverWareHouseIdsList.size() > 0) {
            orderWareHouseYtListBean.setWarehouseIds(serverWareHouseIdsList);
        }
        if (serverYtIdsList.size() > 0) {
            orderWareHouseYtListBean.setPlatformIds(serverYtIdsList);
        }
        Gson gson = new Gson();
        String toJson = gson.toJson(orderWareHouseYtListBean);

        OkGo.<QueryVoLzyResponse<QiyeXinXiReportBean>>post(BaseUrl.YT_Base + BaseUrl.ADDMUCHWAREHOUSERESERVE)
                .upJson(toJson)
                .execute(new QueryVoDialogCallback<QueryVoLzyResponse<QiyeXinXiReportBean>>(getActivity(), true) {
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
                    public void onSuccessNotData(Response<QueryVoLzyResponse<QiyeXinXiReportBean>> response, String desc) {
                        super.onSuccessNotData(response, desc);

                    }

                    @Override
                    public void onSuccess(Response<QueryVoLzyResponse<QiyeXinXiReportBean>> response, String desc) {
                        startActivity(PlatformListActivity.class);
                    }
                });

    }

    @OnClick({R.id.tv_yt_name, R.id.tv_add_platform_moreorder, R.id.bt_orderby_cangku})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_yt_name:
                if (ytNamesList != null && ytNamesList.size() > 0) {
                    initWarehousePicker();
                } else {
                    showToast("请先填写仓库码或扫描仓库/月台二维码");
                }
                break;
            case R.id.tv_add_platform_moreorder:
                addInList();
                break;
            case R.id.bt_orderby_cangku:
                addMuchWareHouse();
                break;
        }
    }

    /**
     * 用户点击添加后，想容器中添加数据
     */
    private void addInList() {
        if (data != null && data.size() > 0) {
            if (tvYtName.getText().toString().equals("全部")) {
                YTListBean setYtListBean = new YTListBean();
                setYtListBean.setWarehouseId(data.get(0).getWarehouseId());
                setYtListBean.setWarehouseName(data.get(0).getWarehouseName());
                setYtListBean.setWarehouseCode(data.get(0).getWarehouseCode());
                setYtListBean.isAll = true;
                if (isChongfu(setYtListBean, true)) {
                    showToast("仓库已添加，请勿重复添加");
                } else {
                    yonghuSelectData.add(setYtListBean);
                }
            } else {
                YTListBean setYtListBean1 = new YTListBean();
                setYtListBean1 = ytListBean;
                setYtListBean1.isAll = false;
                if (isChongfu(setYtListBean1, false)) {
                    showToast("月台已添加，请勿重复添加");
                } else {
                    yonghuSelectData.add(setYtListBean1);
                }
            }
            mAdapter.setData(yonghuSelectData);
        } else {
            showToast("请先填写仓库码或扫描仓库/月台二维码");
        }
    }

    /**
     * 判断用户添加的是否重复
     *
     * @param ytListBean
     * @param isAll
     */
    private boolean isChongfu(YTListBean ytListBean, boolean isAll) {
        boolean isChongfu = false;
        if (isAll) {
            for (int i = 0; i < yonghuSelectData.size(); i++) {
                if (yonghuSelectData.get(i).isAll) {
                    if (yonghuSelectData.get(i).getWarehouseId().equals(ytListBean.getWarehouseId())) {
                        isChongfu = true;
                    }
                }
            }
        } else {
            for (int i = 0; i < yonghuSelectData.size(); i++) {
                if (!yonghuSelectData.get(i).isAll) {
                    if (yonghuSelectData.get(i).getPlatformId().equals(ytListBean.getPlatformId())) {
                        isChongfu = true;
                    }
                }
            }
        }

        return isChongfu;
    }

    private void initWarehousePicker() {
        typeOptions = new OptionsPickerBuilder(getActivity(), (options1, option2, options3, v) -> {
            String value = (String) ytNamesList.get(options1);
            tvYtName.setText(value);
            if (!value.equals("全部")) {
                ytId = ytIdsList.get(options1 - 1);
                ytListBean = data.get(options1 - 1);
            }
        })
                .setLayoutRes(R.layout.pickerview_custom_options, v -> {
                    final TextView tvSubmit = v.findViewById(R.id.tv_confirm);
                    TextView ivCancel = v.findViewById(R.id.tv_cancel);
                    tvSubmit.setOnClickListener(v1 -> {
                        typeOptions.dismiss();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                typeOptions.returnData();
                            }
                        }, 500);
                    });

                    ivCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            typeOptions.dismiss();
                        }
                    });
                })
                .isDialog(false)
                .setOutSideCancelable(false)
                .build();

        typeOptions.setPicker(ytNamesList);
        typeOptions.show();
    }

    protected CommonAdapter<YTListBean> initRecyclerViewAdapter() {
        CommonAdapter<YTListBean> adapter = new CommonAdapter<YTListBean>(getActivity(),
                R.layout.item_yt_selet_list, yonghuSelectData) {
            @Override
            public void convert(ViewHolder itemViewHolder, YTListBean bean, int position) {
                if (bean.isAll) {
                    itemViewHolder.setVisible(R.id.rl_yt_name, false);
                } else {
                    itemViewHolder.setVisible(R.id.rl_yt_name, true);
                    itemViewHolder.setText(R.id.tv_yt_name, bean.getPlatformName());
                }
                itemViewHolder.setText(R.id.tv_warehouse_code, bean.getWarehouseCode());
                itemViewHolder.setText(R.id.tv_warehouse_name, bean.getWarehouseName());
                itemViewHolder.setOnClickListener(R.id.iv_app_yt_close, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        reFreshList(position);
                    }
                });

            }
        };
        //adapter.setEmptyView(R.layout.view_inv_smart_emptyview);
        adapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener<YTListBean>() {
            @Override
            public void onItemClick(View view, ViewHolder holder, YTListBean bean, int position) {

            }

            @Override
            public boolean onItemLongClick(View view, ViewHolder holder, YTListBean bean) {
                return false;
            }
        });
        return adapter;
    }

    /**
     * 删除并刷新月台列表
     *
     * @param position
     */
    private void reFreshList(int position) {
        if (isDeleteAble) {
            isDeleteAble = false;
            yonghuSelectData.remove(position);//删除数据源
            mAdapter.notifyItemRemoved(position);//刷新被删除的地方
            mAdapter.notifyItemRangeChanged(position, mAdapter.getItemCount());
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(500);
                        isDeleteAble = true;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }
}
