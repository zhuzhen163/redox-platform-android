package com.redoxyt.platform.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.redoxyt.platform.R;
import com.redoxyt.platform.bean.BillBean;
import com.redoxyt.platform.bean.CarListBean;


/**
 * @author zz
 * description：
 */

public class CarListAdapter extends ListBaseAdapter <CarListBean>{

    TextView tv_carCode,tv_bindTime;
    CardView cv_bind;
    ImageView iv_flag;

    private OnItemClickListener mOnItemClickListener;

    public CarListAdapter(Context context) {
        super(context);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_car_list;
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, int position) {
        CarListBean carListBean = mDataList.get(position);
        tv_carCode = holder.getView(R.id.tv_carCode);
        tv_bindTime = holder.getView(R.id.tv_bindTime);
        cv_bind = holder.getView(R.id.cv_bind);
        iv_flag = holder.getView(R.id.iv_flag);

        tv_carCode.setText(carListBean.getCarCode());
        tv_bindTime.setText("绑定日期："+carListBean.getCreateTime());
        if (carListBean.getFlag() == 1){
            iv_flag.setVisibility(View.VISIBLE);
        }else {
            iv_flag.setVisibility(View.GONE);
        }

        cv_bind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnItemClickListener != null){
                    mOnItemClickListener.onItemClick(position);
                }
            }
        });
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

}
