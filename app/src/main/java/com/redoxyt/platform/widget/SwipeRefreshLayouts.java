package com.redoxyt.platform.widget;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;

/**
 * Created by zz.
 * description:
 */

public class SwipeRefreshLayouts extends android.support.v4.widget.SwipeRefreshLayout{
    public SwipeRefreshLayouts(Context context)
    {
        super(context);
    }

    public SwipeRefreshLayouts(Context context,AttributeSet attrs)
    {
        super(context,attrs);
    }

    @Override
    public boolean canChildScrollUp()
    {
        View target=getChildAt(0);
        if(target instanceof AbsListView)
        {
            final AbsListView absListView=(AbsListView)target;
            return absListView.getChildCount()>0
                    &&(absListView.getFirstVisiblePosition()>0||absListView.getChildAt(0)
                    .getTop()<absListView.getPaddingTop());
        }
        else
            return ViewCompat.canScrollVertically(target,-1);
    }
}
