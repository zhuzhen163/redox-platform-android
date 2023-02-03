package com.redoxyt.platform.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.redoxyt.platform.R;
import com.redoxyt.platform.bean.KanBanCarBean;
import com.redoxyt.platform.bean.ReservationBean;

import java.util.List;


public class KanBanAdapter extends ListBaseAdapter<KanBanCarBean> {

    RecyclerView recycleList;
    KanBanCarAdapter carAdapter;
    private Context context;

    private OnItemClickListener mOnItemClickListener;

    public void setmOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public KanBanAdapter(Context context) {
        super(context);
        this.context = context;
    }

    private void setListView(Context context, List<KanBanCarBean.Subs> subs) {
        LinearLayoutManager manager = new LinearLayoutManager(context);
        recycleList.setLayoutManager(manager);
        carAdapter = new KanBanCarAdapter(context);
        recycleList.setAdapter(carAdapter);
        carAdapter.setDataList(subs);
        carAdapter.notifyDataSetChanged();
    }


    @Override
    public int getLayoutId() {
        return R.layout.adapter_kan_ban;
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, int position) {
        KanBanCarBean kanBanEntity = mDataList.get(position);

        TextView tv_yt = holder.getView(R.id.tv_yt);
        ImageView iv_join = holder.getView(R.id.iv_join);
        recycleList = holder.getView(R.id.recycleList);
        setListView(context,kanBanEntity.getSubs());

        tv_yt.setText(kanBanEntity.getPlatformName());

        iv_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnItemClickListener != null){
                    mOnItemClickListener.onItemClick(kanBanEntity,position);
                }
            }
        });
    }

    public interface OnItemClickListener {
        void onItemClick(KanBanCarBean bean,int position);
    }
}
