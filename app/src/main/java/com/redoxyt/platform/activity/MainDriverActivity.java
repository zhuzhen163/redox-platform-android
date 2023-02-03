package com.redoxyt.platform.activity;


import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.jaeger.library.StatusBarUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.redoxyt.platform.R;
import com.redoxyt.platform.base.BaseActivity;
import com.redoxyt.platform.bean.AiBuildResetBean;
import com.redoxyt.platform.bean.UserBean;
import com.redoxyt.platform.fragment.AdmissionNoticeFragment;
import com.redoxyt.platform.fragment.MineFragment;
import com.redoxyt.platform.fragment.MyReservationFragment;
import com.redoxyt.platform.fragment.ReservationCodeFragment;
import com.redoxyt.platform.uitl.ToastUtil;
import com.redoxyt.platform.widget.SystemTTS;

import java.util.List;

import butterknife.BindView;
import http.callback.QueryVoDialogCallback;
import http.model.QueryVoLzyResponse;
import http.utils.BaseUrl;
import utils.ConfigUtils;

/**
 * 司机身份
 */
public class MainDriverActivity extends MainActivity {

    @BindView(R.id.frame_layout)
    FrameLayout frameLayout;
    @BindView(R.id.tab_notice)
    RadioButton tab_notice;
    @BindView(R.id.tab_reservation)
    RadioButton tab_reservation;
    @BindView(R.id.tab_reservation_code)
    RadioButton tab_reservation_code;
    @BindView(R.id.tab_mine)
    RadioButton tabMine;
    @BindView(R.id.rg_bottom_bar)
    RadioGroup rgBottomBar;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    AdmissionNoticeFragment noticeFragment;//入场须知
    MyReservationFragment myReservationFragment;//我的预约
    ReservationCodeFragment reservationCodeFragment;//预约码
    MineFragment mineFragment;//我的
    private List<AiBuildResetBean> data;


    @Override
    public int setView() {
        return R.layout.activity_main_driver;
    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setTranslucentForImageViewInFragment(this, 100, null);
    }

    @Override
    public void initData() {
    }

    private void initFragment(Bundle savedInstanceState) {
        fragmentManager = getSupportFragmentManager();
        if (savedInstanceState != null) {
            @SuppressLint("RestrictedApi") List<Fragment> fragments = getSupportFragmentManager().getFragments();
            for (Fragment f : fragments) {
                if (f instanceof AdmissionNoticeFragment) {
                    noticeFragment = (AdmissionNoticeFragment) f;
                } else if (f instanceof MyReservationFragment) {
                    myReservationFragment = (MyReservationFragment) f;
                } else if (f instanceof ReservationCodeFragment) {
                    reservationCodeFragment = (ReservationCodeFragment) f;
                } else if (f instanceof MineFragment) {
                    mineFragment = (MineFragment) f;
                }
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initFragment(savedInstanceState);
        switchToHomePageFragment();
        initView();
        SystemTTS.getInstance(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (BaseUrl.TO_MAIN_MYRESERVATIONFRAGMENT == 1) {
            BaseUrl.TO_MAIN_MYRESERVATIONFRAGMENT = 0;
            switchToMyReservationFragment();
        }
    }

    public void initView() {

        rgBottomBar.setOnCheckedChangeListener((group, checkedId) -> {

            switch (checkedId) {
                case R.id.tab_notice:
                    switchToHomePageFragment();
                    break;
                case R.id.tab_reservation:
                    switchToMyReservationFragment();
                    break;
                case R.id.tab_reservation_code:
                    switchToReservationCodeFragment();
                    break;
                case R.id.tab_mine:
                    switchToMineFragment();
                    break;
            }
        });

    }

    private void switchToHomePageFragment() {
        StatusBarUtil.setTranslucentForImageViewInFragment(this, 100, null);
        fragmentTransaction = fragmentManager.beginTransaction();
        hideAllFragment(fragmentTransaction);
        if (noticeFragment != null) {
            fragmentTransaction.show(noticeFragment);
        } else {
            noticeFragment = new AdmissionNoticeFragment();
            fragmentTransaction.add(R.id.frame_layout, noticeFragment);
        }
        fragmentTransaction.commitAllowingStateLoss();
    }

    private void switchToMyReservationFragment() {
        StatusBarUtil.setTranslucentForImageViewInFragment(this, 100, null);
        fragmentTransaction = fragmentManager.beginTransaction();
        hideAllFragment(fragmentTransaction);
        if (myReservationFragment != null) {
            fragmentTransaction.show(myReservationFragment);
            myReservationFragment.getByReserveList();
        } else {
            myReservationFragment = new MyReservationFragment();
            fragmentTransaction.add(R.id.frame_layout, myReservationFragment);
        }
        fragmentTransaction.commitAllowingStateLoss();
    }


    private void switchToReservationCodeFragment() {
        StatusBarUtil.setTranslucentForImageViewInFragment(this, 100, null);
        fragmentTransaction = fragmentManager.beginTransaction();
        hideAllFragment(fragmentTransaction);
        if (reservationCodeFragment != null) {
            fragmentTransaction.show(reservationCodeFragment);
            reservationCodeFragment.getByReservationList();
        } else {
            reservationCodeFragment = new ReservationCodeFragment();
            fragmentTransaction.add(R.id.frame_layout, reservationCodeFragment);
        }
        fragmentTransaction.commitAllowingStateLoss();
    }


    private void switchToMineFragment() {
        StatusBarUtil.setTranslucentForImageViewInFragment(this, 0, null);
        fragmentTransaction = fragmentManager.beginTransaction();
        hideAllFragment(fragmentTransaction);
        if (mineFragment != null) {
            fragmentTransaction.show(mineFragment);
            mineFragment.myInfo();
        } else {
            mineFragment = new MineFragment();
            fragmentTransaction.add(R.id.frame_layout, mineFragment);
        }
        fragmentTransaction.commitAllowingStateLoss();
    }

    private void hideAllFragment(FragmentTransaction fragmentTransaction) {

        if (noticeFragment != null) {
            fragmentTransaction.hide(noticeFragment);
        }
        if (myReservationFragment != null) {
            fragmentTransaction.hide(myReservationFragment);
        }
        if (reservationCodeFragment != null) {
            fragmentTransaction.hide(reservationCodeFragment);
        }
        if (mineFragment != null) {
            fragmentTransaction.hide(mineFragment);
        }

    }
    /**
     * 多仓完成订单,操作剩余仓库
     */
    public void remainMuchWarehouse() {
        OkGo.<QueryVoLzyResponse<List<AiBuildResetBean>>>get(BaseUrl.YT_Base + BaseUrl.remainMuchWarehouse)
                .execute(new QueryVoDialogCallback<QueryVoLzyResponse<List<AiBuildResetBean>>>(this, true) {
                    @Override
                    public void onFail(int code, String desc) {
                        showToast(desc);
                        super.onFail(code, desc);
                    }

                    @Override
                    public void onError(String desc) {
                        super.onError(desc);
                        showToast(desc);
                    }

                    @Override
                    public void onSuccess(Response<QueryVoLzyResponse<List<AiBuildResetBean>>> response, String desc) {
                        List<AiBuildResetBean> data = response.body().getData();
                        if (data != null && data.size() > 0) {
                            startActivity(OrderPlanWarnActivity.class);
                        }
                    }
                });
    }
    /**
     * 双击退出
     *
     * @param keyCode
     * @param event
     * @return
     */
    private long mExitTime;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 3000) {
                ToastUtil.showToast("再按一次退出程序");
                mExitTime = System.currentTimeMillis();
            } else {
                moveTaskToBack(true);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (myReservationFragment != null) {
            myReservationFragment.onActivityResult(requestCode, resultCode, data);
        }
    }


}
