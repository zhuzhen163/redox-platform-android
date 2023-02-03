package com.redoxyt.platform.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;


/**
 * Created by ${Smlz} on 2020/6/2
 * QQ：39341349
 */
public class AppMoreOrderFraPagerAdapter extends FragmentPagerAdapter {
    private final List<Fragment> list_fragment;

    public AppMoreOrderFraPagerAdapter(FragmentManager fm, List<Fragment> list_fragment) {
        super(fm);
        this.list_fragment = list_fragment;
    }

    @Override
    public Fragment getItem(int position) {
        return list_fragment.get(position);
    }

    @Override
    public int getCount() {
        return list_fragment.size();
    }


}
