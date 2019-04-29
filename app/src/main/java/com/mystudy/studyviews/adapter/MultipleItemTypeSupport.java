package com.mystudy.studyviews.adapter;

public interface MultipleItemTypeSupport<T> {
    int getLayoutId(int itemViewType);//返回layoutId
    int getItemViewType(int position,T t);//返回itemview的type的值
}
