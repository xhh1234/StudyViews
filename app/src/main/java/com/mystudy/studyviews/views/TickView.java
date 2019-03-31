package com.mystudy.studyviews.views;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import com.mystudy.studyviews.util.LogUtil;

import androidx.annotation.Nullable;

/**
 * 打对号，有动画的思路如下：可以说是路径追踪
 * 通过测量Path来实现，用PathMeasure类来测量Path;
 * 通过PathMeasure类的setPath()来关联Path
 * 通过PathMeasure类的getLength()来获取Path的长度。
 *通过PathMeasure类的getSegment()来截取测量的Path的片段。
 */
public class TickView extends View {
    private Paint circlePaint;
    private Path circlePath;
    private Path mDstPath;
    private ValueAnimator valueAnimator,tickValueAnimator;
    private PathMeasure mPathMeasure;
    private Float tickLength;
    private Float mValueAnimator;
    private int width;
    private Boolean IsHasCircle=false;

    public TickView(Context context) {
        super(context);
        init();
    }

    public TickView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TickView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LogUtil.e(getClass().getSimpleName(),"init");
        circlePaint=new Paint();
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setStrokeWidth(5);
        circlePath=new Path();
        mPathMeasure=new PathMeasure();
    }

    Float rx,ry,r;
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        LogUtil.e(getClass().getSimpleName(),"onMeasure");
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        LogUtil.e(getClass().getSimpleName(),"onSizeChange");
        width=getWidth();
        int height=getHeight();
        rx= Float.valueOf(width/2);
        ry= Float.valueOf(height/2);
        r= Float.valueOf(Math.min(width,height)/2);
        initCircleAnimation();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        LogUtil.e(getClass().getSimpleName(),"onLayout");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        LogUtil.e(getClass().getSimpleName(),"onDraw");
        if (mDstPath==null){
            return;
        }
        if (IsHasCircle) {
            canvas.drawPath(circlePath,circlePaint);
        }
        mDstPath.reset(); //刷新当前截取 Path
        mDstPath.lineTo(0,0);// 避免硬件加速的Bug
        //截取片段
        float top=mValueAnimator*tickLength;
        mPathMeasure.getSegment(0,top,mDstPath,true);
        canvas.drawPath(mDstPath,circlePaint);
    }

    /**
     * 当View从屏幕消失时，关闭动画，防止内存泄露
     */
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        // 取消圆形动画
        boolean isCircleNeedCancel =
                ( valueAnimator!= null && valueAnimator.isRunning());
        if (isCircleNeedCancel){
            valueAnimator.cancel();
        }
        //取消打勾动画
        boolean isTickNeedCancel=(tickValueAnimator!=null&&tickValueAnimator.isRunning());
        if (isTickNeedCancel){
            tickValueAnimator.cancel();
        }
    }

    /**
     * 初始化圆的动画
     */
    private void initCircleAnimation(){
        circlePath.addCircle(rx,ry,r, Path.Direction.CW);
        mPathMeasure.setPath(circlePath,false);
        tickLength=mPathMeasure.getLength();

        valueAnimator=ValueAnimator.ofFloat(0,1);
        valueAnimator.setDuration(3000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mValueAnimator= (Float) animation.getAnimatedValue();
                //不断的刷新UI
                invalidate();
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                IsHasCircle=true;
                initTick();
                tickValueAnimator.start();
                invalidate();
            }
        });
    }

    /**
     * 初始化渐变
     */
    private void initShaderGradient(){
        //Paint线性渐变
        LinearGradient linearGradient=new LinearGradient(0,0,width,width,
                Color.parseColor("#FF4081"),Color.YELLOW, Shader.TileMode.REPEAT);
        circlePaint.setShader(linearGradient);
    }
    /**
     * 初始化打勾
     */
    private void initTick(){
        Path path = new Path();
        // 对号起点
        float startX = (float) (0.3 * width);
        float startY = (float) (0.5 * width);
        path.moveTo(startX, startY);

        // 对号拐角点
        float cornerX = (float) (0.43 * width);
        float cornerY = (float) (0.66 * width);
        path.lineTo(cornerX, cornerY);

        // 对号终点
        float endX = (float) (0.75 * width);
        float endY = (float) (0.4 * width);
        path.lineTo(endX, endY);
        mPathMeasure.setPath(path,false);
        tickLength=mPathMeasure.getLength();
        tickValueAnimator=ValueAnimator.ofFloat(0,1);
        tickValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mValueAnimator= (Float) animation.getAnimatedValue();
                invalidate();
            }
        });
        tickValueAnimator.setDuration(3000);
    }

    /**
     * 开始动画
     */
    public void startAnimation(){
        mDstPath=new Path();
        initShaderGradient();
        valueAnimator.start();
    }

}
