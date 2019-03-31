package com.mystudy.studyviews.fragment;


import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.RotateDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.mystudy.studyviews.R;
import com.mystudy.studyviews.views.Anim_List_ImageView;

import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class FrameAnimationListFragment extends Fragment {


    @BindView(R.id.iv_frame_list)
    ImageView ivFrameList;
    @BindView(R.id.fl_fragment)
    FrameLayout flFragment;

    Anim_List_ImageView anim_list_imageView;
    AnimationDrawable animationDrawable;


    public FrameAnimationListFragment() {
        // Required empty public constructor
    }

    private AnimationDrawable anim;
    private RotateDrawable rotateDrawable;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_frame_one, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (ivFrameList != null) {
            anim = (AnimationDrawable) ivFrameList.getBackground();
//            rotateDrawable= (RotateDrawable) ivFrameList.getBackground();
        } else {
            Toast.makeText(getContext(), "失败", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick({R.id.btn_open, R.id.btn_stop})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_open:
                anim.start();
                break;
            case R.id.btn_stop:
                anim.stop();
                break;
        }
    }
}
