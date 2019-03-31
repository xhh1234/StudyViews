package com.mystudy.studyviews.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;

import java.lang.reflect.Field;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

public class Anim_List_ImageView extends AppCompatImageView {
    private AnimationDrawable anim;
    public Anim_List_ImageView(Context context) {
        super(context);
    }

    public Anim_List_ImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Anim_List_ImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    public void setAnimationDrawable(AnimationDrawable anim){
        this.anim=anim;
    }
    public void setLocaton(int left,int top){
        this.setFrame(left,top,left+200,top+200);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        try {
            Field field= AnimationDrawable.class.getField("mCurFrame");
            field.setAccessible(true);
            int curFrame=field.getInt(anim);
            if (curFrame==anim.getNumberOfFrames()-1){
                setVisibility(INVISIBLE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDraw(canvas);
    }
}
