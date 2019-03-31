package com.mystudy.studyviews.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import com.mystudy.studyviews.util.LogUtil;

import java.util.ArrayList;

import androidx.annotation.Nullable;

public class ElectrocardView extends View {
    private Paint mPaint, electrocardPaint;
    private Path electrocarPath;
    private int gridWidth, gridHeight;
    private int horizontalLineNum = 3;
    private int verticalLineNum = 5;
    private int baseLine, maxLine;
    private Boolean IsGridDraw = true;
    private int index = 0;
    private int widthofSmallGird;
    private ArrayList<Float> data = new ArrayList<Float>();
    private ArrayList<Float> electrocardDatas = new ArrayList<>();

    public ElectrocardView(Context context) {
        super(context);
        init();
    }

    public ElectrocardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ElectrocardView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.GREEN);
        electrocardPaint = new Paint();
        electrocardPaint.setColor(Color.YELLOW);
        electrocardPaint.setStyle(Paint.Style.STROKE);
        electrocardPaint.setStrokeWidth(5);
        electrocarPath = new Path();
    }

    public void setNum(int verticalLineNum, int horizontalLineNum) {
        this.verticalLineNum = verticalLineNum;
        this.horizontalLineNum = horizontalLineNum;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawGird(canvas);
        drawElectrocard(canvas);
        postDelayed(new Runnable() {
            @Override
            public void run() {
                addData();
            }
        },500);
    }

    /**
     * 画心电
     * @param canvas
     */
    private void drawElectrocard(Canvas canvas) {
        electrocarPath.moveTo(0, baseLine - data.get(0));
        for (int i = 0; i < electrocardDatas.size(); i++) {
            float y = baseLine - electrocardDatas.get(i);
            electrocarPath.lineTo(i * widthofSmallGird, y);
        }
        canvas.drawPath(electrocarPath, electrocardPaint);

    }

    /**
     * 数据
     */
    public void addData() {
        if (data.size() > 0) {
            electrocardDatas.add(data.get(index));
            index++;
            if (index >= data.size() - 1) {
                index = 0;
                electrocardDatas.clear();
                electrocarPath.reset();
                data.clear();
                setData();
            }
        }
        invalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        gridWidth = w;
        gridHeight = h;
        LogUtil.e("onSizeChange", w + "_fadsf_" + h);
        verticalLineNum = verticalLineNum * 5;
        horizontalLineNum = horizontalLineNum * 5;
        widthofSmallGird = gridWidth / (verticalLineNum * 5);
        baseLine = h / 2;
        maxLine = h / 3;
        setData();
        addData();
    }

    /**
     * 数据
     */
    private void setData() {
        for (int j = 0; j < 2; j++) {
            for (int i = 0; i < 12; i++) {
                data.add(0f);
            }
            for (int i = 0; i < 10; i++) {
                double random;
                if (i % 2 == 0) {
                    random = Math.random();
                } else {
                    random = -Math.random();
                }
                float value = (float) (maxLine * random);
                data.add(value);
            }
            for (int i = 0; i < 12; i++) {
                data.add(0f);
            }
        }
    }

    /**
     * 画网格
     * @param canvas
     */
    private void drawGird(Canvas canvas) {
        for (int i = 0; i < verticalLineNum; i++) {
            if (i % 5 == 0) {
                mPaint.setStrokeWidth(3);
            } else {
                mPaint.setStrokeWidth(1);
            }
            float verticalX = gridWidth / verticalLineNum * i;
            canvas.drawLine(verticalX, 0, verticalX, gridHeight, mPaint);
        }
        for (int i = 0; i < horizontalLineNum; i++) {
            if (i % 5 == 0) {
                mPaint.setStrokeWidth(3);
            } else {
                mPaint.setStrokeWidth(1);
            }
            float horizontalY = gridHeight / horizontalLineNum * i;
            canvas.drawLine(0, horizontalY, gridWidth, horizontalY, mPaint);
        }
        IsGridDraw = false;
    }
}
