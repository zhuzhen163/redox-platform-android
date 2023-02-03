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
import com.redoxyt.platform.fragment.BuildReservationFragment;
import com.redoxyt.platform.fragment.EfficiencyFragment;
import com.redoxyt.platform.fragment.KanBanFragment;
import com.redoxyt.platform.fragment.MineFragment;
import com.redoxyt.platform.fragment.ReservationMsgFragment;
import com.redoxyt.platform.fragment.ReservationQueryFragment;
import com.redoxyt.platform.uitl.ToastUtil;

import java.util.List;

import butterknife.BindView;
import http.callback.QueryVoDialogCallback;
import http.model.QueryVoLzyResponse;
import http.utils.BaseUrl;
import utils.ConfigUtils;

/**
 * 管理员/紧急预约身份
 */
public class MainUrgentActivity extends MainActivity {

    @BindView(R.id.frame_layout)
    FrameLayout frameLayout;
    @BindView(R.id.tab_kanban)
    RadioButton tab_kanban;
    @BindView(R.id.tab_reservation_msg)
    RadioButton tab_reservation_msg;
    @BindView(R.id.tab_build)
    RadioButton tab_build;
    @BindView(R.id.tab_mine)
    RadioButton tabMine;
    @BindView(R.id.rg_bottom_bar)
    RadioGroup rgBottomBar;
    @BindView(R.id.tab_efficiency)
    RadioButton tab_efficiency;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    KanBanFragment kanBanFragment;//看板
    ReservationMsgFragment msgFragment;//预约信息
    EfficiencyFragment efficiencyFragment;//效率信息
    BuildReservationFragment buildReservationFragment;//新建紧急预约
    MineFragment mineFragment;//我的

    @Override
    public int setView() {
        return R.layout.activity_main_urgent;
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
                if (f instanceof KanBanFragment) {
                    kanBanFragment = (KanBanFragment) f;
                } else if (f instanceof ReservationMsgFragment) {
                    msgFragment = (ReservationMsgFragment) f;
                } else if (f instanceof EfficiencyFragment) {
                    efficiencyFragment = (EfficiencyFragment) f;
                } else if (f instanceof ReservationQueryFragment) {
                    buildReservationFragment = (BuildReservationFragment) f;
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
        switchToKanBanPageFragment();
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
                case R.id.tab_kanban:
                    switchToKanBanPageFragment();
                    break;
                case R.id.tab_reservation_msg:
                    switchToReservationMsgFragment();
                    break;
                case R.id.tab_efficiency:
                    switchToEfficiencyFragment();
                    break;
                case R.id.tab_build:
                    switchToBuildReservationFragment();
                    break;
                case R.id.tab_mine:
                    switchToMineFragment();
                    break;
            }
        });

    }

    private void switchToKanBanPageFragment() {
        StatusBarUtil.setTranslucentForImageViewInFragment(this, 100, null);
        fragmentTransaction = fragmentManager.beginTransaction();
        hideAllFragment(fragmentTransaction);
        if (kanBanFragment != null) {
            fragmentTransaction.show(kanBanFragment);
            kanBanFragment.refreshData();
        } else {
            kanBanFragment = new KanBanFragment();
            fragmentTransaction.add(R.id.frame_layout, kanBanFragment);
        }
        fragmentTransaction.commitAllowingStateLoss();
    }

    private void switchToReservationMsgFragment() {
        StatusBarUtil.setTranslucentForImageViewInFragment(this, 100, null);
        fragmentTransaction = fragmentManager.beginTransaction();
        hideAllFragment(fragmentTransaction);
        if (msgFragment != null) {
            fragmentTransaction.show(msgFragment);
        } else {
            msgFragment = new ReservationMsgFragment();
            fragmentTransaction.add(R.id.frame_layout, msgFragment);
        }
        fragmentTransaction.commitAllowingStateLoss();
    }

    private void switchToEfficiencyFragment() {
        StatusBarUtil.setTranslucentForImageViewInFragment(this, 100, null);
        fragmentTransaction = fragmentManager.beginTransaction();
        hideAllFragment(fragmentTransaction);
        if (efficiencyFragment != null) {
            fragmentTransaction.show(efficiencyFragment);
        } else {
            efficiencyFragment = new EfficiencyFragment();
            fragmentTransaction.add(R.id.frame_layout, efficiencyFragment);
        }
        fragmentTransaction.commitAllowingStateLoss();
    }

    private void switchToBuildReservationFragment() {
        StatusBarUtil.setTranslucentForImageViewInFragment(this, 100, null);
        fragmentTransaction = fragmentManager.beginTransaction();
        hideAllFragment(fragmentTransaction);
        if (buildReservationFragment!= null) {
            fragmentTransaction.show(buildReservationFragment);
            buildReservationFragment.onResume();
        } else {
            buildReservationFragment = new BuildReservationFragment();
            fragmentTransaction.add(R.id.frame_layout, buildReservationFragment);
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

        if (kanBanFragment != null) {
            fragmentTransaction.hide(kanBanFragment);
        }
        if (msgFragment != null) {
            fragmentTransaction.hide(msgFragment);
        }
        if (efficiencyFragment != null) {
            fragmentTransaction.hide(efficiencyFragment);
        }
        if (buildReservationFragment != null) {
            fragmentTransaction.hide(buildReservationFragment);
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
