package com.redoxyt.platform.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.redoxyt.platform.R;
import com.redoxyt.platform.adapter.CurrencySearchAdapter;
import com.redoxyt.platform.adapter.ReservationQueryAdapter;
import com.redoxyt.platform.base.BaseActivity;
import com.redoxyt.platform.bean.ReservationBean;
import com.redoxyt.platform.bean.ReservationListBean;
import com.redoxyt.platform.uitl.AppUtils;
import com.redoxyt.platform.widget.FlowLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import http.callback.QueryVoDialogCallback;
import http.model.QueryVoLzyResponse;
import http.utils.BaseUrl;
import utils.ConfigUtils;

/**
 * Created by zz.
 * description:搜索
 */

public class CurrencySearchActivity extends BaseActivity {

    @BindView(R.id.ll_history)
    LinearLayout ll_history;
    @BindView(R.id.iv_delete_his)
    ImageView iv_delete_his;
    @BindView(R.id.fl_history)
    FlowLayout fl_history;
    @BindView(R.id.et_search)
    EditText et_search;
    @BindView(R.id.tv_searchNull)
    TextView tv_searchNull;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.iv_clear)
    ImageView iv_clear;
    @BindView(R.id.lv_list)
    LRecyclerView lv_list;
    @BindView(R.id.tv_search)
    TextView tv_search;
    private LRecyclerViewAdapter mLRecyclerViewAdapter = null;
    private CurrencySearchAdapter searchAdapter;
    private int page = 1;
    private String input = "";

    private LayoutInflater mInflater;
    private List<String> mHistoryKeywords = new ArrayList<>();//记录文本

    @Override
    public int setView() {
        return R.layout.acitivty_currency_search;
    }

    @Override
    protected void setStatusBar() {
//        super.setStatusBar();
    }

    @Override
    public void initData() {
        setSearchList();
        mInflater = LayoutInflater.from(this);
        initSearchHistory();

        et_search.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH){
                input=et_search.getText().toString().trim();
                if(!TextUtils.isEmpty(input)){
                    save(input);
                    page = 1;
                    getByReserveList(input);
                    AppUtils.hideInputMethod(CurrencySearchActivity.this);
                }else {
                    showToast("请输入搜索词");
                }
                return true;
            }
            return false;
        });

        lv_list.setOnLoadMoreListener(() ->{
            getByReserveList(input);
        });
    }

    private void setSearchList() {
        lv_list.setLayoutManager(new LinearLayoutManager(CurrencySearchActivity.this));
        lv_list.setHasFixedSize(true);
        lv_list.refresh();
        searchAdapter = new CurrencySearchAdapter(CurrencySearchActivity.this);
        mLRecyclerViewAdapter = new LRecyclerViewAdapter(searchAdapter);
        lv_list.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader); //设置下拉刷新Progress的样式
        lv_list.setAdapter(mLRecyclerViewAdapter);
        lv_list.setLoadMoreEnabled(true);
        lv_list.setPullRefreshEnabled(false);
    }

    @OnClick({R.id.iv_delete_his,R.id.iv_clear,R.id.iv_back,R.id.tv_search})
    public void onViewClicked(View view){
        switch (view.getId()){
            case R.id.iv_delete_his:
                cleanHistory();
                break;
            case R.id.iv_clear:
                et_search.setText("");
                input = "";
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_search:
                input=et_search.getText().toString().trim();
                if(!TextUtils.isEmpty(input)){
                    save(input);
                    page = 1;
                    getByReserveList(input);
                    AppUtils.hideInputMethod(CurrencySearchActivity.this);
                }else {
                    showToast("请输入搜索词");
                }
                break;
        }
    }

    /**
     * 初始化搜索历史记录
     */
    public void initSearchHistory() {
        String history = ConfigUtils.getSearch();
        if (!TextUtils.isEmpty(history)) {
            List<String> list = new ArrayList<String>();
            String[] split = history.split(",");
            for (int i = 0; i < split.length; i++) {
                if (i<8){
                    list.add(split[i]);
                }
            }
            mHistoryKeywords = list;
        }
        if (mHistoryKeywords.size() > 0) {
            searchHistory();
        }

    }

    /**
     * 储存搜索历史
     */
    public void save(String input) {
        String oldText = ConfigUtils.getSearch();
        if (!TextUtils.isEmpty(input) && !(oldText.contains(input))) {
            mHistoryKeywords.add(0, input);
            if (mHistoryKeywords.size() > 8) {
                mHistoryKeywords.remove(mHistoryKeywords.size()-1);
            }
            searchHistory();

            ConfigUtils.saveSearch(input + "," + oldText);
        }

    }

    private void searchHistory(){
        fl_history.removeAllViews();
        for (int i = 0; i < mHistoryKeywords.size(); i++) {
            TextView tv = (TextView) mInflater.inflate(R.layout.search_label_tv, fl_history, false);
            tv.setText(mHistoryKeywords.get(i));
            final String str = tv.getText().toString();
            //点击事件
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    et_search.setText(str);
                    AppUtils.hideInputMethod(CurrencySearchActivity.this);
                    input = str;
                    page = 1;
                    getByReserveList(input);
                }
            });
            fl_history.addView(tv);
        }
    }

    /**
     * 清除历史纪录
     */
    public void cleanHistory() {
        ConfigUtils.saveSearch("");
        fl_history.removeAllViews();
        mHistoryKeywords.clear();
    }

    /**
     * 收索预约信息
     */
    public void getByReserveList(String input) {
        HttpParams params = new HttpParams();
        int userFlag = ConfigUtils.getUserFlag();
        if (userFlag == 4){
            params.put("fleetId", ConfigUtils.getGroupId());
        }
        params.put("searchText",input);
        params.put("page",page);
        params.put("rows",20);
        OkGo.<QueryVoLzyResponse<ReservationListBean>>get(BaseUrl.YT_Base + BaseUrl.getByReserveList)
                .params(params)
                .execute(new QueryVoDialogCallback<QueryVoLzyResponse<ReservationListBean>>(CurrencySearchActivity.this, true) {
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
                    public void onSuccessNotData(Response<QueryVoLzyResponse<ReservationListBean>> response, String desc) {
                        super.onSuccessNotData(response, desc);
                    }

                    @Override
                    public void onSuccess(Response<QueryVoLzyResponse<ReservationListBean>> response, String desc) {
                        ReservationListBean data = response.body().getData();
                        if (data != null){
                            List<ReservationBean> reservationList = data.getReservationList();
                            if (page == 1){
                                searchAdapter.setDataList(reservationList);
                            }else {
                                searchAdapter.addAll(reservationList);
                            }
                            requestComplete();
                            if (data.getTotal() == searchAdapter.getDataList().size()){
                                lv_list.setNoMore(true);
                            }
                            page++;
                        }
                    }
                });
    }

    /**
     *
     */
    private void requestComplete(){
        try {
            mLRecyclerViewAdapter.notifyDataSetChanged();
            lv_list.refreshComplete(20);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
