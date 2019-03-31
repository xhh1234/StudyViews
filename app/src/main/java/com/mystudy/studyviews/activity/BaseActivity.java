package com.mystudy.studyviews.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.mystudy.studyviews.util.LogUtil;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {

    public final String TAG = this.getClass().getSimpleName();

    public void actionStart(Context mContext, Class activityClass){
        mContext.startActivity(new Intent(mContext,activityClass));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.e(TAG,"onCreate");
    }
}
