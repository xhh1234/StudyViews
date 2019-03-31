package com.mystudy.studyviews.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.mystudy.studyviews.R;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

public class FirstViews extends View {
    Path mPath;
    Canvas mCanvas;
    RectF mRectf;
    Paint mPaint;

    int color;

    public FirstViews(Context context) {
//        super(context);
        this(context,null);
    }

    public FirstViews(Context context, @Nullable AttributeSet attrs) {
//        super(context, attrs);
        this(context, attrs,0);
    }

    public FirstViews(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a=context.obtainStyledAttributes(attrs,R.styleable.FirstViews);
        color=a.getColor(R.styleable.FirstViews_view_color,Color.BLUE);
        a.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int bottom=getPaddingBottom();
        int top=getPaddingTop();
        int left=getPaddingLeft();
        int  right=getPaddingRight();
        int width=getWidth()-left-right;
        int height=getHeight()-bottom-top;
        int r= Math.min(width,height)/2;
//        RectF mRectf=new RectF(0,0,width,height);
//        mPaint=new Paint();
//        mPath=new Path();
//        mPaint.setColor(Color.RED);
//        mPaint.setStrokeWidth(10);
//        mPaint.setStyle(Paint.Style.STROKE);
//        mPaint.setTextSize(20);
//        canvas.drawRoundRect(mRectf,30,30,mPaint);
//        canvas.drawText("abcdg",40,30,mPaint);
//        canvas.drawText("abcdg",0,3,40,60,mPaint);
//        mPath.cubicTo(540,750,640,450,840,600);
////        mPath.addCircle(400,400,110, Path.Direction.CW);
//        canvas.drawPath(mPath,mPaint);
////        mPaint.reset();
////        mPaint.setStyle(Paint.Style.FILL);
//        canvas.drawTextOnPath("This is TextView",mPath,0,0,mPaint);
//        canvas.translate(300,300);
        mPaint=new Paint();
        mPaint.setColor(color);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawCircle(left+width/2,top+height/2,r,mPaint);
    }
}
