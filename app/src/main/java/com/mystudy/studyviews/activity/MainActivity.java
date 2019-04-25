package com.mystudy.studyviews.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.mystudy.studyviews.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {


    @BindView(R.id.btn_frame_animation)
    Button btnFrameAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }


    @OnClick({R.id.btn_frame_animation,R.id.btn_tablayout,R.id.btn_swiperefreshlayout})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.btn_frame_animation:
                actionStart(this, FrameAnimActivity.class);
                break;
            case R.id.btn_tablayout:
                actionStart(this,TabLayoutActivity.class);
                break;
            case R.id.btn_swiperefreshlayout:
                actionStart(this,SwipeRefreshActivity.class);
                break;

        }
    }
}
