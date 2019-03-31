package com.mystudy.studyviews.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mystudy.studyviews.R;
import com.mystudy.studyviews.util.LogUtil;
import com.mystudy.studyviews.views.TickView;

import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class TickViewFragment extends Fragment {


    @BindView(R.id.tv_tick)
    TickView tvTick;

    public TickViewFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tick_view, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.btn_tick)
    public void onViewClicked() {
        LogUtil.e("Tick fragment", "onClicked");
        tvTick.startAnimation();
    }
}
