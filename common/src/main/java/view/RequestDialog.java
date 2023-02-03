package view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.redoxyt.app.common.R;
import com.bumptech.glide.Glide;

/**
 * Created by Administrator on 2018/7/6/006.
 */

public class RequestDialog extends Dialog {
    Context mContext;

    public RequestDialog(@NonNull Context context) {
        super(context, R.style.dialog_background);
        this.mContext = context;
    }

    AnimationDrawable anim;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.dialog_background_layout, null);
        imageView = (ImageView) inflate.findViewById(R.id.img);
        Glide.with(mContext).asGif().load(R.mipmap.gif_loading).into(imageView);
        setContentView(inflate);
    }


    @Override
    protected void onStop() {
        super.onStop();
//        if (anim != null) {
//            anim.stop();
//        }
    }
}
