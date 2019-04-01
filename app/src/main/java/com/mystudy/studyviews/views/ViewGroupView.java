package com.mystudy.studyviews.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class ViewGroupView extends ViewGroup {
    public ViewGroupView(Context context) {
        super(context);
    }

    public ViewGroupView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ViewGroupView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    /**
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     * 计算所有ChildView的宽度和高度 然后根据ChildView的计算结果，设置自己的宽和高
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //获得此ViewGroup上级容器为其推荐的宽和高，以及计算模式
        int widthMode=MeasureSpec.getMode(widthMeasureSpec);
        int heightMode=MeasureSpec.getMode(heightMeasureSpec);
        int sizeWidth=MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight=MeasureSpec.getSize(heightMeasureSpec);
        //计算所有的子View的宽高与模式
        measureChildren(widthMeasureSpec,heightMeasureSpec);
        //如果设置是wrap_content的宽高
        int width=0;
        int height=0;
        //获取childrenView的个数
        int ccount=getChildCount();
        int cwidth=0;
        int cheight=0;

        // 用于计算左边两个childView的高度
        int lHeight = 0;
        // 用于计算右边两个childView的高度，最终高度取二者之间大值
        int rHeight = 0;

        // 用于计算上边两个childView的宽度
        int tWidth = 0;
        // 用于计算下面两个childiew的宽度，最终宽度取二者之间大值
        int bWidth = 0;

        ViewGroup.MarginLayoutParams cParams=null;


        //根据childView计算的出的宽和高，以及设置的margin计算容器的宽和高，主要用于容器是warp_content时
        for (int i = 0; i < ccount; i++) {
            View childrenView= getChildAt(i);
            cwidth=childrenView.getMeasuredWidth();
            cheight=childrenView.getMeasuredHeight();
            cParams= new MarginLayoutParams(cwidth,cheight);

            if (i==0||i==1){
                tWidth+=cwidth+cParams.leftMargin+cParams.rightMargin;
            }

            if (i==2||i==3){
                bWidth+=cwidth+cParams.leftMargin+cParams.rightMargin;
            }

            if (i==0||i==2){
                lHeight+=cheight+cParams.bottomMargin+cParams.topMargin;
            }
            if (i==1||i==3){
                rHeight+=cheight+cParams.bottomMargin+cParams.topMargin;
            }

        }
        width = Math.max(tWidth, bWidth);
        height = Math.max(lHeight, rHeight);
        /**
         * 如果是wrap_content设置为我们计算的值
         * 否则：直接设置为父容器计算的值
         */
        setMeasuredDimension((widthMode == MeasureSpec.EXACTLY) ? sizeWidth
                : width, (heightMode == MeasureSpec.EXACTLY) ? sizeHeight
                : height);
    }

    /**
     * @param changed
     * @param l
     * @param t
     * @param r
     * @param b
     * 对其所有childView进行定位（设置childView的绘制区域）
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int ccount=getChildCount();
        int cwidth=0;
        int cheight=0;
        MarginLayoutParams cParams=null;
        //遍历所有childView根据其宽和高，以及margin进行布局
        for (int i = 0; i < ccount; i++) {
            View childView=getChildAt(i);
            cwidth=childView.getMeasuredWidth();
            cheight=childView.getMeasuredHeight();
            cParams= new MarginLayoutParams(cwidth,cheight);
            int cl=0,ct=0,cr=0,cb=0;
            switch (i){
                case 0:
                    cl=cParams.leftMargin;
                    ct=cParams.topMargin;
                    break;
                case 1:
                    cl=getWidth()-cParams.leftMargin-cwidth-cParams.rightMargin;
                    ct=cParams.topMargin;
                    break;
                case 2:
                    cl=cParams.leftMargin;
                    ct=getHeight()-cParams.topMargin-cParams.bottomMargin-cheight;
                    break;
                case 3:
                    cl=getWidth()-cParams.leftMargin-cParams.rightMargin-cwidth;
                    ct=getHeight()-cParams.topMargin-cParams.bottomMargin-cheight;
                    break;
            }
            cr=cl+cwidth;
            cb=ct+cheight;
            childView.layout(cl,ct,cr,cb);
        }
    }
}
