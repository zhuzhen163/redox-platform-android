package com.redoxyt.platform.fragment;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.redoxyt.platform.R;
import com.redoxyt.platform.activity.BuildReservationActivity;
import com.redoxyt.platform.base.BaseFragment;
import com.redoxyt.platform.bean.BannerBean;
import com.redoxyt.platform.widget.ImageCycleView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import http.callback.QueryVoDialogCallback;
import http.model.QueryVoLzyResponse;
import http.utils.BaseUrl;
import util.StatusBarUtil;

public class HomeFragment extends BaseFragment {

    @BindView(R.id.icv_topView)
    ImageCycleView icv_topView;
    @BindView(R.id.tv_reservationPay)
    TextView tv_reservationPay;
    @BindView(R.id.tv_reservationQuery)
    TextView tv_reservationQuery;
    @BindView(R.id.tv_buildReservation)
    TextView tv_buildReservation;

    private List<ImageCycleView.ImageInfo> list_banner = new ArrayList<>();

    public HomeFragmentCallBack callBack;

    public void setCallBack(HomeFragmentCallBack callBack) {
        this.callBack = callBack;
    }

    public interface HomeFragmentCallBack{
        void callBack(int type);
    }

    @Override
    protected void initData() {
        bannerList();

        icv_topView.setOnPageClickListener(new ImageCycleView.OnPageClickListener() {
            @Override
            public void onClick(View imageView, ImageCycleView.ImageInfo imageInfo) {


            }
        });
    }

    @OnClick({R.id.tv_buildReservation,R.id.tv_reservationQuery,R.id.tv_reservationPay})
    public void onViewClicked(View view){
        switch (view.getId()){
            case R.id.tv_buildReservation:
                startActivity(BuildReservationActivity.class);
                break;
            case R.id.tv_reservationQuery:
                if (callBack != null){
                    callBack.callBack(1);
                }
                break;
            case R.id.tv_reservationPay:
                if (callBack != null){
                    callBack.callBack(2);
                }
                break;
        }
    }

    @Override
    protected void setStatusBar() {
       StatusBarUtil.setTranslucentForImageViewInFragment(getActivity(), 100, null);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_home;
    }

    private void bannerList(){
        OkGo.<QueryVoLzyResponse<List<BannerBean>>>get(BaseUrl.YT_Base+BaseUrl.bannerList)
                .execute(new QueryVoDialogCallback<QueryVoLzyResponse<List<BannerBean>>>(getActivity(), false) {
                    @Override
                    public void onFail(int code, String desc) {
                        super.onFail(code, desc);
                    }

                    @Override
                    public void onError(String desc) {
                        super.onError(desc);

                    }

                    @Override
                    public void onSuccessNotData(Response<QueryVoLzyResponse<List<BannerBean>>> response, String desc) {
                        super.onSuccessNotData(response, desc);
                    }

                    @Override
                    public void onSuccess(Response<QueryVoLzyResponse<List<BannerBean>>> response, String desc) {
                        List<BannerBean> data = response.body().getData();
                        if (data != null && data.size()>0){
                            for (int i = 0; i < data.size(); i++) {
                                BannerBean bean = data.get(i);
                                ImageCycleView.ImageInfo info = new ImageCycleView.ImageInfo(bean.getItemPic(),bean.getItemDesc(),bean.getItemLink());
                                list_banner.add(info);
                            }
                            icv_topView.loadData(list_banner, imageInfo -> {
                                ImageView imageView = new ImageView(getActivity());
                                Glide.with(getActivity()).load(imageInfo.image.toString()).into(imageView);
                                return imageView;
                            });
                        }
                    }
                });
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
        }
    }

}
