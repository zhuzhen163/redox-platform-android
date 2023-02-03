package util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.redoxyt.app.common.R;


/**
 * @author Sxw
 * @date 2019/6/21.
 * description：自定义tb
 */

public class ViewUtil {
    //获取对应未知的tablayout
    public static View getTabView(Context context, int position, String titleArray[]) {
        View view = LayoutInflater.from(context).inflate(R.layout.tab_item_layout, null);
        ImageView ivTab = view.findViewById(R.id.iv_tab);
        ivTab.setVisibility(View.GONE);
        TextView txt_title = view.findViewById(R.id.tab_item_title);
        txt_title.setText(titleArray[position]);
        TextView count = view.findViewById(R.id.tab_count);
        TextView txt_title_line = view.findViewById(R.id.tab_item_title_line);
        txt_title_line.setBackgroundResource(R.drawable.selector_tab);
        return view;
    }

    public static Drawable getDrawble(Context context, int resources) {
        Drawable drawable = context.getResources().getDrawable(
                resources);
        // / 这一步必须要做,否则不会显示.
        drawable.setBounds(0, 0, drawable.getMinimumWidth(),
                drawable.getMinimumHeight());
        return drawable;
    }

}
