package com.redoxyt.platform.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.redoxyt.platform.R;
import com.redoxyt.platform.adapter.ComplaintAdapter;
import com.redoxyt.platform.base.BaseActivity;
import com.redoxyt.platform.bean.ComplainListBean;
import com.redoxyt.platform.bean.ComplaintBean;
import com.redoxyt.platform.bean.ComplaintListBean;
import com.redoxyt.platform.uitl.StringUtils;
import com.redoxyt.platform.widget.ComplaintDialog;
import com.redoxyt.platform.widget.YTDialog;


import java.util.List;

import butterknife.BindView;
import http.callback.QueryVoDialogCallback;
import http.model.QueryVoLzyResponse;
import http.utils.BaseUrl;
import utils.ConfigUtils;

/**
 * Created by zz.
 * description:投诉或建议
 */

public class ComplaintActivity extends BaseActivity implements ComplaintDialog.SubmitCallBack,ComplaintAdapter.OnItemClickListener,YTDialog.SubmitCallBack{

    @BindView(R.id.lv_list)
    LRecyclerView mRecyclerView;
    @BindView(R.id.btn_complaint)
    Button btn_complaint;
    @BindView(R.id.tv_confirm)
    TextView tv_confirm;
    @BindView(R.id.tv_privacy)
    TextView tv_privacy;

    private ComplaintDialog complaintDialog;
    private YTDialog dialog;
    private List<ComplaintListBean> listBeans;

    private int page = 1;
    private ComplaintAdapter complaintAdapter;
    private LRecyclerViewAdapter mLRecyclerViewAdapter = null;

    @Override
    public int setView() {
        return R.layout.acitivty_complaint;
    }

    @Override
    public void initData() {
        setListView();
        myComplaint();
        getComplainClass();
        dialog = new YTDialog(ComplaintActivity.this);
        dialog.setCallBack(this);

        btn_complaint.setOnClickListener(view -> {
            if (complaintDialog == null){
                complaintDialog = new ComplaintDialog(ComplaintActivity.this);
                complaintDialog.setList(listBeans);
                complaintDialog.setCallBack(this);
            }
            complaintDialog.show();
        });
        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("mUrl",BaseUrl.YT_BaseH5+"apptraderules");
                bundle.putString("mTitle","交易规则");
                startActivity(WebViewActivity.class,bundle);
            }
        });
        tv_privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bun = new Bundle();
                bun.putString("mUrl",BaseUrl.PRIVATE_URL);
                bun.putString("mTitle","隐私政策");
                startActivity(WebViewActivity.class,bun);
            }
        });
    }

    private void setListView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(ComplaintActivity.this));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.refresh();
        complaintAdapter = new ComplaintAdapter(ComplaintActivity.this);
        complaintAdapter.setOnItemClickListener(this);
        mLRecyclerViewAdapter = new LRecyclerViewAdapter(complaintAdapter);

        mRecyclerView.setAdapter(mLRecyclerViewAdapter);
        mRecyclerView.setLoadMoreEnabled(true);
        mRecyclerView.setPullRefreshEnabled(true);

        mRecyclerView.setOnRefreshListener(() -> {
            page = 1;
            mRecyclerView.setNoMore(false);
            myComplaint();
        });

        mRecyclerView.setOnLoadMoreListener(() -> myComplaint());
    }

    @Override
    public void submit(int type1, int type2, String content) {
        if (StringUtils.isNotBlank(content)){
            page = 1;
            complaintSave(type1,type2,content);
        }else {
            showToast("请输入内容");
        }
    }

    private void complaintSave(int complainClass,int complainType,String complainContent) {
        HttpParams params = new HttpParams();
        params.put("complainUserId", ConfigUtils.getUserId());
        params.put("complainUserMobile", ConfigUtils.getUserMoble());
        params.put("complainClass",complainClass);
        params.put("complainType",complainType);
        params.put("complainContent",complainContent);
        params.put("complainFlag","1");
        OkGo.<QueryVoLzyResponse<String>>post(BaseUrl.YT_Base+ BaseUrl.complaintSave)
                .params(params)
                .execute(new QueryVoDialogCallback<QueryVoLzyResponse<String>>(ComplaintActivity.this, true) {
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
                        if (complaintDialog != null && complaintDialog.isShowing()){
                            complaintDialog.dismiss();
                        }
                        if (dialog != null){
                            dialog.setCancel("提示",desc,"确定",true);
                        }
                        myComplaint();
                    }

                    @Override
                    public void onSuccess(Response<QueryVoLzyResponse<String>> response, String desc) {
                        if (complaintDialog != null && complaintDialog.isShowing()){
                            complaintDialog.dismiss();
                        }
                        if (dialog != null){
                            dialog.setCancel("提示",desc,"确定",true);
                        }
                        myComplaint();
                    }
                });
    }

    private void myComplaint() {
        HttpParams params = new HttpParams();
        params.put("page",page);
        params.put("rows",20);
        OkGo.<QueryVoLzyResponse<ComplainListBean>>get(BaseUrl.YT_Base+ BaseUrl.myComplaint)
                .params(params)
                .execute(new QueryVoDialogCallback<QueryVoLzyResponse<ComplainListBean>>(ComplaintActivity.this, true) {
                    @Override
                    public void onFail(int code, String desc) {
                        super.onFail(code, desc);
                        showToast(desc);
                        requestComplete();
                    }

                    @Override
                    public void onError(String desc) {
                        super.onError(desc);
                        showToast(desc);
                    }

                    @Override
                    public void onSuccessNotData(Response<QueryVoLzyResponse<ComplainListBean>> response, String desc) {
                        super.onSuccessNotData(response, desc);
                        showToast(desc);
                        requestComplete();
                    }

                    @Override
                    public void onSuccess(Response<QueryVoLzyResponse<ComplainListBean>> response, String desc) {
                        ComplainListBean data = response.body().getData();
                        List<ComplaintBean> list = data.getList();
                        if (page == 1){
                            complaintAdapter.setDataList(list);
                        }else {
                            complaintAdapter.addAll(list);
                        }
                        requestComplete();
                        if (data.getTotal() == complaintAdapter.getDataList().size()){
                            mRecyclerView.setNoMore(true);
                        }
                        page++;
                    }
                });
    }

    private void requestComplete(){
        complaintAdapter.notifyDataSetChanged();
        mLRecyclerViewAdapter.notifyDataSetChanged();
        if (mRecyclerView != null){
            mRecyclerView.refreshComplete(20);
        }
    }

    private void getComplainClass() {
        OkGo.<QueryVoLzyResponse<List<ComplaintListBean>>>get(BaseUrl.YT_Base+ BaseUrl.getComplainClass)
                .execute(new QueryVoDialogCallback<QueryVoLzyResponse<List<ComplaintListBean>>>(ComplaintActivity.this, true) {
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
                    public void onSuccessNotData(Response<QueryVoLzyResponse<List<ComplaintListBean>>> response, String desc) {
                        super.onSuccessNotData(response, desc);
                        showToast(desc);
                    }

                    @Override
                    public void onSuccess(Response<QueryVoLzyResponse<List<ComplaintListBean>>> response, String desc) {
                        List<ComplaintListBean> data = response.body().getData();
                        listBeans = data;
                    }
                });
    }

    private void appraise(int id,String state) {
        HttpParams params = new HttpParams();
        params.put("complainId",id);
        params.put("complainFeedbackFlag",state);
        OkGo.<QueryVoLzyResponse<String>>post(BaseUrl.YT_Base+ BaseUrl.appraise)
                .params(params)
                .execute(new QueryVoDialogCallback<QueryVoLzyResponse<String>>(ComplaintActivity.this, true) {
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
                        myComplaint();
                    }

                    @Override
                    public void onSuccess(Response<QueryVoLzyResponse<String>> response, String desc) {
                        showToast(desc);
                        myComplaint();
                    }
                });
    }


    @Override
    public void onItemClick(int id,String state) {
        appraise(id,state);
    }

    @Override
    public void submit() {

    }
}
