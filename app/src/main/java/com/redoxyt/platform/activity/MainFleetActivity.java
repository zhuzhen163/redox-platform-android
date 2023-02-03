package com.redoxyt.platform.activity;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.jaeger.library.StatusBarUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.redoxyt.platform.R;
import com.redoxyt.platform.bean.UserBean;
import com.redoxyt.platform.fragment.HomeFragment;
import com.redoxyt.platform.fragment.MineFragment;
import com.redoxyt.platform.fragment.ReservationQueryFragment;
import com.redoxyt.platform.uitl.ToastUtil;

import java.util.List;

import butterknife.BindView;
import http.callback.QueryVoDialogCallback;
import http.model.QueryVoLzyResponse;
import http.utils.BaseUrl;
import utils.ConfigUtils;

/**
 * 车队
 */
public class MainFleetActivity extends MainActivity implements HomeFragment.HomeFragmentCallBack{

    @BindView(R.id.frame_layout)
    FrameLayout frameLayout;
    @BindView(R.id.tab_home)
    RadioButton tab_home;
    @BindView(R.id.tab_query)
    RadioButton tab_query;
    @BindView(R.id.tab_mine)
    RadioButton tabMine;
    @BindView(R.id.rg_bottom_bar)
    RadioGroup rgBottomBar;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    HomeFragment homeFragment;//主页
    ReservationQueryFragment reservationQueryFragment;//预约查询
    MineFragment mineFragment;//我的

    @Override
    public int setView() {
        return R.layout.activity_main_fleet;
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
                if (f instanceof HomeFragment) {
                    homeFragment = (HomeFragment) f;
                } else if (f instanceof ReservationQueryFragment) {
                    reservationQueryFragment = (ReservationQueryFragment) f;
                }else if (f instanceof MineFragment) {
                    mineFragment = (MineFragment) f;
                }
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initFragment(savedInstanceState);
        switchToReservationQueryFragment();
        switchToHomePageFragment();
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        myInfo();
    }

    public void initView() {

        rgBottomBar.setOnCheckedChangeListener((group, checkedId) -> {

            switch (checkedId) {
                case R.id.tab_home:
                    switchToHomePageFragment();
                    break;
                case R.id.tab_query:
                    switchToReservationQueryFragment();
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
        if (homeFragment != null) {
            fragmentTransaction.show(homeFragment);
        } else {
            homeFragment = new HomeFragment();
            homeFragment.setCallBack(this);
            fragmentTransaction.add(R.id.frame_layout, homeFragment);
        }
        fragmentTransaction.commitAllowingStateLoss();
    }

    private void switchToReservationQueryFragment() {
        StatusBarUtil.setTranslucentForImageViewInFragment(this, 100, null);
        fragmentTransaction = fragmentManager.beginTransaction();
        hideAllFragment(fragmentTransaction);
        if (reservationQueryFragment!= null) {
            fragmentTransaction.show(reservationQueryFragment);
        } else {
            reservationQueryFragment = new ReservationQueryFragment();
            fragmentTransaction.add(R.id.frame_layout, reservationQueryFragment);
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

        if (homeFragment != null) {
            fragmentTransaction.hide(homeFragment);
        }
        if (reservationQueryFragment != null) {
            fragmentTransaction.hide(reservationQueryFragment);
        }
        if (mineFragment != null) {
            fragmentTransaction.hide(mineFragment);
        }

    }

    /**
     * 双击退出
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

    }

    @Override
    public void callBack(int type) {
        if (type == 1){
            tab_query.setText("预约查询");
        }else if (type == 2){
            tab_query.setText("预约付款");
        }
        rgBottomBar.check(R.id.tab_query);
        if (reservationQueryFragment!= null) {
            reservationQueryFragment.setCheck(type);
        }
    }

}
