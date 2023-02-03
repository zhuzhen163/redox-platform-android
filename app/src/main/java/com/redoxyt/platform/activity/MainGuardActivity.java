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
import com.redoxyt.platform.R;
import com.redoxyt.platform.base.BaseActivity;
import com.redoxyt.platform.fragment.EntryCheckFragment;
import com.redoxyt.platform.fragment.MineFragment;
import com.redoxyt.platform.uitl.ToastUtil;

import java.util.List;

import butterknife.BindView;

/**
 * 门卫身份
 */
public class MainGuardActivity extends MainActivity{

    @BindView(R.id.frame_layout)
    FrameLayout frameLayout;
    @BindView(R.id.tab_entry_check)
    RadioButton tab_entry_check;
    @BindView(R.id.tab_mine)
    RadioButton tabMine;
    @BindView(R.id.rg_bottom_bar)
    RadioGroup rgBottomBar;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    EntryCheckFragment entryCheckFragment;//入场检查
    MineFragment mineFragment;//我的


    @Override
    public int setView() {
        return R.layout.activity_main_guard;
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
                if (f instanceof EntryCheckFragment) {
                    entryCheckFragment = (EntryCheckFragment) f;
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
        switchToEntryCheckFragment();
        initView();
    }

    public void initView() {

        rgBottomBar.setOnCheckedChangeListener((group, checkedId) -> {

            switch (checkedId) {
                case R.id.tab_entry_check:
                    switchToEntryCheckFragment();
                    break;
                case R.id.tab_mine:
                    switchToMineFragment();
                    break;
            }
        });

    }

    private void switchToEntryCheckFragment() {
        StatusBarUtil.setTranslucentForImageViewInFragment(this, 100, null);
        fragmentTransaction = fragmentManager.beginTransaction();
        hideAllFragment(fragmentTransaction);
        if (entryCheckFragment != null) {
            fragmentTransaction.show(entryCheckFragment);
        } else {
            entryCheckFragment = new EntryCheckFragment();
            fragmentTransaction.add(R.id.frame_layout, entryCheckFragment);
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

        if (entryCheckFragment != null) {
            fragmentTransaction.hide(entryCheckFragment);
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
        if (entryCheckFragment != null)
        entryCheckFragment.onActivityResult(requestCode,resultCode,data);
    }

}
