package com.redoxyt.platform.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.redoxyt.platform.R;
import com.redoxyt.platform.bean.WarehouseBean;

import java.util.List;

/**
 * Created by zz.
 * description:
 */

public class ManagerWarehouseAdapter extends RecyclerView.Adapter<ManagerWarehouseAdapter.MyHolder> {

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(WarehouseBean bean);
    }

    private List<WarehouseBean> list;
    public ManagerWarehouseAdapter(List<WarehouseBean> list){
        this.list = list;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_manager_warehouse,viewGroup,false);
        MyHolder myHolder = new MyHolder(view);
        return myHolder;
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(List<WarehouseBean> list){
        this.list = list;
        notifyDataSetChanged();
    }

    // 绑定类
    public class MyHolder extends RecyclerView.ViewHolder {
        TextView tv_warehouseCode,tv_warehouseName,tv_warehouseAddress;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            tv_warehouseCode = itemView.findViewById(R.id.tv_warehouseCode);
            tv_warehouseName = itemView.findViewById(R.id.tv_warehouseName);
            tv_warehouseAddress = itemView.findViewById(R.id.tv_warehouseAddress);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, final int position) {
        WarehouseBean bean = list.get(position);
        myHolder.tv_warehouseCode.setText("仓 库 码："+bean.getPlatformWarehouseCode());
        myHolder.tv_warehouseName.setText("仓库名称："+bean.getPlatformWarehouseName());
        myHolder.tv_warehouseAddress.setText("仓库地址："+bean.getGroupAddress());
        myHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnItemClickListener != null){
                    mOnItemClickListener.onItemClick(bean);
                }
            }
        });
    }
}
