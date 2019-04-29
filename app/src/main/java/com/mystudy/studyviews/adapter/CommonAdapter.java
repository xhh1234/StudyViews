package com.mystudy.studyviews.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public abstract class CommonAdapter<T> extends RecyclerView.Adapter<ViewHolder> {
    List<T> mdata;
    Context mContext;
    LayoutInflater mLayoutInflater;
    int LayoutId;
    

    public CommonAdapter(List<T> mdata, Context mContext, int layoutId) {
        this.mdata = mdata;
        this.mContext = mContext;
        LayoutId = layoutId;
        mLayoutInflater=LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewHolder holder;
        holder=ViewHolder.get(mContext,parent,LayoutId);

        return holder;

    }

    @NonNull
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //绑定数据
        convert(holder,mdata.get(position),position);
    }

    @Override
    public int getItemViewType(int position) {

         return super.getItemViewType(position);

    }

    /**
     * 实现这个方法来绑定数据与事件
     * @param holder
     * @param t
     */
    public abstract void convert(ViewHolder holder, T t,int position);

    @Override
    public int getItemCount() {
        return mdata.size();
    }

}
