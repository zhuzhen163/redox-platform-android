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
import android.widget.Toast;

import com.jaeger.library.StatusBarUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.redoxyt.platform.R;
import com.redoxyt.platform.base.BaseActivity;
import com.redoxyt.platform.bean.UserBean;
import com.redoxyt.platform.fragment.MineFragment;
import com.redoxyt.platform.fragment.ReservationCarryFragment;
import com.redoxyt.platform.fragment.StatusQueryFragment;
import com.redoxyt.platform.uitl.ToastUtil;

import java.util.List;

import butterknife.BindView;
import http.callback.QueryVoDialogCallback;
import http.model.QueryVoLzyResponse;
import http.utils.BaseUrl;
import utils.ConfigUtils;

/**
 * 库管员身份
 */
public class MainStorekeeperActivity extends MainActivity{

    @BindView(R.id.frame_layout)
    FrameLayout frameLayout;
    @BindView(R.id.tab_carry)
    RadioButton tab_carry;
    @BindView(R.id.tab_status_query)
    RadioButton tab_status_query;
    @BindView(R.id.tab_mine)
    RadioButton tabMine;
    @BindView(R.id.rg_bottom_bar)
    RadioGroup rgBottomBar;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    ReservationCarryFragment reservationCarryFragment;//预约执行
    StatusQueryFragment statusQueryFragment;//状态查询
    MineFragment mineFragment;//我的


    @Override
    public int setView() {
        return R.layout.activity_main_storekeeper;
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
                if (f instanceof ReservationCarryFragment) {
                    reservationCarryFragment = (ReservationCarryFragment) f;
                } else if (f instanceof StatusQueryFragment) {
                    statusQueryFragment = (StatusQueryFragment) f;
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
        switchToReservationCarryFragment();
        initView();
    }

    public void initView() {

        rgBottomBar.setOnCheckedChangeListener((group, checkedId) -> {

            switch (checkedId) {
                case R.id.tab_carry:
                    switchToReservationCarryFragment();
                    break;
                case R.id.tab_status_query:
                    switchToStatusQueryFragment();
                    break;
                case R.id.tab_mine:
                    switchToMineFragment();
                    break;
            }
        });

    }

    private void switchToReservationCarryFragment() {
        StatusBarUtil.setTranslucentForImageViewInFragment(this, 100, null);
        fragmentTransaction = fragmentManager.beginTransaction();
        hideAllFragment(fragmentTransaction);
        if (reservationCarryFragment != null) {
            fragmentTransaction.show(reservationCarryFragment);
        } else {
            reservationCarryFragment = new ReservationCarryFragment();
            fragmentTransaction.add(R.id.frame_layout, reservationCarryFragment);
        }
        fragmentTransaction.commitAllowingStateLoss();
    }

    private void switchToStatusQueryFragment() {
        StatusBarUtil.setTranslucentForImageViewInFragment(this, 100, null);
        fragmentTransaction = fragmentManager.beginTransaction();
        hideAllFragment(fragmentTransaction);
        if (statusQueryFragment!= null) {
            fragmentTransaction.show(statusQueryFragment);
            statusQueryFragment.refresh();
        } else {
            statusQueryFragment = new StatusQueryFragment();
            fragmentTransaction.add(R.id.frame_layout, statusQueryFragment);
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

        if (reservationCarryFragment != null) {
            fragmentTransaction.hide(reservationCarryFragment);
        }
        if (statusQueryFragment != null) {
            fragmentTransaction.hide(statusQueryFragment);
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

}
