package com.redoxyt.platform.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * 通用的RecylerView的adapter，简单使用时只需要通过匿名内部类的方式重写convert方法，一行代码就可以创建adapter，避免重复的viewholder的创建代码。
 * 如：
 * mAdapter = new CommonAdapter<String>(this, R.layout.item_list, mDatas)
 {
 @Override
 protected void convert(ViewHolder holder, String s, int position)
 {
 //给textview赋值直接通过settext（id，value）的方式，imageview类似
 holder.setText(R.id.id_item_list_title, s + " : " + holder.getAdapterPosition() + " , " + holder.getLayoutPosition());
 }
 };
 create by dzy at 2017/8/7
 * @param <T>
 */
public abstract class CommonAdapter<T> extends RecyclerView.Adapter<ViewHolder>
{
    public static final int ITEM_TYPE_EMPTY = -1;
    public static final int ITEM_TYPE_LOAD_MORE =  - 2;
    protected Context mContext;
    protected int mLayoutId;
    private View mEmptyView;
    private int mEmptyLayoutId;
    private View mLoadMoreView;
    private int mLoadMoreLayoutId;
    protected List<T> mDatas;
    protected LayoutInflater mInflater;
    private OnItemClickListener mOnItemClickListener;
    private boolean mIsShowLoadMore;


    public CommonAdapter(Context context, int layoutId, List<T> datas)
    {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mLayoutId = layoutId;
        mDatas = datas;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType)
    {
        if (isEmpty())
        {
            ViewHolder holder;
            if (mEmptyView != null)
            {
                holder = ViewHolder.createViewHolder(parent.getContext(), mEmptyView);
            } else
            {
                holder = ViewHolder.createViewHolder(parent.getContext(), parent, mEmptyLayoutId);
            }
            return holder;
        }
        if (viewType == ITEM_TYPE_LOAD_MORE)
        {
            ViewHolder holder;
            if (mLoadMoreView != null)
            {
                holder = ViewHolder.createViewHolder(parent.getContext(), mLoadMoreView);
            } else
            {
                holder = ViewHolder.createViewHolder(parent.getContext(), parent, mLoadMoreLayoutId);
            }
            return holder;
        }
        ViewHolder viewHolder = ViewHolder.createViewHolder(mContext, parent, mLayoutId);
        setListener(parent, viewHolder, viewType);
        return viewHolder;
    }

    @Override
    public int getItemViewType(int position)
    {
        if (isEmpty())
        {
            return ITEM_TYPE_EMPTY;
        }

        if (isShowLoadMore(position))
        {
            return ITEM_TYPE_LOAD_MORE;
        }
        return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        if (isEmpty())
        {
            convertEmptyView(holder);
            return;
        }
        if (position >= mDatas.size())
        {
            return;
        }
        convert(holder, mDatas.get(position),position);
    }

    /**
     * 对emptyview的界面元素进行操作的话，请重写此方法
     * @param holder
     */
    protected void convertEmptyView(ViewHolder holder) {
    }

    public abstract void convert(ViewHolder holder, T t, int position);

    @Override
    public int getItemCount()
    {
        if (isEmpty()) return 1;
        return mDatas.size()+ (isShowLoadMore() ? 1 : 0);
    }

    private boolean isEmpty()
    {
        return (mEmptyView != null || mEmptyLayoutId != 0) && mDatas.size() <= 0;
    }

    public void setEmptyView(View emptyView)
    {
        mEmptyView = emptyView;
    }

    public void setEmptyView(int layoutId)
    {
        mEmptyLayoutId = layoutId;
    }

    protected void setListener(final ViewGroup parent, final ViewHolder viewHolder, int viewType) {
        if (!isEnabled(viewType)) return;
        viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    int position = viewHolder.getAdapterPosition();
                    T bean = position<mDatas.size()?mDatas.get(position):null;
                    mOnItemClickListener.onItemClick(v, viewHolder , bean,position);
                }
            }
        });

        viewHolder.getConvertView().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mOnItemClickListener != null) {
                    int position = viewHolder.getAdapterPosition();
                    T bean = position<mDatas.size()?mDatas.get(position):null;
                    return mOnItemClickListener.onItemLongClick(v, viewHolder, bean);
                }
                return false;
            }
        });
    }

    protected boolean isEnabled(int viewType) {
        return true;
    }

    public interface OnItemClickListener<T> {
        void onItemClick(View view, ViewHolder holder, T bean, int position);

        boolean onItemLongClick(View view, ViewHolder holder, T bean);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    private boolean isShowLoadMore(int position)
    {
        return hasLoadMore() && position >= mDatas.size();
    }
    private boolean isShowLoadMore()
    {
        return hasLoadMore() &&!isEmpty()&&mIsShowLoadMore;
    }

    public void setIsShowLoadMore(boolean flag){
        mIsShowLoadMore=flag;
        notifyDataSetChanged();
    }
    private boolean hasLoadMore()
    {
        return mLoadMoreView != null || mLoadMoreLayoutId != 0;
    }

    public void setLoadMoreView(View loadMoreView)
    {
        mLoadMoreView = loadMoreView;
    }

    public void setLoadMoreView(int layoutId)
    {
        mLoadMoreLayoutId = layoutId;
    }

    public void setData(List<T> list){
        this.mDatas=list;
        notifyDataSetChanged();
    }

    public void addData(List<T> list){
        if(list==null||list.isEmpty()){
            return;
        }
        mDatas.addAll(list);
        notifyDataSetChanged();
    }

    public List<T> getData(){
        return mDatas;
    }
}
