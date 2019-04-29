package com.mystudy.studyviews.adapter;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import java.util.List;

public abstract class MultipleItemTypeAdapter<T> extends CommonAdapter<T>{
    private MultipleItemTypeSupport multipleItemTypeSupport;

    public MultipleItemTypeAdapter(List<T> mdata, Context mContext, MultipleItemTypeSupport<T> multipleItemTypeSupport) {
        super(mdata, mContext, -1);
        this.multipleItemTypeSupport=multipleItemTypeSupport;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layoutId=multipleItemTypeSupport.getLayoutId(viewType);
        ViewHolder holder=ViewHolder.get(mContext,parent,layoutId);
        return holder;
    }

    @Override
    public int getItemViewType(int position) {
        return multipleItemTypeSupport.getItemViewType(position,mdata.get(position));
    }

    @Override
    public void convert(ViewHolder holder, T t, int position) {
        converts(holder,t,position);
    }
    public abstract void converts(ViewHolder holder, T t,int position);
}
