package com.mystudy.studyviews.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * 多边形
 * 雷达
 */
public class polygonView extends View {
    private Path polygonPath, textPath,contentPath;
    private Paint polygonPaint, pointPaint, textPaint,contentPaint;
    private float radius;//半径
    private int count = 6;//多边
    private int currentR;
    private float angle = (float) (Math.PI * 2 / count);//平均的角度
    private int centerX, centerY;
    private float[] datas;

    public polygonView(Context context) {
        super(context);
        init();
    }

    public polygonView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public polygonView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        polygonPath = new Path();
        polygonPaint = new Paint();
        polygonPaint.setColor(Color.BLACK);
        polygonPaint.setStrokeWidth(3);
        polygonPaint.setStyle(Paint.Style.STROKE);
        pointPaint = new Paint();
        pointPaint.setStrokeWidth(10);
        pointPaint.setColor(Color.GREEN);
        pointPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        textPaint = new Paint();
        textPaint.setStrokeWidth(5);
        textPaint.setColor(Color.RED);
        textPaint.setTextSize(36);
        textPaint.setStyle(Paint.Style.STROKE);
        textPath = new Path();
        contentPaint=new Paint();
        contentPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        contentPaint.setStrokeWidth(5);
        contentPaint.setColor(Color.BLUE);
        contentPath=new Path();

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int width = getWidth();
        int height = getHeight();
        centerX = width / 2;
        centerY = height / 2;
        radius = (Math.min(width, height)) / 2 * 0.8f;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawpolygon(canvas);
        drawLine(canvas);
        drawText(canvas);
        drawCentent(canvas);
    }


    /**
     * @param datas
     * 内容的数据
     */
    public void setDatas(float[] datas){
        this.datas=datas;
    }

    /**
     * @param canvas
     * 画占比
     */
    private void drawCentent(Canvas canvas) {
        if (datas==null){
            datas=new float[]{0.6f,0.5f,0.0f,0.8f,0.9f,0.8f};
        }
        canvas.drawPoint(0,0,pointPaint);
        contentPath.reset();
        for (int i = 0; i < datas.length; i++) {
            float x= (float) (radius*Math.cos(angle*i)*datas[i]);
            float y= (float) (radius*Math.sin(angle*i)*datas[i]);
            canvas.drawPoint(x,y,pointPaint);
            if (i==0) {
                contentPath.moveTo(x, y);
            }else {
                contentPath.lineTo(x,y);
            }
        }
        canvas.drawPath(contentPath,contentPaint);
    }

    /**
     * @param canvas 画标识
     */
    private void drawText(Canvas canvas) {
        canvas.translate(centerX,centerY);
        canvas.drawPoint(0,0,pointPaint);
        for (int i = 0; i < count; i++) {
            float textX = (float) (radius/0.9f * Math.cos(angle * i));
            float textY = (float) (radius/0.9f  * Math.sin(angle * i));
            canvas.drawText(String.valueOf(i+1),textX,textY,textPaint);
        }
    }

    /**
     * @param canvas 画线
     */
    private void drawLine(Canvas canvas) {
        for (int i = 0; i < count; i++) {
            polygonPath.reset();
            polygonPath.moveTo(centerX, centerY);
            float x = (float) (centerX + radius * Math.cos(angle * i));
            float y = (float) (centerY + radius * Math.sin(angle * i));
            polygonPath.lineTo(x, y);
            polygonPath.close();
            canvas.drawPath(polygonPath, polygonPaint);
        }

    }

    /**
     * @param canvas 画多边形
     */
    private void drawpolygon(Canvas canvas) {
        currentR = (int) (radius / (count - 1));
        for (int i = 0; i < count; i++) {
            float r = currentR * i;
            polygonPath.reset();
            for (int j = 0; j < count; j++) {
                if (j == 0) {
                    polygonPath.moveTo(centerX + r, centerY);
//                    canvas.drawPoint(centerX + r, centerY, pointPaint);
                } else {
                    float x = (float) (centerX + r * Math.cos(angle * j));
                    float y = (float) (centerY + r * Math.sin(angle * j));
                    polygonPath.lineTo(x, y);
                }
            }
            polygonPath.close();
            canvas.drawPath(polygonPath, polygonPaint);
        }
    }


}
