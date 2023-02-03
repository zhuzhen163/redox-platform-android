package com.redoxyt.platform.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;

import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.redoxyt.platform.R;
import com.redoxyt.platform.adapter.InspectionRecordAdapter;
import com.redoxyt.platform.base.BaseActivity;
import com.redoxyt.platform.bean.EntryCheckListBean;
import com.redoxyt.platform.uitl.AppUtils;

import java.util.List;

import butterknife.BindView;
import http.callback.QueryVoDialogCallback;
import http.model.QueryVoLzyResponse;
import http.utils.BaseUrl;

/**
 * Created by zz.
 * description:入场检查记录
 */

public class InspectionRecordActivity extends BaseActivity {

    @BindView(R.id.lv_list)
    LRecyclerView mRecyclerView;
    @BindView(R.id.et_search)
    EditText et_search;
    @BindView(R.id.iv_clear)
    ImageView iv_clear;

    private LRecyclerViewAdapter mLRecyclerViewAdapter = null;
    private InspectionRecordAdapter recordAdapter;

    private String groupId;
    private int page = 1;

    @Override
    public int setView() {
        return R.layout.acitivty_inspection_record;
    }

    @Override
    public void initData() {
        groupId = getIntent().getStringExtra("groupId");
        setListView();
        listByReserveCode();

        iv_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listByReserveCode();
            }
        });

        et_search.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH){
                String input=et_search.getText().toString().trim();
                if(!TextUtils.isEmpty(input)){

                    AppUtils.hideInputMethod(InspectionRecordActivity.this);
                }else {
                    showToast("请输入搜索词");
                }
                return true;
            }
            return false;
        });

        mRecyclerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                mRecyclerView.setNoMore(false);
                listByReserveCode();
            }
        });

        mRecyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                listByReserveCode();
            }
        });
    }

    /**
     * 查询入场检查记录列表
     */
    public void listByReserveCode() {
        HttpParams params = new HttpParams();
        params.put("warehouseId",groupId);
        params.put("page",page);
        params.put("rows","20");
        OkGo.<QueryVoLzyResponse<EntryCheckListBean>>get(BaseUrl.YT_Base + BaseUrl.listCheckRecord)
                .params(params)
                .execute(new QueryVoDialogCallback<QueryVoLzyResponse<EntryCheckListBean>>(InspectionRecordActivity.this, true) {
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
                    public void onSuccessNotData(Response<QueryVoLzyResponse<EntryCheckListBean>> response, String desc) {
                        super.onSuccessNotData(response, desc);
                        recordAdapter.clear();
                        recordAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onSuccess(Response<QueryVoLzyResponse<EntryCheckListBean>> response, String desc) {
                        EntryCheckListBean data = response.body().getData();
                        List<EntryCheckListBean.WarehouseRule> list = data.getList();
                        if (page == 1){
                            recordAdapter.setDataList(list);
                        }else {
                            recordAdapter.addAll(list);
                        }
                        requestComplete();
                        if (data.getTotal() == recordAdapter.getDataList().size()){
                            mRecyclerView.setNoMore(true);
                        }
                        page++;
                    }
                });
    }

    private void setListView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(InspectionRecordActivity.this));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.refresh();
        recordAdapter = new InspectionRecordAdapter(InspectionRecordActivity.this);
        mLRecyclerViewAdapter = new LRecyclerViewAdapter(recordAdapter);
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader); //设置下拉刷新Progress的样式
        mRecyclerView.setAdapter(mLRecyclerViewAdapter);
        mRecyclerView.setLoadMoreEnabled(true);
        mRecyclerView.setPullRefreshEnabled(true);
    }

    private void requestComplete(){
        try {
            mLRecyclerViewAdapter.notifyDataSetChanged();
            mRecyclerView.refreshComplete(20);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
