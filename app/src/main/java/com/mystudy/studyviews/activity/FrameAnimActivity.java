package com.mystudy.studyviews.activity;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.mystudy.studyviews.R;
import com.mystudy.studyviews.fragment.TickViewFragment;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;
import butterknife.ButterKnife;

public class FrameAnimActivity extends BaseActivity {

    @BindView(R.id.fl_fragment)
    FrameLayout flFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame_anim);
        ButterKnife.bind(this);
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fl_fragment,new TickViewFragment());
        fragmentTransaction.commit();
    }
}
