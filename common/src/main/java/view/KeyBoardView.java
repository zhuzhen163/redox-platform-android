package view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.redoxyt.app.common.R;

import utils.ScreenUtil;


/**
 * @author Sxw
 * @date 2019/12/6.
 * description：密码键盘
 */
public class KeyBoardView extends LinearLayout {
    private GridView gv;
    private Context context;
    private OnKeyBoardItemClickListener listener;

    public KeyBoardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        View view = LayoutInflater.from(context).inflate(R.layout.key_board, null);
        gv = (GridView) view.findViewById(R.id.gv_key_board);
        addView(view);
    }

    public void setOnKeyBoardItemClickListener(OnKeyBoardItemClickListener listener) {
        this.listener = listener;
    }

    public void setKeyBoardData(final String keyArr[]) {
        //绑定适配器
        gv.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return keyArr.length;
            }

            @Override
            public Object getItem(int position) {
                return keyArr[position];
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                ViewHolder holder = null;
                if (convertView == null) {
                    convertView = LayoutInflater.from(context).inflate(R.layout.item_key_broad, null);
                    int w = (ScreenUtil.getScreenWidth(context) - ScreenUtil.dip2px(context, 1)) / 3;
                    int h = (ScreenUtil.getScreenWidth(context) - ScreenUtil.dip2px(context, 1)) / 18 * 3;
                    ////设置convertView的LayoutParams
                    convertView.setLayoutParams(new AbsListView.LayoutParams(w, h));
                    holder = new ViewHolder();
                    holder.keyBoard = (TextView) convertView.findViewById(R.id.tv_key_borad_num);
                    holder.vBg = convertView.findViewById(R.id.v_gray_bg);
                    holder.ivDel = (ImageView) convertView.findViewById(R.id.iv_del);
                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolder) convertView.getTag();
                }
                String content = keyArr[position];
                if ("backspace".equals(content)) {
                    holder.keyBoard.setVisibility(GONE);
                    holder.vBg.setVisibility(VISIBLE);
                    holder.ivDel.setVisibility(VISIBLE);
                } else if (!TextUtils.isEmpty(content) && !"backspace".equals(content)) {
                    holder.keyBoard.setText(content);
                    holder.keyBoard.setVisibility(VISIBLE);
                    holder.vBg.setVisibility(GONE);
                    holder.ivDel.setVisibility(GONE);
                } else if (TextUtils.isEmpty(content)) {
                    holder.keyBoard.setVisibility(GONE);
                    holder.vBg.setVisibility(VISIBLE);
                    holder.ivDel.setVisibility(GONE);
                }
                return convertView;
            }

            class ViewHolder {
                TextView keyBoard;
                View vBg;
                ImageView ivDel;
            }
        });
        //GridView的点击监听
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (listener != null) {
                    if ("backspace".equals(keyArr[position])) {
                        listener.onBackSpaceClick(view);
                    } else if (!TextUtils.isEmpty(keyArr[position]) && !"backspace".equals(keyArr[position])) {
                        listener.onNumItemClick(view, keyArr[position]);
                    }
                    listener.onTotalNum(view);
                }

            }
        });
    }

    public interface OnKeyBoardItemClickListener {
        void onNumItemClick(View view, String num);

        void onBackSpaceClick(View view);

        void onTotalNum(View view);
    }
}
