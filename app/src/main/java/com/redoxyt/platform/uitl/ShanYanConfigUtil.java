package com.redoxyt.platform.uitl;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.chuanglan.shanyan_sdk.OneKeyLoginManager;
import com.chuanglan.shanyan_sdk.tool.ShanYanUIConfig;
import com.redoxyt.platform.R;

import http.utils.BaseUrl;


public class ShanYanConfigUtil {
    /**
     * 闪验三网运营商授权页配置类
     *
     * @param context
     * @return
     */

    //沉浸式竖屏样式
    public static ShanYanUIConfig getCJSConfig(final Context context) {
        /************************************************自定义控件**************************************************************/
        Drawable logBtnImgPath = context.getResources().getDrawable(R.drawable.yt_one_login_button);
        Drawable backgruond = context.getResources().getDrawable(R.drawable.yt_one_login_bg);
        Drawable returnBg = context.getResources().getDrawable(R.drawable.shanyan_demo_return_left_bg);
        Drawable uncheckedImgPath = context.getResources().getDrawable(R.drawable.yt_chex_nm);
        Drawable checkedImgPath = context.getResources().getDrawable(R.drawable.yt_chex_on);
        //loading自定义加载框
        LayoutInflater inflater = LayoutInflater.from(context);
        RelativeLayout view_dialog = (RelativeLayout) inflater.inflate(R.layout.shanyan_demo_dialog_layout, null);
        RelativeLayout.LayoutParams mLayoutParams3 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        view_dialog.setLayoutParams(mLayoutParams3);
        view_dialog.setVisibility(View.GONE);

        LayoutInflater inflater1 = LayoutInflater.from(context);
        RelativeLayout relativeLayout = (RelativeLayout) inflater1.inflate(R.layout.shanyan_demo_other_login_item, null);
        RelativeLayout.LayoutParams layoutParamsOther = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParamsOther.setMargins(0, AbScreenUtils.dp2px(context, 330), 0, 0);
        layoutParamsOther.addRule(RelativeLayout.CENTER_HORIZONTAL);
        relativeLayout.setLayoutParams(layoutParamsOther);
        ShanYanUIConfig uiConfig = new ShanYanUIConfig.Builder()
                .setDialogTheme(false, 720, 1280, 0, 0, true)
                .setActivityTranslateAnim("shanyan_demo_fade_in_anim", "shanyan_dmeo_fade_out_anim")
                //授权页导航栏：
                .setNavColor(Color.parseColor("#ffffff"))  //设置导航栏颜色
                .setNavText("")  //设置导航栏标题文字
                .setNavReturnBtnWidth(25)
                .setNavReturnBtnHeight(25)
                .setAuthBGImgPath(backgruond)
                .setLogoHidden(true)   //是否隐藏logo
                .setDialogDimAmount(0f)
                .setNavReturnImgPath(returnBg)
                .setNavReturnBtnOffsetX(15)
                .setNavReturnBtnOffsetY(5)
                .setFullScreen(true)
                .setStatusBarHidden(true)

                //授权页号码栏：
                .setNumberColor(Color.parseColor("#333333"))  //设置手机号码字体颜色
                .setNumFieldOffsetY(180)    //设置号码栏相对于标题栏下边缘y偏移
                .setNumberSize(25)
                .setNumFieldHeight(30)

                //授权页登录按钮：
                .setLogBtnText("本机号码一键登录")  //设置登录按钮文字
                .setLogBtnTextColor(Color.parseColor("#FAFAFF"))   //设置登录按钮文字颜色
                .setLogBtnImgPath(logBtnImgPath)   //设置登录按钮图片
                .setLogBtnTextSize(15)
                .setLogBtnHeight(45)
                .setLogBtnOffsetY(260)
                .setLogBtnWidth(AbScreenUtils.getScreenWidth(context, true) - 100)

                .addCustomView(relativeLayout, false, false, (context1, view) -> {
                    OneKeyLoginManager.getInstance().finishAuthActivity();
                })
                //授权页隐私栏：
                .setAppPrivacyOne("厂厂宝用户协议", BaseUrl.YT_BaseH5+"apptraderules")  //设置开发者隐私条款1名称和URL(名称，url)
                .setPrivacyText("登录即同意", "和", "、", "、", "并授权闪验获取手机号")
                .setPrivacyOffsetBottomY(50)//设置隐私条款相对于屏幕下边缘y偏
                .setPrivacyState(false)
                .setPrivacyTextSize(12)
                .setPrivacyOffsetX(15)
                .setAppPrivacyColor(Color.parseColor("#333333"),Color.parseColor("#F48833"))
                .setUncheckedImgPath(uncheckedImgPath)
                .setCheckedImgPath(checkedImgPath)
                .setShanYanSloganTextColor(Color.parseColor("#ffffff"))

                .setLoadingView(view_dialog)
                .setSloganOffsetBottomY(20)
                .setSloganTextColor(Color.parseColor("#333333"))
                .build();
        return uiConfig;

    }

}
