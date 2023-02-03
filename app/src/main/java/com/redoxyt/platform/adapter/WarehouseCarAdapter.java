package com.redoxyt.platform.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.redoxyt.platform.R;
import com.redoxyt.platform.activity.WarehouseCarStateActivity;
import com.redoxyt.platform.bean.KanBanStateInfosBean;
import com.redoxyt.platform.bean.KanBanStatesBean;
import com.redoxyt.platform.bean.WarehouseCarListBean;

import java.util.List;


public class WarehouseCarAdapter extends ListBaseAdapter<WarehouseCarListBean> {

    RecyclerView list_working;
    RecyclerView list_no_report;
    RecyclerView list_report;
    CarStateAdapter carWorkingAdapter;
    CarStateNoReportAdapter carNoReportAdapter;
    CarStateReportAdapter carReportAdapter;
    TextView tv_working,tv_no_report,tv_report;
    ImageView iv_workMore,iv_noReportMore,iv_reportMore,iv_current;
    private WarehouseCarStateActivity context;
    private String platformId;

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(String platformId,int reserveStatus,String stateNum);
    }

    public WarehouseCarAdapter(WarehouseCarStateActivity context) {
        super(context);
        this.context = context;
    }

    private void setWorkingListView(Context context, List<KanBanStateInfosBean> subs) {
        LinearLayoutManager manager = new LinearLayoutManager(context);
        list_working.setLayoutManager(manager);
        carWorkingAdapter = new CarStateAdapter(context);
        list_working.setAdapter(carWorkingAdapter);
        carWorkingAdapter.setDataList(subs);
        carWorkingAdapter.notifyDataSetChanged();
    }
    private void setNoReportListView(Context context, List<KanBanStateInfosBean> subs) {
        LinearLayoutManager manager = new LinearLayoutManager(context);
        list_no_report.setLayoutManager(manager);
        carNoReportAdapter = new CarStateNoReportAdapter(context);
        list_no_report.setAdapter(carNoReportAdapter);
        carNoReportAdapter.setDataList(subs);
        carNoReportAdapter.notifyDataSetChanged();
    }
    private void setReportListView(Context context, List<KanBanStateInfosBean> subs) {
        LinearLayoutManager manager = new LinearLayoutManager(context);
        list_report.setLayoutManager(manager);
        carReportAdapter = new CarStateReportAdapter(context);
        list_report.setAdapter(carReportAdapter);
        carReportAdapter.setDataList(subs);
        carReportAdapter.notifyDataSetChanged();
    }


    @Override
    public int getLayoutId() {
        return R.layout.adapter_warehouse_car;
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, int position) {
        WarehouseCarListBean kanBanEntity = mDataList.get(position);

        TextView tv_yt = holder.getView(R.id.tv_yt);
        list_working = holder.getView(R.id.list_working);
        list_no_report = holder.getView(R.id.list_no_report);
        list_report = holder.getView(R.id.list_report);
        tv_working = holder.getView(R.id.tv_working);
        tv_no_report = holder.getView(R.id.tv_no_report);
        tv_report = holder.getView(R.id.tv_report);
        iv_workMore = holder.getView(R.id.iv_workMore);
        iv_noReportMore = holder.getView(R.id.iv_noReportMore);
        iv_reportMore = holder.getView(R.id.iv_reportMore);
        iv_current = holder.getView(R.id.iv_current);
        if (this.platformId.equals(kanBanEntity.getPlatformId())){
            iv_current.setVisibility(View.VISIBLE);
        }else {
            iv_current.setVisibility(View.GONE);
        }
        List<KanBanStatesBean> kanBanStates = kanBanEntity.getKanBanStates();
        if (kanBanStates.size()>0){
            for (int i = 0; i < kanBanStates.size(); i++) {
                KanBanStatesBean kanBanStatesBean = kanBanStates.get(i);
                int reserveStatus = kanBanStatesBean.getReserveStatus();
                if (reserveStatus == 2||reserveStatus == 3){//作业中
                    tv_working.setText("作业中 "+kanBanStatesBean.getCountCar());
                    setWorkingListView(context,kanBanStatesBean.getKanBanStateInfos());
                }else if (reserveStatus == 0){//未报到
                    tv_no_report.setText("未报到 "+kanBanStatesBean.getCountCar());
                    setNoReportListView(context,kanBanStatesBean.getKanBanStateInfos());
                }else {//已完成
                    tv_report.setText("已完成 "+kanBanStatesBean.getCountCar());
                    setReportListView(context,kanBanStatesBean.getKanBanStateInfos());
                }
            }
        }
        tv_yt.setText(kanBanEntity.getPlatformName());

        iv_workMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnItemClickListener != null){
                    KanBanStatesBean kanBanStatesBean = kanBanEntity.getKanBanStates().get(0);
                    int countCar = 0;
                    if (kanBanStatesBean != null){
                        countCar = kanBanStatesBean.getCountCar();
                    }
                    mOnItemClickListener.onItemClick(kanBanEntity.getPlatformId(),3,"作业中 "+countCar);
                }
            }
        });
        iv_noReportMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnItemClickListener != null){
                    KanBanStatesBean kanBanStatesBean = kanBanEntity.getKanBanStates().get(1);
                    int countCar = 0;
                    if (kanBanStatesBean != null){
                        countCar = kanBanStatesBean.getCountCar();
                    }
                    mOnItemClickListener.onItemClick(kanBanEntity.getPlatformId(),0,"未报到 "+countCar);
                }
            }
        });
        iv_reportMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnItemClickListener != null){
                    KanBanStatesBean kanBanStatesBean = kanBanEntity.getKanBanStates().get(2);
                    int countCar = 0;
                    if (kanBanStatesBean != null){
                        countCar = kanBanStatesBean.getCountCar();
                    }
                    mOnItemClickListener.onItemClick(kanBanEntity.getPlatformId(),4,"已完成 "+countCar);
                }
            }
        });

//        if(position == getItemCount()){//已经到达列表的底部
//            context.setData();
//        }
    }

    public void setPlatformId(String platformId){
        this.platformId = platformId;
    }
}
