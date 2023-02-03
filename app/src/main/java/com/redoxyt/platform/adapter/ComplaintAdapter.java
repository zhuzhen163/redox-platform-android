package com.redoxyt.platform.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.redoxyt.platform.R;
import com.redoxyt.platform.bean.ComplaintBean;

import java.util.List;


/**
 * @author zz
 * description：
 */

public class ComplaintAdapter extends ListBaseAdapter<ComplaintBean> {

    private List<ComplaintBean> managerList;
    TextView tv_driver_content;
    TextView tv_title;
    TextView tv_pt_content;
    TextView tv_time;
    TextView tv_status;
    TextView tv_que;
    TextView tv_pt_time;
    ImageView iv_yes,iv_no;
    RelativeLayout rl_pt;
    LinearLayout ll_pt;

    public ComplaintAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_complaint;
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, int position) {
        ComplaintBean listBean = mDataList.get(position);
        tv_title = holder.getView(R.id.tv_title);
        tv_driver_content = holder.getView(R.id.tv_driver_content);
        tv_pt_content = holder.getView(R.id.tv_pt_content);
        tv_time = holder.getView(R.id.tv_time);
        tv_status = holder.getView(R.id.tv_status);
        tv_que = holder.getView(R.id.tv_que);
        rl_pt = holder.getView(R.id.rl_pt);
        tv_pt_time = holder.getView(R.id.tv_pt_time);
        iv_yes = holder.getView(R.id.iv_yes);
        iv_no = holder.getView(R.id.iv_no);
        ll_pt = holder.getView(R.id.ll_pt);

        tv_que.setText(listBean.getComplainClassName()+" > "+listBean.getComplainTypeName());
        tv_time.setText(listBean.getCreateTime());
        tv_driver_content.setText(listBean.getComplainContent());

        int complainStatus = listBean.getComplainStatus();
        if (complainStatus == 0){
            tv_status.setText("处理中");
            tv_status.setBackgroundColor(Color.parseColor("#FF625E"));
            rl_pt.setVisibility(View.GONE);
            ll_pt.setVisibility(View.GONE);
        }else if (complainStatus == 1 || complainStatus == 2){
            tv_status.setText("已处理");
            tv_status.setBackgroundColor(Color.parseColor("#5398ED"));
            rl_pt.setVisibility(View.VISIBLE);
            tv_pt_content.setText("官方回复："+listBean.getComplainResult());
            tv_pt_time.setText(listBean.getUpdateTime());
            ll_pt.setVisibility(View.VISIBLE);
            ll_pt.setVisibility(View.VISIBLE);
            iv_yes.setEnabled(false);
            iv_no.setEnabled(false);
            if (listBean.getComplainFeedbackFlag() == -1){
                iv_yes.setBackgroundResource(R.drawable.icon_yes_1);
                iv_no.setBackgroundResource(R.drawable.icon_no_1);
                iv_yes.setEnabled(true);
                iv_no.setEnabled(true);
            }else if (listBean.getComplainFeedbackFlag() == 0){
                iv_yes.setBackgroundResource(R.drawable.icon_yes_1);
                iv_no.setBackgroundResource(R.drawable.icon_no_2);
            }else if (listBean.getComplainFeedbackFlag() == 1){
                iv_yes.setBackgroundResource(R.drawable.icon_yes_2);
                iv_no.setBackgroundResource(R.drawable.icon_no_1);
            }
        }
        iv_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnItemClickListener != null){
                    mOnItemClickListener.onItemClick(listBean.getComplainId(),"1");
                }
            }
        });

        iv_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnItemClickListener != null){
                    mOnItemClickListener.onItemClick(listBean.getComplainId(),"0");
                }
            }
        });

    }


    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int id, String state);
    }
}
