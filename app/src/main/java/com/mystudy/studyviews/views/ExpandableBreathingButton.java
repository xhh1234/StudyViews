package com.mystudy.studyviews.views;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

import androidx.annotation.Nullable;

/**
 *呼吸展开的Button
 */
public class ExpandableBreathingButton extends View {
    private Color circleColor, roundRectColor, textColor;
    private Paint circlePaint, rounddRectPaint, textPaint;
    private int width, height;
    private float circleRadius, roundRectRadius;
    private RectF roundCircleRectF;
    private float roundRectLeft;
    private float textWidth, textHeight;
    private ValueAnimator BreathingAnimator, expandableAnimator;
    private float breathingAnimatorValue;
    private String[] contentStr;
    private ArrayList<Float> contentXList=new ArrayList<Float>();

    public ExpandableBreathingButton(Context context) {
        super(context);
        init();
    }

    public ExpandableBreathingButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ExpandableBreathingButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * 初始化一些Paint
     */
    private void init() {
        circlePaint = new Paint();
        rounddRectPaint = new Paint();
        textPaint = new Paint();
        circlePaint.setColor(Color.YELLOW);
        circlePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        circlePaint.setStrokeWidth(5);
        rounddRectPaint.setColor(Color.BLUE);
        rounddRectPaint.setStyle(Paint.Style.FILL);
        rounddRectPaint.setStrokeWidth(5);
        textPaint.setStrokeWidth(5);
        textPaint.setStyle(Paint.Style.STROKE);
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(68);
        contentStr = new String[]{"图片", "照片", "相片"};
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
        circleRadius = h * 0.8f / 2;//圆的半径
        roundRectRadius = h * 0.9f / 2;//圆角矩形中的圆的半径
        textWidth = textPaint.measureText("发布");//计算文字的宽度
        textHeight = textPaint.descent() + textPaint.ascent();//计算文字的高度
        initBreathingAnimator();
        startBreathingAnimator();
        roundRectLeft = width;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawRoundRect(canvas);
        drawCircle(canvas);
        drawText(canvas);
        drawContentText(canvas);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    protected void onDetachedFromWindow() {
        if (BreathingAnimator != null & BreathingAnimator.isRunning()) {
            BreathingAnimator.cancel();
        }
        if (expandableAnimator != null & expandableAnimator.isRunning()) {
            expandableAnimator.cancel();
        }
        super.onDetachedFromWindow();
    }

    private void startBreathingAnimator() {
        if (BreathingAnimator != null & !BreathingAnimator.isRunning()) {
            BreathingAnimator.start();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                onClickDownThing(x, y);
                break;
        }
        return super.onTouchEvent(event);
    }

    /**
     * @param x
     * @param y 点击时按下的事件的处理
     */
    private void onClickDownThing(float x, float y) {
        Log.e("onClickThing", "onClickThing: " + x + "___" + y);
        if (x > width - circleRadius * 2) {//点击圆圈的事件
            BreathingAnimator.cancel();
            initExpandableAnimation();
            expandableAnimator.start();
        }
    }

    /**
     * 初始化展开的动画
     */
    private void initExpandableAnimation() {
        expandableAnimator = ValueAnimator.ofFloat(width, 0f);
        expandableAnimator.setDuration(5000);
        expandableAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                roundRectLeft = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
    }


    /**
     * 初始化呼吸的动画
     */
    private void initBreathingAnimator() {
        BreathingAnimator = ValueAnimator.ofFloat(circleRadius, roundRectRadius);
        BreathingAnimator.setDuration(5000);
        BreathingAnimator.setRepeatMode(ValueAnimator.REVERSE);
        BreathingAnimator.setRepeatCount(ValueAnimator.INFINITE);
        BreathingAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                breathingAnimatorValue = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        BreathingAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                breathingAnimatorValue = roundRectRadius;
                invalidate();
            }
        });
    }

    /**
     * @param canvas 画文字
     */
    private void drawText(Canvas canvas) {
        float x = width - roundRectRadius - textWidth / 2;
        float y = height / 2 - textHeight / 2;
        canvas.drawText("发布", x, y, textPaint);
    }

    /**
     * @param canvas
     * 画内容文字
     */
    private void drawContentText(Canvas canvas) {
        float y = height / 2 - textHeight / 2;
        float x = width - roundRectLeft - roundRectRadius*2;
        contentXList.clear();
        if (x > roundRectRadius * 3) {
            for (int i = 0; i < contentStr.length; i++) {
                float contentTextWidth = textPaint.measureText(contentStr[contentStr.length-1-i]);
                x = x - contentTextWidth*1.5f;
                contentXList.add(x);
                canvas.drawText(contentStr[contentStr.length-1-i], x, y, textPaint);
            }
        }
    }

    /**
     * @param canvas 画圆角矩形
     */
    private void drawRoundRect(Canvas canvas) {
        float top = 0;
        float right = width;
        float bottom = height;
        roundCircleRectF = new RectF(roundRectLeft, top, right, bottom);
        canvas.drawRoundRect(roundCircleRectF, roundRectRadius, roundRectRadius, rounddRectPaint);

    }

    /**
     * @param canvas 画圆
     */
    private void drawCircle(Canvas canvas) {
        float x = width - roundRectRadius;
        float y = height / 2;

        canvas.drawCircle(x, y, breathingAnimatorValue, rounddRectPaint);
        canvas.drawCircle(x, y, circleRadius, circlePaint);
    }

}
