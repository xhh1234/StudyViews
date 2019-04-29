package com.mystudy.studyviews.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 通用的ViewHolder
 */
public class ViewHolder extends RecyclerView.ViewHolder {
    private SparseArray<View> mViews;
    private View mConverView;
    private Context mContext;

    public ViewHolder(@NonNull View itemView, Context mContext, ViewGroup parent) {
        super(itemView);
        this.mConverView = itemView;
        this.mContext = mContext;
        mViews=new SparseArray<>();
    }
    public static ViewHolder get(Context context, ViewGroup parent, int layoutId){
        View itemview= LayoutInflater.from(context).inflate(layoutId,parent,false);
        ViewHolder viewHolder=new ViewHolder(itemview,context,parent);
        return viewHolder;
    }

    /**
     * 通过viewid获取控件
     * @param viewId
     * @param <T>
     * @return
     */
    public <T extends View>T getViews(int viewId){
        View view=mViews.get(viewId);
        if (view==null){
            view=mConverView.findViewById(viewId);
            mViews.put(viewId,view);
        }
        return (T) view;
    }

    //铺助方法

    public ViewHolder setText(int viewId,String msg){
        TextView textView=getViews(viewId);
        textView.setText(msg);
        return this;
    }
    public ViewHolder setVisibility(int viewId,int visibility){
        View view=getViews(viewId);
        view.setVisibility(visibility);
        return this;
    }

}
